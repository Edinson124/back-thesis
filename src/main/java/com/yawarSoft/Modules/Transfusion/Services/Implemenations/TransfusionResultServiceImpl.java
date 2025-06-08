package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import com.yawarSoft.Core.Entities.TransfusionResultEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionResultCreateDTO;
import com.yawarSoft.Modules.Transfusion.Enums.TransfusionStatus;
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
    public TransfusionResultCreateDTO createTransfusionResult(Long idTransfusion, TransfusionResultRequestDTO request) {
        UserEntity userEntity = authenticatedUserService.getCurrentUser();
        TransfusionResultCreateDTO result = new TransfusionResultCreateDTO();
        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(idTransfusion)
                .orElseThrow(() -> new IllegalArgumentException("Transfusion no encontrado con id: " + idTransfusion));

        if (!transfusionEntity.getStatus().equals(TransfusionStatus.LIBERADA.getLabel())) {
            result.setId(-1L);
            result.setCreated(false);
            return result;
        }
        if (transfusionEntity.getTransfusionResult() != null) {
            result.setId(0L);
            result.setCreated(false);
            return result;
        }

        TransfusionResultEntity transfusionResultEntity = transfusionResultMapper.toEntityByRequest(request);
        transfusionResultEntity.setPatient(transfusionEntity.getPatient());
        transfusionResultEntity.setCreatedAt(LocalDateTime.now());
        transfusionResultEntity.setCreatedBy(userEntity);

        TransfusionResultEntity transfusionResultSaved = transfusionResultRepository.save(transfusionResultEntity);

        transfusionEntity.setTransfusionResult(transfusionResultSaved);
        transfusionRequestRepository.save(transfusionEntity);
        result.setId(transfusionResultSaved.getId() );
        result.setCreated(true);
        return result;
    }
}
