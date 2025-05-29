package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Enums.DonorStatus;
import com.yawarSoft.Modules.Transfusion.Dto.PatientGetDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientDocumentRequest;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientRequestDTO;
import com.yawarSoft.Modules.Transfusion.Mappers.PatientMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.PatientRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public Boolean existsByDocument(String documentType, String documentNumber) {
        String docInfoDonor = documentType + '|' + documentNumber;
        String newSearchHash = hmacUtil.generateHmac(docInfoDonor);
        return patientRepository.existsBySearchHash(newSearchHash);
    }

    @Override
    public PatientGetDTO getPatient(String documentType, String documentNumber) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);
        PatientEntity patientEntity = patientRepository.findBySearchHash(searchHash)
                .orElse(null);

        return patientMapper.toGetDto(patientEntity, aesGCMEncryptionUtil);
    }

    @Override
    public Long createPatient(PatientRequestDTO patientRequestDTO) {
        String combinedInfo = patientRequestDTO.getDocumentType() + '|' + patientRequestDTO.getDocumentNumber();
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        if (patientRepository.existsBySearchHash(searchHash)) {
            throw new IllegalArgumentException("El documento ya existe en el sistema");
        }
        PatientEntity patientEntity = patientMapper.toEntity(patientRequestDTO, aesGCMEncryptionUtil);
        patientEntity.setSearchHash(searchHash);
        patientEntity.setCreatedBy(UserUtils.getAuthenticatedUser());
        PatientEntity patientSaved = patientRepository.save(patientEntity);
        return patientSaved.getId();
    }

    @Override
    public PatientGetDTO updatePatient(PatientRequestDTO patientRequestDTO) {
        String docInfoPatient = patientRequestDTO.getDocumentType() + '|' + patientRequestDTO.getDocumentNumber();
        String searchHash = hmacUtil.generateHmac(docInfoPatient);

        PatientEntity existingPatient = patientRepository.findBySearchHash(searchHash)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con documento: "
                        + patientRequestDTO.getDocumentType() + " - " + patientRequestDTO.getDocumentNumber()));
        patientMapper.updateEntityFromDto(patientRequestDTO,existingPatient,aesGCMEncryptionUtil);
        existingPatient.setUpdatedBy(UserUtils.getAuthenticatedUser());
        existingPatient.setUpdatedAt(LocalDateTime.now());

        patientRepository.save(existingPatient);
        return patientMapper.toGetDto(existingPatient,aesGCMEncryptionUtil);
    }

}
