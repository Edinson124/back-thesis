package com.yawarSoft.Modules.Storage.Repositories;

import com.yawarSoft.Core.Entities.BloodStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodStorageRepository extends JpaRepository<BloodStorageEntity,Integer> {
    @Modifying
    @Query(value = "INSERT INTO blood_storage (id) VALUES (:idBloodBank)", nativeQuery = true)
    void insertInitialStorage(@Param("idBloodBank") Integer idBloodBank);

}
