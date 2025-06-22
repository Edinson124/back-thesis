package com.yawarSoft.Modules.Interoperability.Repositories;

import com.yawarSoft.Core.Entities.ExternalSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalSystemRepository extends JpaRepository<ExternalSystemEntity, Integer> {
    Optional<ExternalSystemEntity> findByBloodBank_Id(Integer idBloodBank);
}
