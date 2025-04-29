package com.yawarSoft.Modules.Transfusion.Repositories;

import com.yawarSoft.Core.Entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
