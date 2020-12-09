package com.dana.soa2back.service.repository;

import com.dana.soa2back.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Override
    List<Organization> findAll();

    @Override
    Optional<Organization> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);
}
