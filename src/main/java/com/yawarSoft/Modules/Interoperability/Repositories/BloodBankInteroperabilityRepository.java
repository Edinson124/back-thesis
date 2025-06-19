package com.yawarSoft.Modules.Interoperability.Repositories;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BloodBankInteroperabilityRepository extends JpaRepository<BloodBankEntity,Integer>, JpaSpecificationExecutor<BloodBankEntity> {
    Page<BloodBankEntity> findByStatusAndIsInternal(String status, Boolean isInternal, Pageable pageable);

}
