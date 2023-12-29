package org.repository;

import org.model.Consumer;
import org.model.Program;
import org.model.ProgramEnrollment;
import org.model.Provider;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    <S extends Provider> S save (S entity);

    Optional<Provider> findById(Long id);

    boolean existsById(Long Id);

    List<Provider> findAll();

    List<Provider> findAll(Sort sort);

    List<Provider> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(Provider entity);

    void deleteAll(Iterable<? extends Provider> entities);

    void deleteAll();

    Optional<Provider> findProviderByUsername(String username);

    List<ProgramEnrollment> findByProgram_Id(Long programId);

    @Query("SELECT pe.consumer FROM ProgramEnrollment pe WHERE pe.program.provider.id = :providerId")
    List<Consumer> findConsumersByProviderId(Long providerId);
}
