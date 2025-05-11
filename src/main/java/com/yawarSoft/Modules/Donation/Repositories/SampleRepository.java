package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository  extends JpaRepository<SampleEntity, Long> {
    List<SampleEntity> findByDonationId(Long idDonation);
}
