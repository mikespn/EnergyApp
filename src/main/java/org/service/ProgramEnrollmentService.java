package org.service;

import org.dto.ProgramEnrollmentRequestDTO;
import org.dto.ProgramEnrollmentResponseDTO;
import org.exceptions.DataFetchingException;
import org.exceptions.programEnrollmentException.ProgramEnrollmentException;
import org.model.Counter;
import org.model.Program;
import org.model.ProgramEnrollment;
import org.model.Consumer;
import org.modelmapper.ModelMapper;
import org.repository.ConsumerRepository;
import org.repository.CounterRepository;
import org.repository.ProgramEnrollmentRepository;
import org.repository.ProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.util.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramEnrollmentService {

    private final ProgramEnrollmentRepository programEnrollmentRepository;
    private final ConsumerRepository consumerRepository;
    private final ProgramRepository programRepository;
    private final CounterRepository counterRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProgramEnrollmentService.class);
    private final ServiceUtil serviceUtil;
    private final ModelMapper standardModelMapper;
    private final ModelMapper modelMapperNullSkip;

    @Autowired
    public ProgramEnrollmentService(ProgramEnrollmentRepository programEnrollmentRepository, ConsumerRepository consumerRepository,
                                    ProgramRepository programRepository, CounterRepository counterRepository,
                                    ServiceUtil serviceUtil, ModelMapper standardModelMapper,
                                    ModelMapper modelMapperNullSkip) {
        this.programEnrollmentRepository = programEnrollmentRepository;
        this.consumerRepository = consumerRepository;
        this.programRepository = programRepository;
        this.counterRepository = counterRepository;
        this.serviceUtil = serviceUtil;
        this.standardModelMapper = standardModelMapper;
        this.modelMapperNullSkip = modelMapperNullSkip;
    }


    @Transactional
    public ProgramEnrollmentResponseDTO createProgramEnrollment(ProgramEnrollmentRequestDTO requestDTO) {
        ProgramEnrollment programEnrollment = standardModelMapper.map(requestDTO, ProgramEnrollment.class);

        // Handling relationships
        Consumer consumer = consumerRepository.findById(requestDTO.getConsumerId())
                .orElseThrow(() -> new ProgramEnrollmentException("Consumer not found"));
        programEnrollment.setConsumer(consumer);

        Program program = programRepository.findById(requestDTO.getProgramId())
                .orElseThrow(() -> new ProgramEnrollmentException("Program not found"));
        programEnrollment.setProgram(program);

        if (requestDTO.getCounterId() != null) {
            Counter counter = counterRepository.findById(requestDTO.getCounterId())
                    .orElseThrow(() -> new ProgramEnrollmentException("Counter not found"));
            programEnrollment.setCounter(counter);
        }

        ProgramEnrollment savedProgramEnrollment = programEnrollmentRepository.save(programEnrollment);
        return standardModelMapper.map(savedProgramEnrollment, ProgramEnrollmentResponseDTO.class);
    }

    @Transactional
    public ProgramEnrollmentResponseDTO updateProgramEnrollment(Long id, ProgramEnrollmentRequestDTO requestDTO) {
        try {
            logger.info("Updating program enrollment with ID: {}", id);
            ProgramEnrollment existingProgramEnrollment = programEnrollmentRepository.findById(id)
                    .orElseThrow(() -> new ProgramEnrollmentException("Program Enrollment with ID: " + id + " not found"));

            // Update fields from DTO
            if (requestDTO.getConsumerId() != null) {
                Consumer consumer = consumerRepository.findById(requestDTO.getConsumerId())
                        .orElseThrow(() -> new ProgramEnrollmentException("Consumer not found"));
                existingProgramEnrollment.setConsumer(consumer);
            }

            if (requestDTO.getProgramId() != null) {
                Program program = programRepository.findById(requestDTO.getProgramId())
                        .orElseThrow(() -> new ProgramEnrollmentException("Program not found"));
                existingProgramEnrollment.setProgram(program);
            }

            if (requestDTO.getCounterId() != null) {
                Counter counter = counterRepository.findById(requestDTO.getCounterId())
                        .orElseThrow(() -> new ProgramEnrollmentException("Counter not found"));
                existingProgramEnrollment.setCounter(counter);
            }

            existingProgramEnrollment.setStatus(requestDTO.getStatus());
            existingProgramEnrollment.setEnrollmentDate(requestDTO.getEnrollmentDate());
            existingProgramEnrollment.setEndDate(requestDTO.getEndDate());

            // Save the updated program enrollment
            ProgramEnrollment savedProgramEnrollment = programEnrollmentRepository.save(existingProgramEnrollment);

            // Convert to DTO
            return modelMapperNullSkip.map(savedProgramEnrollment, ProgramEnrollmentResponseDTO.class);

        } catch (ProgramEnrollmentException e) {
            logger.error("Error updating program enrollment with ID: {}", id, e);
            throw e; // Rethrow
        } catch (Exception e) {
            logger.error("General error updating program enrollment with ID: {}", id, e);
            throw new ProgramEnrollmentException("Error updating program enrollment with ID: " + id, e);
        }
    }

    @Transactional
    public void deleteProgramEnrollment(Long id) {
        try {
            logger.info("Deleting program enrollment with ID: {}", id);
            ProgramEnrollment programEnrollment = programEnrollmentRepository.findById(id)
                    .orElseThrow(() -> new ProgramEnrollmentException("Program Enrollment with ID: " + id + " not found"));

            // Dissociate relationships if necessary
            // E.g., setting the counter and consumer reference to null
            programEnrollment.setConsumer(null);
            programEnrollment.setProgram(null);
            if (programEnrollment.getCounter() != null) {
                Counter counter = programEnrollment.getCounter();
                counter.setProgramEnrollment(null);
                counterRepository.save(counter);
            }

            programEnrollmentRepository.delete(programEnrollment);
            logger.info("Program Enrollment with ID: {} successfully deleted", id);
        } catch (ProgramEnrollmentException e) {
            logger.error("Error deleting program enrollment with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("General error deleting program enrollment with ID: {}", id, e);
            throw new ProgramEnrollmentException("Error deleting program enrollment with ID: " + id, e);
        }
    }

    public ProgramEnrollmentResponseDTO getProgramEnrollmentById(Long id) {
        try {
            logger.info("Fetching program enrollment with ID: {}", id);
            ProgramEnrollment programEnrollment = programEnrollmentRepository.findById(id)
                    .orElseThrow(() -> new ProgramEnrollmentException("Program Enrollment with ID: " + id + " not found"));

            return standardModelMapper.map(programEnrollment, ProgramEnrollmentResponseDTO.class);
        } catch (ProgramEnrollmentException e) {
            logger.error("Error fetching program enrollment with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("General error fetching program enrollment with ID: {}", id, e);
            throw new DataFetchingException("Error fetching program enrollment with ID: " + id, e);
        }
    }

    @Transactional(readOnly = true)
    public List<ProgramEnrollmentResponseDTO> getAllProgramEnrollmentsByCategory(Long categoryId) {
        try {
            logger.info("Fetching program enrollments for category ID: {}", categoryId);
            List<Program> programs = programRepository.findProgramsByCategoryId(categoryId);
            if (programs.isEmpty()) {
                logger.info("No programs found for category ID: {}", categoryId);
                return new ArrayList<>();
            }

            List<ProgramEnrollment> enrollments = programEnrollmentRepository.findByProgramIn(programs);
            return enrollments.stream()
                    .map(enrollment -> standardModelMapper.map(enrollment, ProgramEnrollmentResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching program enrollments for category ID: {}", categoryId, e);
            throw new DataFetchingException("Error fetching program enrollments for category ID: " + categoryId, e);
        }
    }
}
