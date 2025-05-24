package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.NetworkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BBNetworkRepository extends JpaRepository<NetworkEntity,Integer> {
    @Query("""
        SELECT DISTINCT n FROM NetworkEntity n
        JOIN n.bloodBankRelations r
        WHERE r.status = :status
        AND (:name IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:idBloodBank IS NULL OR r.bloodBank.id = :idBloodBank)
    """)
    Page<NetworkEntity> searchByNameAndBloodBankIdAndStatus(
            @Param("name") String name,
            @Param("idBloodBank") Integer idBloodBank,
            @Param("status") String status,
            Pageable pageable
    );

    @Query("""
    SELECT DISTINCT n FROM NetworkEntity n
    LEFT JOIN FETCH n.bloodBankRelations r
    LEFT JOIN FETCH r.bloodBank
    WHERE n.id = :id AND r.status = :status
""")
    Optional<NetworkEntity> findByIdWithRelationsAndStatus(
            @Param("id") Integer id,
            @Param("status") String status
    );
}
