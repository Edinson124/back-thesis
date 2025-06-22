package com.yawarSoft.Modules.Interoperability.Repositories;

import com.yawarSoft.Core.Entities.ExternalEndpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalEndpointRepository  extends JpaRepository<ExternalEndpointEntity, Integer> {
    Optional<ExternalEndpointEntity> findByExternalSystem_IdAndResourceNameAndInteractionType
            (Integer id, String resource, String interaction);
}
