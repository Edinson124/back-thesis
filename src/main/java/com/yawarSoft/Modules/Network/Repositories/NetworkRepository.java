package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.NetworkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkRepository extends JpaRepository<NetworkEntity,Integer> {
    @Query("""
        SELECT DISTINCT n FROM NetworkEntity n
        JOIN n.bloodBankRelations r
        WHERE r.status = :status
        AND r.bloodBank.id IN (:userBloodBankId)
        AND (:name IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:idBloodBank IS NULL OR EXISTS (
            SELECT 1 FROM BloodBankNetworkEntity r2
            WHERE r2.network.id = n.id AND r2.status = :status AND r2.bloodBank.id = :idBloodBank
        ))
    """)
    Page<NetworkEntity> findByUserBankAndFilters(
            @Param("userBloodBankId") Integer userBloodBankId,
            @Param("name") String name,
            @Param("idBloodBank") Integer idBloodBank,
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT DISTINCT n FROM NetworkEntity n " +
            "JOIN n.bloodBankRelations r " +
            "WHERE r.bloodBank.id = :bloodBankId " +
            "AND r.status = :status " +
            "AND n.status = :networkStatus")
    List<NetworkEntity> findNetworksByBloodBankRelation(
            @Param("bloodBankId") Integer bloodBankId,
            @Param("status") String relationStatus,
            @Param("networkStatus") String networkStatus);

}
