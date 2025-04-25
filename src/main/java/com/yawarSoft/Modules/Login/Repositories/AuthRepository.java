package com.yawarSoft.Modules.Login.Repositories;

import com.yawarSoft.Core.Entities.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity,Integer> {
    Optional<AuthEntity> findByUsername(String username);
}
