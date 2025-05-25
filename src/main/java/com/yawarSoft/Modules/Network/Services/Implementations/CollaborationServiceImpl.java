package com.yawarSoft.Modules.Network.Services.Implementations;

import com.yawarSoft.Core.Entities.BloodBankNetworkEntity;
import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Admin.Enums.NetworkBBStatus;
import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Response.OptionBloodBankNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.Response.StockNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.UnitCollaborationTableDto;
import com.yawarSoft.Modules.Network.Mappers.NetworkCollaborationMapper;
import com.yawarSoft.Modules.Network.Mappers.UnitCollaborationMapper;
import com.yawarSoft.Modules.Network.Repositories.BloodBankNetworkRepository;
import com.yawarSoft.Modules.Network.Repositories.NetworkRepository;
import com.yawarSoft.Modules.Network.Repositories.StockCollaborationRepository;
import com.yawarSoft.Modules.Network.Services.Interfaces.CollaborationService;
import com.yawarSoft.Modules.Storage.Enums.UnitStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollaborationServiceImpl implements CollaborationService {

    private final AuthenticatedUserService authenticatedUserService;
    private final NetworkRepository networkRepository;
    private final StockCollaborationRepository stockCollaborationRepository;
    private final BloodBankNetworkRepository bloodBankNetworkRepository;
    private final NetworkCollaborationMapper networkCollaborationMapper;
    private final UnitCollaborationMapper unitCollaborationMapper;

    public CollaborationServiceImpl(AuthenticatedUserService authenticatedUserService, NetworkRepository networkRepository, StockCollaborationRepository stockCollaborationRepository, BloodBankNetworkRepository bloodBankNetworkRepository, NetworkCollaborationMapper networkCollaborationMapper, UnitCollaborationMapper unitCollaborationMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.networkRepository = networkRepository;
        this.stockCollaborationRepository = stockCollaborationRepository;
        this.bloodBankNetworkRepository = bloodBankNetworkRepository;
        this.networkCollaborationMapper = networkCollaborationMapper;
        this.unitCollaborationMapper = unitCollaborationMapper;
    }


    @Override
    public Page<NetworkCollaborationDTO> searchNetworksByUserAndOptionalFilters(String name, Integer idBloodBank, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserEntity userEntity = authenticatedUserService.getCurrentUser();
        Integer userBloodBankId = userEntity.getBloodBank().getId();
        String status = NetworkBBStatus.ACTIVE.name();

        return networkRepository.findByUserBankAndFilters(
                        userBloodBankId,
                        name,
                        idBloodBank,
                        status,
                        pageable
                )
                .map(network -> {
                    // Filtrar relaciones ACTIVE
                    network.setBloodBankRelations(
                            network.getBloodBankRelations().stream()
                                    .filter(rel -> status.equals(rel.getStatus()))
                                    .collect(Collectors.toList())
                    );
                    return network;
                })
                .map(networkCollaborationMapper::toDto)
                .map(dto -> {
                    // Ordenar por ID en el DTO
                    dto.getBloodBankDetails().sort(Comparator.comparing(BloodBankNetworkCollaborationDTO::getId));
                    return dto;
                });
    }

    @Override
    public StockNetworkDTO getUnitsStock(Integer idBloodBank, Integer idNetwork, int page, int size,
                                         LocalDate startEntryDate, LocalDate endEntryDate,
                                         LocalDate startExpirationDate, LocalDate endExpirationDate,
                                         String bloodType, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer userBankId = userAuthenticated.getBloodBank().getId();

        long activeBanks = bloodBankNetworkRepository.countActiveBanksInNetwork(idNetwork,
                userBankId, idBloodBank);

        boolean canViewStock = (activeBanks == 2);

        if (!canViewStock) {
            return StockNetworkDTO.builder()
                    .canViewUser(false)
                    .unitsStock(Page.empty())
                    .build();
        }

        Specification<UnitEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Fecha de vencimiento
            if (startExpirationDate != null && endExpirationDate != null) {
                predicates.add(cb.between(root.get("expirationDate"), startExpirationDate, endExpirationDate));
            } else if (startExpirationDate != null) {
                predicates.add(cb.equal(root.get("expirationDate"), startExpirationDate));
            } else if (endExpirationDate != null) {
                predicates.add(cb.equal(root.get("expirationDate"), endExpirationDate));
            }

            // Fecha de ingreso
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(root.get("entryDate"), startEntryDate, endEntryDate));
            } else if (startEntryDate != null) {
                predicates.add(cb.equal(root.get("entryDate"), startEntryDate));
            } else if (endEntryDate != null) {
                predicates.add(cb.equal(root.get("entryDate"), endEntryDate));
            }
            if (bloodType != null && !bloodType.isBlank()) {
                predicates.add(cb.equal(root.get("bloodType"), bloodType));
            }

            predicates.add(cb.equal(root.get("status"), UnitStatus.SUITABLE.getLabel()));

            if (type != null && !type.isBlank()) {
                predicates.add(cb.equal(root.get("unitType"), type));
            }

            // Filtrar por unidades en cuarentena (status = 'Disponible')
            predicates.add(cb.equal(root.get("bloodBank").get("id"), idBloodBank));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<UnitEntity> unitsPage = stockCollaborationRepository.findAll(spec, pageable);
        Page<UnitCollaborationTableDto> mappedPage = unitsPage.map(unitCollaborationMapper::toTableDTO);
        return StockNetworkDTO.builder()
                .canViewUser(true)
                .unitsStock(mappedPage)
                .build();

    }

    @Override
    public OptionBloodBankNetworkDTO getBBOptionsNetwork(Integer networkId) {
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer userBankId = userAuthenticated.getBloodBank().getId();

        boolean isActive = bloodBankNetworkRepository.existsByNetworkIdAndBloodBankIdAndStatus(
                networkId, userBankId, NetworkBBStatus.ACTIVE.name());

        OptionBloodBankNetworkDTO dto = new OptionBloodBankNetworkDTO();
        dto.setCanViewUser(isActive);

        if (!isActive) {
            dto.setBloodBanks(Collections.emptyList());
            return dto;
        }
        List<BloodBankNetworkEntity> activeNetworkBanks = bloodBankNetworkRepository
                .findByNetworkIdAndStatusOrderByBloodBank_NameAsc(networkId, NetworkBBStatus.ACTIVE.name());

        List<BloodBankNetworkCollaborationDTO> mapped = activeNetworkBanks.stream()
                .map(networkCollaborationMapper::toDetailsDto)
                .collect(Collectors.toList());

        dto.setBloodBanks(mapped);
        return dto;
    }

}
