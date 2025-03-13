package com.yawarSoft.Repositories;

import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN u.roles r WHERE " +
            "(:search IS NULL OR :search = '' OR u.firstNames LIKE CONCAT('%', :search, '%') OR u.documentNumber LIKE CONCAT('%', :search, '%')) AND " +
            "(:role IS NULL OR r.name = :role) AND " +
            "(:status IS NULL OR u.status = :status) " +
            "ORDER BY u.firstNames ASC")
    Page<UserEntity > findByFilters(@Param("search") String search, @Param("role") String role,
                                    @Param("status") UserStatus status, Pageable pageable);

    Optional<UserEntity> findUserEntityByUsername(String username);
    Page<UserEntity> findAll(Pageable pageable);
}
