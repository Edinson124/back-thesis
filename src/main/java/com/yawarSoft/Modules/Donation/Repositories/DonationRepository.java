package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.DonationEntity;
import com.yawarSoft.Modules.Donation.Enums.DonationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {

    Page<DonationEntity> findByDonorId(Long donorId, Pageable pageable);
    Optional<DonationEntity> findByDonorIdAndStatus(Long donorId, String status);
    Optional<DonationEntity> findTopByDonorIdOrderByIdDesc(Long donorId);

    @Modifying
    @Transactional
    @Query("UPDATE DonationEntity d SET d.interviewAnswer.id = :interviewAnswerId WHERE d.id = :donationId")
    int updateInterviewAnswer(@Param("donationId") Long donationId, @Param("interviewAnswerId") Long interviewAnswerId);

    @Modifying
    @Transactional
    @Query("UPDATE DonationEntity d SET d.physicalAssessment.id = :physicalAssessmentId WHERE d.id = :donationId")
    int updatePhysicalAssessment(@Param("donationId") Long donationId, @Param("physicalAssessmentId") Long physicalAssessmentId);

    @Modifying
    @Transactional
    @Query("UPDATE DonationEntity d SET d.bloodExtraction.id = :bloodExtractionId WHERE d.id = :donationId")
    int updateBloodExtraction(@Param("donationId") Long donationId, @Param("bloodExtractionId") Long bloodExtractionId);

    @Modifying
    @Transactional
    @Query("UPDATE DonationEntity d SET d.serologyTest.id = :serologyTestId WHERE d.id = :donationId")
    int updateSerologyTest(@Param("donationId") Long donationId, @Param("serologyTestId") Long serologyTestId);

    @Modifying
    @Transactional
    @Query("UPDATE DonationEntity d SET d.hematologicalTest.id = :hematologicalTestId WHERE d.id = :donationId")
    int updateHematologicalTest(@Param("donationId") Long donationId, @Param("hematologicalTestId") Long hematologicalTestId);

    @Query("SELECT d.status FROM DonationEntity d WHERE d.id = :id")
    String findStatusById(@Param("id") Long id);
}
