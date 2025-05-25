package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.ShipmentRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRequestRepository extends JpaRepository<ShipmentRequestEntity,Integer>, JpaSpecificationExecutor<ShipmentRequestEntity> {
}
