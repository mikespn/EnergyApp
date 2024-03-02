//package org.energyapp.repository;
//
//import org.energyapp.model.Consumer;
//import org.energyapp.model.Counter;
//import org.energyapp.model.ProgramEnrollment;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ConsumerRepository extends JpaRepository <Consumer, Long> {
//
//    <S extends Consumer> S save (S entity);
//
//    Optional<Consumer> findById(Long id);
//
//    boolean existsById (Long id);
//
//    List<Consumer> findAll();
//
//    List<Consumer> findAll(Sort sort);
//
//    List<Consumer> findAllById(Iterable<Long> ids);
//
//    long count();
//
//    void deleteById(Long id);
//
//    void deleteAll(Iterable<? extends Consumer> entities);
//
//    void deleteAll();
//
//    List<Counter> findCountersByConsumerID(Long consumerId);
//
//    List<ProgramEnrollment> findProgramEnrollmentByConsumerID(Long consumerId);
//
//    Page<Consumer> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
//
//
//}
