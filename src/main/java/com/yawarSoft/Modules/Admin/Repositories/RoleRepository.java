package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {

    //Se envia listado de string y se retorna solo los que conincidan en la base de datos
    List<RoleEntity> findRoleEntitiesByNameIn(List<String> rolesName);
    List<RoleEntity> findByStatusOrderByNameAsc(String status);

    @Query("SELECT DISTINCT r FROM RoleEntity r WHERE " +
            "(:search IS NULL OR :search = '' OR r.name LIKE CONCAT('%', :search, '%')) AND " +
            "(:status IS NULL OR r.status = :status) " +
            "ORDER BY r.id ASC")
    Page<RoleEntity> findByFilters(@Param("search") String search,@Param("status") String status, Pageable pageable);

    Optional<RoleEntity> findById(Long id);
    boolean existsByName(String name);
}