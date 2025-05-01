package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;
import com.yawarSoft.Modules.Donation.Enums.DonorStatus;
import com.yawarSoft.Modules.Donation.Mappers.DonorMapper;
import com.yawarSoft.Modules.Donation.Repositories.DonorRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;
    private final DonorMapper donorMapper;
    private final HmacUtil hmacUtil;

    public DonorServiceImpl(DonorRepository donorRepository, AESGCMEncryptionUtil aesGCMEncryptionUtil, DonorMapper donorMapper, HmacUtil hmacUtil) {
        this.donorRepository = donorRepository;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
        this.donorMapper = donorMapper;
        this.hmacUtil = hmacUtil;
    }

    @Override
    public Long createDonor(DonorRequestDTO donorRequestDTO) {
        String combinedInfo = donorRequestDTO.getDocumentType() + '|' + donorRequestDTO.getDocumentNumber();
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        if (donorRepository.existsBySearchHash(searchHash)) {
            throw new IllegalArgumentException("El documento ya existe en el sistema");
        }

        DonorEntity donorEntity = donorMapper.toEntity(donorRequestDTO, aesGCMEncryptionUtil);
        donorEntity.setSearchHash(searchHash);
        donorEntity.setCreatedBy(UserUtils.getAuthenticatedUser());
        donorEntity.setStatus(DonorStatus.APTO.getLabel());
        DonorEntity donorSaved = donorRepository.save(donorEntity);
        return donorSaved.getId();
    }

    @Override
    public Boolean existsByDocument(Long id, String documentType, String documentNumber) {
        String docInfoDonor = documentType + '|' + documentNumber;
        String newSearchHash = hmacUtil.generateHmac(docInfoDonor);
        return donorRepository.existsBySearchHash(newSearchHash);
    }

    @Override
    public DonorGetDTO updateDonor(Long id, DonorRequestDTO donorRequestDTO) throws Exception {
        DonorEntity existingDonor = donorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donante no encontrado con ID: " + id));

        String docInfoDonor = donorRequestDTO.getDocumentType() + '|' + donorRequestDTO.getDocumentNumber();
        String newSearchHash = hmacUtil.generateHmac(docInfoDonor);

        if (!existingDonor.getSearchHash().equals(newSearchHash) &&
                donorRepository.existsBySearchHash(newSearchHash)) {
            throw new IllegalArgumentException("El número de documento ya está registrado con otro donante.");
        }

        existingDonor.setFirstName(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getFirstName()).getBytes());
        existingDonor.setLastName(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getLastName()).getBytes());
        existingDonor.setSecondLastName(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getSecondLastName()).getBytes());
        existingDonor.setDocumentType(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getDocumentType()).getBytes());
        existingDonor.setDocumentNumber(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getDocumentNumber()).getBytes());
        existingDonor.setAddress(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getAddress()).getBytes());
        existingDonor.setPhone(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getPhone()).getBytes());
        existingDonor.setEmail(aesGCMEncryptionUtil.encrypt(donorRequestDTO.getEmail()).getBytes());
        existingDonor.setBirthDate(donorRequestDTO.getBirthDate());
        existingDonor.setGender(donorRequestDTO.getGender());
        existingDonor.setRegion(donorRequestDTO.getRegion());
        existingDonor.setProvince(donorRequestDTO.getProvince());
        existingDonor.setDistrict(donorRequestDTO.getDistrict());

        existingDonor.setPlaceOfBirth(donorRequestDTO.getPlaceOfBirth());
        existingDonor.setPlaceOfOrigin(donorRequestDTO.getPlaceOfOrigin());
        existingDonor.setMaritalStatus(donorRequestDTO.getMaritalStatus());
        existingDonor.setTrips(donorRequestDTO.getTrips());
        existingDonor.setDonationRequest(donorRequestDTO.isDonationRequest());

        String combinedInfo = donorRequestDTO.getDocumentType() + '|' + donorRequestDTO.getDocumentNumber();
        existingDonor.setSearchHash(hmacUtil.generateHmac(combinedInfo));

        donorRepository.save(existingDonor);
        return donorMapper.toGetDto(existingDonor,aesGCMEncryptionUtil);
    }


    @Override
    public DonorGetDTO getDonor(GetDonorRequest infoDonorRequest) {
        String combinedInfo = infoDonorRequest.documentType() + '|' + infoDonorRequest.documentNumber();
        String searchHash = hmacUtil.generateHmac(combinedInfo);
        DonorEntity donorEntity = donorRepository.findBySearchHash(searchHash)
                .orElseThrow(() -> new IllegalArgumentException("Donante no encontrado con documento: " + infoDonorRequest.documentNumber()));

        return donorMapper.toGetDto(donorEntity, aesGCMEncryptionUtil);
    }

    @Override
    public Long getIdDonor(String documentType, String documentNumber) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        return donorRepository.findIdBySearchHash(searchHash)
                .orElse(0L);
    }

}
