package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.BloodBankNetworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankNetworkRepository extends JpaRepository<BloodBankNetworkEntity, Integer> {

    boolean existsByNetworkIdAndBloodBankIdAndStatus(Integer networkId, Integer bloodBankId, String status);
    List<BloodBankNetworkEntity> findByNetworkIdAndStatusOrderByBloodBank_NameAsc(Integer networkId, String status);

    @Query("""
        SELECT COUNT(bbn)
        FROM BloodBankNetworkEntity bbn
        WHERE bbn.network.id = :networkId
          AND bbn.status = 'ACTIVE'
          AND bbn.bloodBank.id IN (:userBankId, :targetBankId)
    """)
    long countActiveBanksInNetwork(@Param("networkId") Integer networkId,
                                   @Param("userBankId") Integer userBankId,
                                   @Param("targetBankId") Integer targetBankId);
}
