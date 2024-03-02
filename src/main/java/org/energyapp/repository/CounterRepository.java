package org.energyapp.repository;

import org.energyapp.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

    Optional<Counter> findCounterBySerialNumber (String serialNumber);
    Optional<Counter> findCounterByConsumerUsername (String username);
    List<Counter> findCounterByConsumerId(Long id);
    Optional<Counter> findCounterByProgramEnrollmentId(Long programEnrollmentId);
}
