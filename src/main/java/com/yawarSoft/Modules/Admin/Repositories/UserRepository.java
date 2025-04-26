package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import com.yawarSoft.Modules.Admin.Repositories.Projections.UserProjectionSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN u.role r WHERE " +
            "(:search IS NULL OR :search = '' OR u.firstName LIKE CONCAT('%', :search, '%') OR u.documentNumber LIKE CONCAT('%', :search, '%')) AND " +
            "(:role IS NULL OR r.name = :role) AND " +
            "(:status IS NULL OR u.status = :status) " +
            "ORDER BY u.firstName ASC")
    Page<UserEntity > findByFilters(@Param("search") String search, @Param("role") String role,
                                    @Param("status") UserStatus status, Pageable pageable);

    Page<UserEntity> findAll(Pageable pageable);
    boolean existsByDocumentNumberAndIdNot(String documentNumber, Integer id);
    boolean existsByDocumentNumber(String documentNumber);
    boolean existsByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);

    @Query("SELECT u.id AS id, " +
            "u.firstName AS firstName, " +
            "u.lastName AS lastName, " +
            "u.secondLastName AS secondLastName, " +
            "u.documentType AS documentType, " +
            "u.documentNumber AS documentNumber " +
            "FROM UserEntity u JOIN u.role r " +
            "WHERE r.name = 'Medico' AND u.bloodBank.id = :id AND u.status = 'ACTIVE'")
    List<UserProjectionSelect> getUserRoleMedicByBloodBank(@Param("id") Integer bloodBankId);
}
