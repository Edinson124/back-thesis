package com.yawarSoft.Modules.Storage.Repositories;

import com.yawarSoft.Core.Entities.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long>, JpaSpecificationExecutor<UnitEntity> {
    List<UnitEntity> findByDonationId(Long donationId);
}
