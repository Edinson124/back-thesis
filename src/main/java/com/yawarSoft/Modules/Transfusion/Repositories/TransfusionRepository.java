package com.yawarSoft.Modules.Transfusion.Repositories;

import com.yawarSoft.Core.Entities.DonationEntity;
import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfusionRepository extends JpaRepository<TransfusionRequestEntity, Long>, JpaSpecificationExecutor<TransfusionRequestEntity> {
    Page<TransfusionRequestEntity> findByPatientId(Long patientId, Pageable pageable);
}
