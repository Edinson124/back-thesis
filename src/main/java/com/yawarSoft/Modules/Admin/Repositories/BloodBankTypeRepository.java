package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.BloodBankTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankTypeRepository extends JpaRepository<BloodBankTypeEntity,Integer> {
    List<BloodBankTypeEntity> findByStatus(String status);
}
