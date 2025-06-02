package com.yawarSoft.Modules.Laboratory.Repositories;

import com.yawarSoft.Core.Entities.SerologyTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerologyTestRepository extends JpaRepository<SerologyTestEntity, Long> {
}
