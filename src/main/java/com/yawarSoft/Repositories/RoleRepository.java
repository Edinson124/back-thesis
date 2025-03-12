package com.yawarSoft.Repositories;

import com.yawarSoft.Entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    //Se envia listado de string y se retorna solo los que conincidan en la base de datos
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> rolesName);
    Optional<RoleEntity> findById(Long id);
}