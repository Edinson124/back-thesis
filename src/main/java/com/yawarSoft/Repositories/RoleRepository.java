package com.yawarSoft.Repositories;

import com.yawarSoft.Entities.RoleEntity;
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
    Optional<RoleEntity> findById(Long id);
    @Query("SELECT r FROM RoleEntity r WHERE r.bloodBankType.id = :bloodBankTypeId")
    List<RoleEntity> findRolesByBloodBankTypeId(@Param("bloodBankTypeId") Integer bloodBankTypeId);
}