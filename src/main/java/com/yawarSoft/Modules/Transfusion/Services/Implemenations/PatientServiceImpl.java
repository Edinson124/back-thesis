package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Modules.Transfusion.Repositories.PatientRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientEntity getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }
}
