package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.InterviewQuestionStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewQuestionStructureRepository extends JpaRepository<InterviewQuestionStructureEntity, Long> {
    Optional<InterviewQuestionStructureEntity> findByStatus(String status);
}
