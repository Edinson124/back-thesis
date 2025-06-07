package com.yawarSoft.Modules.Storage.Repositories;

import com.yawarSoft.Core.Entities.BloodStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodStorageRepository extends JpaRepository<BloodStorageEntity,Integer> {
}
