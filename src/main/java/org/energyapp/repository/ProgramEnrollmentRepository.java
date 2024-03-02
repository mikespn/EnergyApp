package org.energyapp.repository;

import org.energyapp.model.Program;
import org.energyapp.model.ProgramEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgramEnrollmentRepository extends JpaRepository<ProgramEnrollment, Long> {

    List<ProgramEnrollment> findByConsumerId(Long consumerId);

    List<ProgramEnrollment> findByProgramId(Long programId);

    @Query("SELECT pe.program.id, COUNT(pe) FROM ProgramEnrollment pe GROUP BY pe.program.id")
    List<Object[]> countEnrollmentsPerProgram();

    List<ProgramEnrollment> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);
    List<ProgramEnrollment> findByCounterId(Long counterId);
    List<ProgramEnrollment> findByProgramIn(List<Program> programs);
}
