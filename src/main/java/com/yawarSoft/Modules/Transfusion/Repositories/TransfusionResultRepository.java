package com.yawarSoft.Modules.Transfusion.Repositories;

import com.yawarSoft.Core.Entities.TransfusionResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfusionResultRepository extends JpaRepository<TransfusionResultEntity, Long> {
}
