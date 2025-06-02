package com.yawarSoft.Modules.Laboratory.Repositories;

import com.yawarSoft.Core.Entities.HematologicalTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HematologicalTestRepository extends JpaRepository<HematologicalTestEntity, Long> {
}
