package com.yawarSoft.Modules.Interoperability.Repositories;

import com.yawarSoft.Core.Entities.AuthExternalSystemEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AuthExternalSystemRepository extends JpaRepository<AuthExternalSystemEntity, Integer> {
    @EntityGraph(attributePaths = {"bloodBank"})
    AuthExternalSystemEntity findByBloodBank_Id(Integer idBloodBank);
}
