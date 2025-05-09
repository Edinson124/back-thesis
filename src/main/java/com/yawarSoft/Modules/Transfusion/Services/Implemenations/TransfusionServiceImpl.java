package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TranfusionListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionByPatientDTO;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionRequestMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.PatientRepository;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionService;
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

@Service
public class TransfusionServiceImpl implements TransfusionService {

    private final TransfusionRepository transfusionRepository;
    private final PatientRepository patientRepository;
    private final TransfusionRequestMapper transfusionRequestMapper;
    private final AuthenticatedUserService authenticatedUserService;
    private final HmacUtil hmacUtil;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;

    public TransfusionServiceImpl(TransfusionRepository transfusionRepository, PatientRepository patientRepository, TransfusionRequestMapper transfusionRequestMapper, AuthenticatedUserService authenticatedUserService, HmacUtil hmacUtil, AESGCMEncryptionUtil aesGCMEncryptionUtil) {
        this.transfusionRepository = transfusionRepository;
        this.patientRepository = patientRepository;
        this.transfusionRequestMapper = transfusionRequestMapper;
        this.authenticatedUserService = authenticatedUserService;
        this.hmacUtil = hmacUtil;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
    }

    @Override
    public Page<TransfusionByPatientDTO> getDonationsByDonor(String documentType, String documentNumber, int page, int size) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        Long patientId = patientRepository.findIdBySearchHash(searchHash).orElse(0L);
        if (patientId != 0L) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<TransfusionRequestEntity> donationsPage = transfusionRepository.findByPatientId(patientId, pageable);
            return donationsPage.map(transfusionRequestMapper::toTransfusionByPatientDto);
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

        Page<TransfusionRequestEntity> pageResult = transfusionRepository.findAll(spec, pageable);

        return pageResult.map(entity -> transfusionRequestMapper.toListDTO(entity, aesGCMEncryptionUtil));
    }

}
