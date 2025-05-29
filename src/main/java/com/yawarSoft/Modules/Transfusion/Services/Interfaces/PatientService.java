package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.PatientGetDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientDocumentRequest;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientRequestDTO;

public interface PatientService {
    PatientEntity getPatientById(Long id);
    String getFullNamePatient(String documentType, String documentNumber);
    Long getIdPatient(String documentType, String documentNumber);
    Boolean existsByDocument(String documentType, String documentNumber);
    PatientGetDTO getPatient(String documentType, String documentNumber);
    Long createPatient(PatientRequestDTO patientRequestDTO);

    PatientGetDTO updatePatient(PatientRequestDTO request);
}
