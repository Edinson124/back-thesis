package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.DonorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<DonorEntity, Long> {
    boolean existsBySearchHash(String searchHash);
    Optional<DonorEntity> findBySearchHash(String searchHash);

    @Query("SELECT d.id FROM DonorEntity d WHERE d.searchHash = :searchHash")
    Optional<Long> findIdBySearchHash(@Param("searchHash") String searchHash);

    @Modifying
    @Transactional
    @Query("UPDATE DonorEntity d SET d.status = :status, d.deferralEndDate = :deferralEndDate, d.deferralReason = :deferralReason WHERE d.id = :id")
    int  updateDonorDeferralById(@Param("id") Long id,
                                 @Param("status") String status,
                                 @Param("deferralEndDate") LocalDate deferralEndDate,
                                 @Param("deferralReason") String deferralReason);

    @Modifying
    @Transactional
    @Query("UPDATE DonorEntity d SET d.status =:status1, " +
            "d.deferralEndDate = null,d.deferralReason = null " +
            "WHERE d.status =:status2 AND d.deferralEndDate <= :today")
    int updateDeferredDonorsToEligible(String status1,String status2, LocalDate today);

}