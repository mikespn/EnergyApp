package org.repository;

import org.model.Program;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository <Program, Long> {

    //Save (Create/Update)
    <S extends Program> S save (S entity);

    // Find by ID (Read)
    Optional<Program> findById(Long id);

    //Check existence by ID
    boolean existsById(Long id);

    //Find all (Read)
    List<Program> findAll();

    // Find all programs and sort them
    List<Program> findAll(Sort sort);

    List<Program> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(Program entity);

    void deleteAll(Iterable<? extends Program> entities);

    void deleteAll();

    List<Program> findProgramsByCategoryId(Long categoryId);

    List<Program> findProgramsByProviderID(Long providerId);

    //Find and sort programs within a category by day rate
    List<Program> findProgramsByCategoryId(Long categoryId, Sort sort);

    //Find a program by its name
    List<Program> findByName(String name);

    List<Program> findByIsNightRateApplicableTrue();

    List<Program> findByIsNightRateApplicableFalse();

    List<Program>findAllByOrderByDayRateAsc();

    List<Program>findByNameContainingIgnoreCase(String name);

    List<Program> findByDayRateBetween(BigDecimal minDayRate, BigDecimal maxDayRate);

    List<Program> findByProviderIdAndCategoryId(Long providerId, Long categoryId);

    // Method to count the number of programs in each category
    @Query("SELECT c, COUNT(p) FROM Program p JOIN p.category c GROUP BY c")
    List<Object[]> countProgramsInCategories();

}
