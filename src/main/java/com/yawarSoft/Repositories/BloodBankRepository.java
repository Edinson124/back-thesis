package com.yawarSoft.Repositories;

import com.yawarSoft.Dto.BloodBankProjection;
import com.yawarSoft.Entities.BloodBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloodBankRepository extends JpaRepository<BloodBankEntity,Long> {

    @Query("SELECT e.id AS id, e.name AS name FROM BloodBankEntity e")
    List<BloodBankProjection> getBloodBankSelect();
}
