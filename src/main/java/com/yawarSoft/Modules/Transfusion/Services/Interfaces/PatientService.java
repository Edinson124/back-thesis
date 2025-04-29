package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Core.Entities.PatientEntity;

public interface PatientService {
    PatientEntity getPatientById(Long id);
}
