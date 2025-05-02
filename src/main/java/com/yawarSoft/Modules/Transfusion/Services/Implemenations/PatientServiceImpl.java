package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Modules.Transfusion.Dto.PatientGetDTO;
import com.yawarSoft.Modules.Transfusion.Mappers.PatientMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.PatientRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HmacUtil hmacUtil;
    private final PatientMapper patientMapper;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;

    public PatientServiceImpl(PatientRepository patientRepository, HmacUtil hmacUtil, PatientMapper patientMapper, AESGCMEncryptionUtil aesGCMEncryptionUtil) {
        this.patientRepository = patientRepository;
        this.hmacUtil = hmacUtil;
        this.patientMapper = patientMapper;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
    }

    @Override
    public PatientEntity getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public String getFullNamePatient(String documentType, String documentNumber) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);
            PatientEntity patientEntity = patientRepository.findBySearchHash(searchHash).orElse(null);

        if (patientEntity == null) {
            return null;
        }

        PatientGetDTO patientGetDTO = patientMapper.toGetDto(patientEntity, aesGCMEncryptionUtil);
        return String.join(" ",
                patientGetDTO.getFirstName(),
                patientGetDTO.getLastName(),
                patientGetDTO.getSecondLastName());
    }

    @Override
    public Long getIdPatient(String documentType, String documentNumber) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        return patientRepository.findIdBySearchHash(searchHash)
                .orElse(0L);
    }

}
