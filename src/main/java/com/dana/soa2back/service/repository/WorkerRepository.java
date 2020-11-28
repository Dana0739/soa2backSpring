package com.dana.soa2back.service.repository;

import com.dana.soa2back.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Override
    List<Worker> findAll();

    @Override
    Optional<Worker> findById(Long aLong);

    @Override
    <S extends Worker> S save(S s);

    @Override
    void delete(Worker worker);

    Optional<Worker> findTopByOrderBySalary();

    List<Worker> findAllBySalary(Double salary);

    List<Worker> findAllByNameStartsWith(String prefix);
}
