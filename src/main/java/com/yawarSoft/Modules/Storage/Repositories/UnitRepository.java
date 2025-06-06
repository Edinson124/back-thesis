package com.yawarSoft.Modules.Storage.Repositories;

import com.yawarSoft.Core.Entities.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long>, JpaSpecificationExecutor<UnitEntity> {
    List<UnitEntity> findByDonationId(Long donationId);

    @Modifying
    @Transactional
    @Query("UPDATE UnitEntity u SET u.status = :status WHERE u.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("UPDATE UnitEntity u SET u.bloodType = :bloodType WHERE u.donation.id = :idDonation")
    int updateBloodTypeByDonationId(@Param("idDonation") Long idDonation, @Param("bloodType") String bloodType);

    @Modifying
    @Query("UPDATE UnitEntity u " +
            "SET u.bloodBank.id = :destinationBankId, u.status = :status " +
            "WHERE u.id IN :unitIds")
    int updateUnitsBankByIds(@Param("unitIds") List<Long> unitIds,
                              @Param("destinationBankId") Integer destinationBankId,
                             @Param("status") String status);
}
