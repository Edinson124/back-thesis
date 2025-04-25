package com.yawarSoft.Repositories;

import com.yawarSoft.Core.Entities.GlobalVariablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalVariableRepository extends JpaRepository<GlobalVariablesEntity,Integer> {

    List<GlobalVariablesEntity> findAllByOrderByIdAsc();
}
