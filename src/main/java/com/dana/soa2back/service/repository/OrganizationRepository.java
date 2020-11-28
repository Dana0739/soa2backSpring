package com.dana.soa2back.service.repository;

import com.dana.soa2back.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository  extends JpaRepository<Organization, Long> {
    @Override
    Optional<Organization> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);
}
