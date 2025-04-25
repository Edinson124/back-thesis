package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBankEntity,Integer> {

    @Query("SELECT e.id AS id, e.name AS name, e.bloodBankType.id AS bloodBankTypeId FROM BloodBankEntity e")
    List<BloodBankProjectionSelect> getBloodBankSelect();

}
