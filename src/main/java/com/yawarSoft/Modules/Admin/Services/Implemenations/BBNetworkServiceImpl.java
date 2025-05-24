package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Core.Errors.ResourceNotFoundException;
import com.yawarSoft.Modules.Admin.Dto.BloodBankNetworkDetailsDTO;
import com.yawarSoft.Modules.Admin.Dto.NetworkDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.BloodBankNetworkEntity;
import com.yawarSoft.Core.Entities.NetworkEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Admin.Dto.Request.BBNetworkRequestDTO;
import com.yawarSoft.Modules.Admin.Enums.NetworkBBStatus;
import com.yawarSoft.Modules.Admin.Mappers.BBNetworkMapper;
import com.yawarSoft.Modules.Admin.Repositories.BBNetworkRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BBNetworkService;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
import com.yawarSoft.Core.Utils.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Page<NetworkDTO> searchByNameWithActualBloodBank(Integer idBloodBank, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return networkRepository.searchByNameAndBloodBankIdAndStatus(
                        name,
                        idBloodBank,
                        NetworkBBStatus.ACTIVE.name(),
                        pageable
                )
                .map(network -> {
                    // Filtrar relaciones ACTIVE
                    network.setBloodBankRelations(
                            network.getBloodBankRelations().stream()
                                    .filter(rel -> NetworkBBStatus.ACTIVE.name().equals(rel.getStatus()))
                                    .collect(Collectors.toList())
                    );
                    return network;
                })
                .map(networkMapper::toDto)
                .map(dto -> {
                    // Ordenar por ID en el DTO
                    dto.getBloodBankDetails().sort(Comparator.comparing(BloodBankNetworkDetailsDTO::getId));
                    return dto;
                });
    }


    @Override
    public NetworkDTO getById(Integer id) {
        NetworkEntity entity = networkRepository.findByIdWithRelationsAndStatus(id, NetworkBBStatus.ACTIVE.name())
                .orElseThrow(() -> new ResourceNotFoundException("Red no encontrada con ID: " + id));
        entity.getBloodBankRelations().sort(Comparator.comparing(BloodBankNetworkEntity::getId).reversed());

        return networkMapper.toDto(entity);
    }

    @Override
    public void associateBloodBank(Integer networkId, Integer bloodBankId) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();
        NetworkEntity network = networkRepository.findById(networkId)
                .orElseThrow(() -> new RuntimeException("Red no encontrada"));
        BloodBankEntity bloodBank = bloodBankService.getBloodBankEntityById(bloodBankId)
                .orElseThrow(() -> new RuntimeException("Banco de sangre no encontrado"));

        BloodBankNetworkEntity relation = new BloodBankNetworkEntity();
        relation.setNetwork(network);
        relation.setBloodBank(bloodBank);
        relation.setStatus(NetworkBBStatus.ACTIVE.name());
        relation.setCreatedBy(userAuth);
        network.getBloodBankRelations().add(relation);

        networkRepository.save(network);
    }

    @Override
    public void disassociateBloodBank(Integer networkId, Integer bloodBankId) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();
        NetworkEntity network = networkRepository.findById(networkId)
                .orElseThrow(() -> new RuntimeException("Red no encontrada"));

        network.getBloodBankRelations().stream()
                .filter(relation -> relation.getBloodBank().getId().equals(bloodBankId) && relation.getStatus().equals("ACTIVE"))
                .findFirst()
                .ifPresent(relation -> {
                    relation.setStatus(NetworkBBStatus.INACTIVE.name());
                    relation.setDisassociatedAt(LocalDateTime.now());
                    relation.setDisassociatedBy(userAuth);
                });

        networkRepository.save(network);
    }

    @Override
    @Transactional
    public Integer createNetworkBB(BBNetworkRequestDTO bbNetworkRequestDTO) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();

        NetworkEntity networkEntity = NetworkEntity.builder()
                .name(bbNetworkRequestDTO.getName())
                .description(bbNetworkRequestDTO.getDescription())
                .status(NetworkBBStatus.ACTIVE.name())
                .createdAt(LocalDateTime.now())
                .createdBy(userAuth)
                .build();

        // Guarda para obtener ID y estado persistente
        NetworkEntity savedNetwork = networkRepository.save(networkEntity);

        // Inicializa lista si es null
        if (savedNetwork.getBloodBankRelations() == null) {
            savedNetwork.setBloodBankRelations(new ArrayList<>());
        }

        // Por cada banco de sangre, crea la relación y agrega en ambas direcciones
        bbNetworkRequestDTO.getIdBloodBanks().forEach(bloodBankId -> {
            BloodBankEntity bloodBank = BloodBankEntity.builder()
                    .id(bloodBankId)
                    .build();

            BloodBankNetworkEntity relation = BloodBankNetworkEntity.builder()
                    .network(savedNetwork)   // asigna la red guardada
                    .bloodBank(bloodBank)
                    .createdBy(userAuth)
                    .status(NetworkBBStatus.ACTIVE.name())
                    .build();

            // Agrega la relación a la lista de la red
            savedNetwork.getBloodBankRelations().add(relation);
        });

        networkRepository.save(savedNetwork);
        return savedNetwork.getId();
    }

    @Override
    @Transactional
    public Integer updateNetworkBB(Integer networkId, BBNetworkRequestDTO dto) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();
        LocalDateTime now = LocalDateTime.now();

        NetworkEntity network = networkRepository.findById(networkId)
                .orElseThrow(() -> new ResourceNotFoundException("Red no encontrada con ID: " + networkId));

        // 1. Actualiza campos simples
        network.setName(dto.getName());
        network.setDescription(dto.getDescription());
        network.setUpdatedAt(now);
        network.setUpdatedBy(userAuth);

        // 2. Prepara IDs actuales y futuros
        Set<Integer> newIds = dto.getIdBloodBanks();

        // Asegura que la lista esté inicializada
        if (network.getBloodBankRelations() == null) {
            network.setBloodBankRelations(new ArrayList<>());
        }

        // Mapa de relaciones actuales activas por ID de banco
        Map<Integer, BloodBankNetworkEntity> relacionesActualesActivas = network.getBloodBankRelations().stream()
                .filter(rel -> NetworkBBStatus.ACTIVE.name().equals(rel.getStatus()))
                .collect(Collectors.toMap(
                        rel -> rel.getBloodBank().getId(),
                        rel -> rel
                ));

        // 3. Desactivar los que ya no están
        for (Map.Entry<Integer, BloodBankNetworkEntity> entry : relacionesActualesActivas.entrySet()) {
            Integer idBanco = entry.getKey();
            BloodBankNetworkEntity rel = entry.getValue();

            if (!newIds.contains(idBanco)) {
                rel.setStatus(NetworkBBStatus.INACTIVE.name());
                rel.setDisassociatedAt(now);
                rel.setDisassociatedBy(userAuth);
            }
        }

        // 4. Agregar nuevos (si no existen ya)
        newIds.stream()
                .filter(id -> !relacionesActualesActivas.containsKey(id))
                .forEach(idNuevo -> {
                    BloodBankEntity banco = BloodBankEntity.builder().id(idNuevo).build();
                    BloodBankNetworkEntity nuevaRelacion = BloodBankNetworkEntity.builder()
                            .network(network)
                            .bloodBank(banco)
                            .createdAt(now)
                            .createdBy(userAuth)
                            .status(NetworkBBStatus.ACTIVE.name())
                            .build();
                    network.getBloodBankRelations().add(nuevaRelacion);
                });

        // 5. Guardar red y cascada de relaciones
        networkRepository.save(network);
        return network.getId();
    }

}
