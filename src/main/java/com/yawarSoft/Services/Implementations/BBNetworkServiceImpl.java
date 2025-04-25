package com.yawarSoft.Services.Implementations;

import com.yawarSoft.Core.Errors.ResourceNotFoundException;
import com.yawarSoft.Dto.BloodBankNetworkDetailsDTO;
import com.yawarSoft.Dto.NetworkDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.BloodBankNetworkEntity;
import com.yawarSoft.Core.Entities.NetworkEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Mappers.BBNetworkMapper;
import com.yawarSoft.Repositories.BBNetworkRepository;
import com.yawarSoft.Services.Interfaces.BBNetworkService;
import com.yawarSoft.Services.Interfaces.BloodBankService;
import com.yawarSoft.Core.Utils.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class BBNetworkServiceImpl implements BBNetworkService {

    private final BBNetworkRepository networkRepository;
    private final BBNetworkMapper networkMapper;
    private final BloodBankService bloodBankService;

    public BBNetworkServiceImpl(BBNetworkRepository networkRepository, BBNetworkMapper networkMapper, BloodBankService bloodBankService) {
        this.networkRepository = networkRepository;
        this.networkMapper = networkMapper;
        this.bloodBankService = bloodBankService;
    }

    @Override
    public Page<NetworkDTO> searchByNameWithActualBloodBank(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return networkRepository.findNetworksByStatus(name,"ACTIVE" ,pageable)
                .map(networkMapper::toDto)
                .map(dto -> {
                    // Ordenar manualmente los detalles por ID
                    dto.getBloodBankDetails().sort(Comparator.comparing(BloodBankNetworkDetailsDTO::getId));
                    return dto;
                });
    }

    @Override
    public NetworkDTO getById(Integer id) {
        NetworkEntity entity = networkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Red no encontrada con ID: " + id));
        return networkMapper.toDto(entity);
    }

    @Override
    public void associateBloodBank(Integer networkId, Integer bloodBankId) {
        Integer userId = UserUtils.getAuthenticatedUserId();
        NetworkEntity network = networkRepository.findById(networkId)
                .orElseThrow(() -> new RuntimeException("Red no encontrada"));
        BloodBankEntity bloodBank = bloodBankService.getBloodBankEntityById(bloodBankId)
                .orElseThrow(() -> new RuntimeException("Banco de sangre no encontrado"));

        BloodBankNetworkEntity relation = new BloodBankNetworkEntity();
        relation.setNetwork(network);
        relation.setBloodBank(bloodBank);
        relation.setStatus("ACTIVE");
        relation.setCreatedBy(UserEntity.builder().id(userId).build());
        network.getBloodBankRelations().add(relation);

        networkRepository.save(network);
    }

    @Override
    public void disassociateBloodBank(Integer networkId, Integer bloodBankId) {
        Integer userId = UserUtils.getAuthenticatedUserId();
        NetworkEntity network = networkRepository.findById(networkId)
                .orElseThrow(() -> new RuntimeException("Red no encontrada"));

        network.getBloodBankRelations().stream()
                .filter(relation -> relation.getBloodBank().getId().equals(bloodBankId) && relation.getStatus().equals("ACTIVE"))
                .findFirst()
                .ifPresent(relation -> {
                    relation.setStatus("INACTIVE");
                    relation.setDisassociatedAt(LocalDateTime.now());
                    relation.setDisassociatedBy(UserEntity.builder().id(userId).build());
                });

        networkRepository.save(network);
    }


}
