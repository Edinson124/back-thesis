package com.yawarSoft.Modules.Transfusion.Repositories;

import com.yawarSoft.Core.Entities.TransfusionAssignmentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransfusionAssignmentRepository extends JpaRepository<TransfusionAssignmentEntity, Long> {
    @EntityGraph(attributePaths = {"bloodUnit"})
    @Query("SELECT t FROM TransfusionAssignmentEntity t WHERE t.id = :id")
    Optional<TransfusionAssignmentEntity> findByIdWithUnit(@Param("id") Long id);
}
