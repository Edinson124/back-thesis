package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.PhysicalAssessmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalAssessmentRepository extends JpaRepository<PhysicalAssessmentEntity, Long> {
}
