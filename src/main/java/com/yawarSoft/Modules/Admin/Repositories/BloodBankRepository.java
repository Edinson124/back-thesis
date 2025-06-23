package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBankEntity,Integer>, JpaSpecificationExecutor<BloodBankEntity> {

    @Query("""
    SELECT DISTINCT u FROM BloodBankEntity u
    WHERE
        (:name IS NULL OR :name = '' OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:region IS NULL OR u.region = :region)
    AND (:province IS NULL OR u.province = :province)
    AND (:district IS NULL OR u.district = :district)
    AND u.isInternal = true
    ORDER BY u.name ASC""")
    Page<BloodBankEntity> findByFilters(@Param("name") String name, @Param("region") String region, @Param("province") String province,
                                   @Param("district") String district, Pageable pageable);

    @Query("SELECT e.id AS id, e.name AS name, e.bloodBankType.name AS bloodBankType " +
            "FROM BloodBankEntity e " +
            "WHERE e.status = 'ACTIVE' AND e.isInternal = true order by e.name")
    List<BloodBankProjectionSelect> getBloodBankSelectInternal();

    @Query("SELECT e.id AS id, e.name AS name, e.bloodBankType.name AS bloodBankType " +
            "FROM BloodBankEntity e " +
            "WHERE e.status = 'ACTIVE' order by e.name")
    List<BloodBankProjectionSelect> getBloodBankSelectAll();

    boolean existsByName(String name);
}
