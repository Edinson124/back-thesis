package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.ShipmentXUnitEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentAssingmentRepository extends JpaRepository<ShipmentXUnitEntity,Integer> {
    @EntityGraph(attributePaths = {"bloodUnit"})
    @Query("SELECT t FROM ShipmentXUnitEntity t WHERE t.id = :id")
    Optional<ShipmentXUnitEntity> findByIdWithUnit(@Param("id") Integer id);
}
