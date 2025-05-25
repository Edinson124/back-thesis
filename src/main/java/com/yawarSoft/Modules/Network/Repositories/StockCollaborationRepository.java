package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockCollaborationRepository extends JpaRepository<UnitEntity,Long>, JpaSpecificationExecutor<UnitEntity> {
}
