package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.ShipmentRequestEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ShipmentRequestRepository extends JpaRepository<ShipmentRequestEntity,Integer>, JpaSpecificationExecutor<ShipmentRequestEntity> {
    @EntityGraph(attributePaths = "unitsRequest")
    @Query("SELECT sr FROM ShipmentRequestEntity sr WHERE sr.id = :id")
    Optional<ShipmentRequestEntity> findByIdWithUnits(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE ShipmentRequestEntity s SET s.status = :status WHERE s.id = :id")
    int updateStatusById(@Param("id") Integer id, @Param("status") String status);
}
