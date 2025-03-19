package com.yawarSoft.Repositories;

import com.yawarSoft.Entities.GlobalVariablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalVariableRepository extends JpaRepository<GlobalVariablesEntity,Integer> {
}
