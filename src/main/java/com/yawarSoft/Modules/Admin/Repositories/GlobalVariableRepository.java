package com.yawarSoft.Modules.Admin.Repositories;

import com.yawarSoft.Core.Entities.GlobalVariablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlobalVariableRepository extends JpaRepository<GlobalVariablesEntity,Integer> {

    List<GlobalVariablesEntity> findAllByOrderByIdAsc();
    Optional<GlobalVariablesEntity> findByCode(String code);
}
