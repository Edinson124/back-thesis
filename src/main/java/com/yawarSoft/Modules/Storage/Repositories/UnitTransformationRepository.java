package com.yawarSoft.Modules.Storage.Repositories;

import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Entities.UnitStorageEntity;
import com.yawarSoft.Core.Entities.UnitTransformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnitTransformationRepository extends JpaRepository<UnitTransformationEntity, Long> {
    @Query("SELECT ut.generatedUnit FROM UnitTransformationEntity ut WHERE ut.originUnit.id = :idUnit")
    List<UnitEntity> findGeneratedUnitsByOriginUnitId(@Param("idUnit") Long idUnit);

}
