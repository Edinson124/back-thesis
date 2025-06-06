package com.yawarSoft.Modules.Network.Repositories;

import com.yawarSoft.Core.Entities.ShipmentRequestDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRequestDetailRepository extends JpaRepository<ShipmentRequestDetailEntity,Integer> {
}
