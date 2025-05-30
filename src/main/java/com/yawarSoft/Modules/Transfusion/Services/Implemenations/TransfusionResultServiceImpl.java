package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import com.yawarSoft.Core.Entities.TransfusionResultEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequestDTO;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionResultMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionRequestRepository;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionResultRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransfusionResultServiceImpl implements TransfusionResultService {

    private final TransfusionResultRepository transfusionResultRepository;
    private final TransfusionRequestRepository transfusionRequestRepository;
    private final TransfusionResultMapper transfusionResultMapper;
    private final AuthenticatedUserService authenticatedUserService;

    public TransfusionResultServiceImpl(TransfusionResultRepository transfusionResultRepository, TransfusionRequestRepository transfusionRequestRepository, TransfusionResultMapper transfusionResultMapper, AuthenticatedUserService authenticatedUserService) {
        this.transfusionResultRepository = transfusionResultRepository;
        this.transfusionRequestRepository = transfusionRequestRepository;
        this.transfusionResultMapper = transfusionResultMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional
    @Override
    public Long createTransfusionResult(Long idTransfusion, TransfusionResultRequestDTO request) {
        UserEntity userEntity = authenticatedUserService.getCurrentUser();
        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(idTransfusion)
                .orElseThrow(() -> new IllegalArgumentException("Transfusion no encontrado con id: " + idTransfusion));

        if (transfusionEntity.getTransfusionResult() != null) {
            return 0L;
        }

        TransfusionResultEntity transfusionResultEntity = transfusionResultMapper.toEntityByRequest(request);
        transfusionResultEntity.setPatient(transfusionEntity.getPatient());
        transfusionResultEntity.setCreatedAt(LocalDateTime.now());
        transfusionResultEntity.setCreatedBy(userEntity);

        TransfusionResultEntity transfusionResultSaved = transfusionResultRepository.save(transfusionResultEntity);

        transfusionEntity.setTransfusionResult(transfusionResultSaved);
        transfusionRequestRepository.save(transfusionEntity);

        return transfusionResultSaved.getId();
    }
}
