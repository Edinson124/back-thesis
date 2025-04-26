package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.DonorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<DonorEntity, Long> {
    boolean existsBySearchHash(String searchHash);
    Optional<DonorEntity> findBySearchHash(String searchHash);
}