package com.yawarSoft.Modules.Transfusion.Repositories;

import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransfusionRequestRepository extends JpaRepository<TransfusionRequestEntity, Long>, JpaSpecificationExecutor<TransfusionRequestEntity> {
    Page<TransfusionRequestEntity> findByPatientId(Long patientId, Pageable pageable);
    @Query("SELECT p.bloodType, p.rhFactor FROM TransfusionRequestEntity t JOIN t.patient p WHERE t.id = :id")
    List<Object[]> findBloodTypeAndRhByTransfusionId(@Param("id") Long id);
}
