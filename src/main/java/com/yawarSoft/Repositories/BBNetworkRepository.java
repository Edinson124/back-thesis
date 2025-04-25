package com.yawarSoft.Repositories;

import com.yawarSoft.Core.Entities.NetworkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BBNetworkRepository extends JpaRepository<NetworkEntity,Integer> {
    @Query("SELECT DISTINCT n FROM NetworkEntity n " +
            "JOIN FETCH n.bloodBankRelations b " +
            "WHERE n.name LIKE %:name% AND b.status = :status ")
    Page<NetworkEntity> findNetworksByStatus(@Param("name") String name, @Param("status") String status, Pageable pageable);

}
