package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswerEntity, Long> {
}
