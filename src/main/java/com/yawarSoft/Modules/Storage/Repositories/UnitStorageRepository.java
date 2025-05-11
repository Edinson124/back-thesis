package com.yawarSoft.Modules.Storage.Repositories;

import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Entities.UnitStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitStorageRepository extends JpaRepository<UnitStorageEntity, Long> {
}
