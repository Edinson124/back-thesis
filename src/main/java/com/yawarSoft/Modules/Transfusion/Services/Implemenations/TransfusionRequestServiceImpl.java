package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Modules.Transfusion.Dto.Response.ExistTransfusionDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionDetailDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TranfusionListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionByPatientDTO;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionRequestMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.PatientRepository;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionRequestRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionRequestService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransfusionRequestServiceImpl implements TransfusionRequestService {

    private final TransfusionRequestRepository transfusionRequestRepository;
    private final PatientRepository patientRepository;
    private final TransfusionRequestMapper transfusionRequestMapper;
    private final AuthenticatedUserService authenticatedUserService;
    private final HmacUtil hmacUtil;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;

    public TransfusionRequestServiceImpl(TransfusionRequestRepository transfusionRequestRepository, PatientRepository patientRepository, TransfusionRequestMapper transfusionRequestMapper, AuthenticatedUserService authenticatedUserService, HmacUtil hmacUtil, AESGCMEncryptionUtil aesGCMEncryptionUtil) {
        this.transfusionRequestRepository = transfusionRequestRepository;
        this.patientRepository = patientRepository;
        this.transfusionRequestMapper = transfusionRequestMapper;
        this.authenticatedUserService = authenticatedUserService;
        this.hmacUtil = hmacUtil;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
    }

    @Override
    public Page<TransfusionByPatientDTO> getTranfusionByPatient(String documentType, String documentNumber, int page, int size) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        Long patientId = patientRepository.findIdBySearchHash(searchHash).orElse(0L);
        if (patientId != 0L) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<TransfusionRequestEntity> transfusionPage = transfusionRequestRepository.findByPatientId(patientId, pageable);
            return transfusionPage.map(transfusionRequestMapper::toTransfusionByPatientDto);
        }else{
            throw new IllegalArgumentException("Donante no encontrada con documento");
        }
    }

    @Override
    public Page<TranfusionListDTO> getTransfusions(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, Long code, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer bloodBankId = userAuthenticated.getBloodBank().getId();

        Specification<TransfusionRequestEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por banco de sangre
            predicates.add(cb.equal(root.get("bloodBank").get("id"), bloodBankId));

            // Filtro por fecha
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(root.get("date"), startEntryDate, endEntryDate));
            } else if (startEntryDate != null) {
                predicates.add(cb.equal(root.get("date"), startEntryDate));
            } else if (endEntryDate != null) {
                predicates.add(cb.equal(root.get("date"), endEntryDate));
            }

            // Filtro por status (si viene)
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // Filtro por code (id) si viene
            if (code != null) {
                predicates.add(cb.equal(root.get("id"), code));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<TransfusionRequestEntity> pageResult = transfusionRequestRepository.findAll(spec, pageable);

        return pageResult.map(entity -> transfusionRequestMapper.toListDTO(entity, aesGCMEncryptionUtil));
    }

    @Override
    public ExistTransfusionDTO existsByCode(Long id) {
        ExistTransfusionDTO result = new ExistTransfusionDTO();
        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(id).orElse(null);
        if (transfusionEntity == null) {
            result.setTransfusionActualExists(false);
            result.setCanViewTransfusion(false);
            result.setTransfusionId(null);
            result.setTransfusionResultId(null);
        } else {
            UserEntity userEntity = authenticatedUserService.getCurrentUser();
            // Verificar si el banco de sangre de la donación es el mismo que el banco de sangre del usuario
            Boolean canViewTransfusion = transfusionEntity.getBloodBank().getId().equals(userEntity.getBloodBank().getId());
            result.setTransfusionActualExists(true);
            result.setCanViewTransfusion(canViewTransfusion);
            result.setTransfusionId(transfusionEntity.getId());
            if (transfusionEntity.getTransfusionResult() != null) {
                result.setTransfusionResultId(transfusionEntity.getTransfusionResult().getId());
            } else {
                result.setTransfusionResultId(null);
            }
        }
        return result;
    }

    @Override
    public TransfusionDetailDTO getDetailTransfusion(Long id) {
        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(id).orElse(null);
        return transfusionRequestMapper.toDetailDTO(transfusionEntity, aesGCMEncryptionUtil);
    }

}
