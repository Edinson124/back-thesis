package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.BloodBankTypeEntity;
import com.yawarSoft.Core.Entities.BloodStorageEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.ImageStorageService;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankListDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Dto.Reponse.BloodBankOptionsAddNetworkDTO;
import com.yawarSoft.Modules.Admin.Enums.BloodBankStatus;
import com.yawarSoft.Modules.Admin.Mappers.BloodBankMapper;
import com.yawarSoft.Modules.Admin.Repositories.BloodBankRepository;
import com.yawarSoft.Modules.Admin.Repositories.BloodStorageRepository;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BloodBankServiceImpl implements BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final BloodBankMapper bloodBankMapper;
    private final ImageStorageService imageStorageService;
    private final BloodStorageRepository bloodStorageRepository;

    public BloodBankServiceImpl(BloodBankRepository bloodBankRepository, BloodBankMapper bloodBankMapper, ImageStorageService imageStorageService, BloodStorageRepository bloodStorageRepository) {
        this.bloodBankRepository = bloodBankRepository;
        this.bloodBankMapper = bloodBankMapper;
        this.imageStorageService = imageStorageService;
        this.bloodStorageRepository = bloodStorageRepository;
    }

    @Override
    public Page<BloodBankListDTO> getBloodBankPaginated(int page, int size, String name, String region, String province, String district) {
        Pageable pageable = PageRequest.of(page, size);

        name = (name != null && !name.isBlank()) ? name : null;
        region = (region != null && !region.isBlank()) ? region : null;
        province = (province != null && !province.isBlank()) ? province : null;
        district = (district != null && !district.isBlank()) ? district : null;

        return bloodBankRepository.findByFilters(name, region, province, district, pageable)
                .map(bloodBankMapper::toListDTO);
    }

    @Override
    public BloodBankDTO getBloodBankById(int id) {
        BloodBankEntity bloodBankEntity = bloodBankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banco de sangre no encontrado con ID: " + id));

        return bloodBankMapper.toDTO(bloodBankEntity);
    }

    @Override
    public BloodBankDTO updateBloodBank(int id, BloodBankDTO bloodBankDTO) {

        BloodBankEntity existingBloodBank = bloodBankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banco de sangre no encontrado con ID: " + id));

        if (!existingBloodBank.getName().equals(bloodBankDTO.getName()) &&
                bloodBankRepository.existsByName(bloodBankDTO.getName())) {
            throw new IllegalArgumentException("El nombre del banco de sangre ya está registrado.");
        }

        if (bloodBankDTO.getIdCoordinator() != null) {
            existingBloodBank.setCoordinator(UserEntity.builder()
                    .id(bloodBankDTO.getIdCoordinator())
                    .build());
        } else {
            existingBloodBank.setCoordinator(null);
        }

        existingBloodBank.setName(bloodBankDTO.getName());
        existingBloodBank.setRegion(bloodBankDTO.getRegion());
        existingBloodBank.setProvince(bloodBankDTO.getProvince());
        existingBloodBank.setDistrict(bloodBankDTO.getDistrict());
        existingBloodBank.setAddress(bloodBankDTO.getAddress());
        existingBloodBank.setStatus(bloodBankDTO.getStatus());
        existingBloodBank.setUpdatedAt(LocalDateTime.now());
        existingBloodBank.setUpdatedBy(UserUtils.getAuthenticatedUser());
        existingBloodBank.setBloodBankType(BloodBankTypeEntity.builder().id(bloodBankDTO.getIdType()).build());

        BloodBankEntity bloodBankSaved = bloodBankRepository.save(existingBloodBank);
        return bloodBankMapper.toDTO(bloodBankSaved);
    }

    @Override
    public BloodBankDTO createBloodBank(BloodBankDTO bloodBankDTO) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();

        if (bloodBankRepository.existsByName(bloodBankDTO.getName())) {
            throw new IllegalArgumentException("El nombre del banco de sangre ya está registrado.");
        }

        BloodBankEntity bloodBank = bloodBankMapper.toEntity(bloodBankDTO);
        if (bloodBankDTO.getIdCoordinator() != null) {
            bloodBank.setCoordinator(UserEntity.builder()
                    .id(bloodBankDTO.getIdCoordinator())
                    .build());
        } else {
            bloodBank.setCoordinator(null);
        }
        bloodBank.setCreatedBy(userAuth);
        bloodBank.setIsInternal(true);

        BloodBankEntity bloodBankSaved = bloodBankRepository.saveAndFlush(bloodBank);
        BloodStorageEntity storage = BloodStorageEntity.builder()
                .bloodBank(bloodBankSaved)
                .totalBlood(0)
                .erythrocyteConcentrate(0)
                .freshFrozenPlasma(0)
                .cryoprecipitate(0)
                .platelet(0)
                .plateletApheresis(0)
                .redBloodCellsApheresis(0)
                .plasmaApheresis(0)
                .build();

        bloodStorageRepository.save(storage);
        return bloodBankMapper.toDTO(bloodBankSaved);
    }

    @Override
    public BloodBankDTO changeStatus(Integer id) {
        Optional<BloodBankEntity> bloodBankEntityOptional = bloodBankRepository.findById(id);
        if (bloodBankEntityOptional.isPresent()) {
            BloodBankEntity bloodBank = bloodBankEntityOptional.get();
            bloodBank.setStatus(Objects.equals(bloodBank.getStatus(), BloodBankStatus.ACTIVE.name())
                    ? BloodBankStatus.INACTIVE.name()
                    : BloodBankStatus.ACTIVE.name());
            BloodBankEntity updateBloodBank = bloodBankRepository.save(bloodBank);
            return bloodBankMapper.toDTO(updateBloodBank);
        } else {
            throw new RuntimeException("Banco de sangre no encontrado con ID: " + id);
        }
    }

    @Override
    public List<BloodBankSelectOptionDTO> getBloodBankSelector() {
        List<BloodBankProjectionSelect> bloodBankSelectProjection = bloodBankRepository.getBloodBankSelect();
        return bloodBankMapper.toSelectDtoListFromProjectionList(bloodBankSelectProjection);
    }

    @Override
    public Optional<BloodBankEntity> getBloodBankEntityById(Integer id) {
        return bloodBankRepository.findById(id);
    }

    @Override
    public String updateBloodBankProfileImage(Integer bloodBankId, MultipartFile profileImage) throws IOException {
        Optional<BloodBankEntity> bloodBankEntityOptional = bloodBankRepository.findById(bloodBankId);

        if (bloodBankEntityOptional.isPresent()) {
            BloodBankEntity bloodBank = bloodBankEntityOptional.get();
            String imageUrl = imageStorageService.storeImage(bloodBankId, profileImage, "USER");
            bloodBank.setProfileImageUrl(imageUrl);
            bloodBankRepository.save(bloodBank);
            return imageUrl;
        } else {
            throw new IllegalArgumentException("Banco de sangre no encontrado");
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteBloodBankProfileImage(Integer bloodBankId) {
        Optional<BloodBankEntity> bloodBankEntityOptional = bloodBankRepository.findById(bloodBankId);
        if (bloodBankEntityOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND, "Banco de sangre no encontrado."));
        }

        BloodBankEntity bloodBank = bloodBankEntityOptional.get();

        // Verificar si el usuario tiene una imagen asignada
        if (bloodBank.getProfileImageUrl() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND, "El banco de sangre no tiene una imagen de perfil."));
        }

        // Eliminar la imagen del servidor
        boolean deleted = imageStorageService.deleteImage(bloodBank.getProfileImageUrl());
        if (!deleted) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la imagen del servidor."));
        }

        // Actualizar la URL de la imagen en la base de datos (poner en null)
        bloodBank.setProfileImageUrl(null);
        bloodBankRepository.save(bloodBank);

        return ResponseEntity
                .ok(new ApiResponse(HttpStatus.OK, "Imagen eliminada y perfil actualizado correctamente."));
    }

    @Override
    public Page<BloodBankOptionsAddNetworkDTO> getBloodBankOptionsNetwork(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<BloodBankEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro fijo: status = 'ACTIVE'
            predicates.add(cb.equal(root.get("status"), BloodBankStatus.ACTIVE.name()));

            // Filtro opcional por nombre (contenga, case insensitive)
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            // Combina todos los predicados con AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<BloodBankEntity> bloodBanksPage = bloodBankRepository.findAll(spec, pageable);
        return bloodBanksPage.map(bloodBankMapper::toOptionNetworkDTO);
    }
}
