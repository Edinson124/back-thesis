package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.NetworkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BBNetworkRepository extends JpaRepository<NetworkEntity,Integer> {
    @Query("""
        SELECT DISTINCT n FROM NetworkEntity n
        JOIN n.bloodBankRelations r
        WHERE (:name IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:idBloodBank IS NULL OR (r.bloodBank.id = :idBloodBank AND r.status = :status))
        AND r.status = :status
    """)
    Page<NetworkEntity> searchByNameAndBloodBankIdAndStatus(
            @Param("name") String name,
            @Param("idBloodBank") Integer idBloodBank,
            @Param("status") String status,
            Pageable pageable
    );
}
