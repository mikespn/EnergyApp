package org.service;

import org.dto.ProgramRequestDTO;
import org.dto.ProgramResponseDTO;
import org.exceptions.DataFetchingException;
import org.exceptions.categoryExceptions.CategoryNotFoundException;
import org.exceptions.programExceptions.ProgramNotFoundException;
import org.exceptions.providerExceptions.ProviderNotFoundException;
import org.model.Category;
import org.model.Program;
import org.model.Provider;
import org.modelmapper.ModelMapper;
import org.repository.CategoryRepository;
import org.repository.ProgramRepository;
import org.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.util.ServiceUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramService {

    private final ProgramRepository programRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProgramService.class);
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;
    private final ModelMapper standardModelMapper;
    private final ModelMapper modelMapperNullSkip;
    private final ServiceUtil serviceUtil;

    public ProgramService(ProgramRepository programRepository, ProviderRepository providerRepository,
                          CategoryRepository categoryRepository, ServiceUtil serviceUtil,
                          @Qualifier("standardModelMapper") ModelMapper standardModelMapper,
                          @Qualifier("modelMapperNullSkip") ModelMapper modelMapperNullSkip) {
        this.programRepository = programRepository;
        this.providerRepository = providerRepository;
        this.standardModelMapper = standardModelMapper;
        this.modelMapperNullSkip = modelMapperNullSkip;
        this.serviceUtil = serviceUtil;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProgramResponseDTO createProgram(ProgramRequestDTO requestDTO) {
        try {
            // Check and fetch the provider
            Provider provider = providerRepository.findById(requestDTO.getProviderId())
                    .orElseThrow(() -> new ProviderNotFoundException("Provider not found for ID: " + requestDTO.getProviderId()));

            Category category = categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + requestDTO.getCategoryId()));

            // Map DTO to Program entity
            Program program = standardModelMapper.map(requestDTO, Program.class);
            program.setProvider(provider);
            program.setCategory(category);

            // Save the program entity
            Program savedProgram = programRepository.save(program);

            // Map saved entity to DTO for response
            return standardModelMapper.map(savedProgram, ProgramResponseDTO.class);
        } catch (ProgramNotFoundException e) {
            logger.error("Error creating program due to missing entity: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error creating program: ", e);
            throw new RuntimeException("Error creating program", e);
        }
    }

    @Transactional
    public ProgramResponseDTO updateProgram(Long programId, ProgramRequestDTO requestDTO) {
        try {
            // Fetch the existing program
            Program existingProgram = programRepository.findById(programId)
                    .orElseThrow(() -> new ProgramNotFoundException("Program not found for ID: " + programId));

            // Optionally, fetch the provider if it's being updated
            if (requestDTO.getProviderId() != null) {
                Provider provider = providerRepository.findById(requestDTO.getProviderId())
                        .orElseThrow(() -> new ProgramNotFoundException("Provider not found for ID: " + requestDTO.getProviderId()));
                existingProgram.setProvider(provider);
            }

            // Update other fields of the program
            modelMapperNullSkip.map(requestDTO, existingProgram);

            // Save the updated program entity
            Program updatedProgram = programRepository.save(existingProgram);

            // Map the updated entity to DTO for response
            return standardModelMapper.map(updatedProgram, ProgramResponseDTO.class);
        } catch (ProgramNotFoundException e) {
            logger.error("Error updating program: Entity not found for ID " + programId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error updating program: ", e);
            throw new RuntimeException("Error updating program", e); // Consider using a more specific custom exception
        }
    }

    @Transactional(readOnly = true)
    public ProgramResponseDTO findProgramById(Long programId) {
        try {
            // Fetch the program from the repository
            Program program = programRepository.findById(programId)
                    .orElseThrow(() -> new ProgramNotFoundException("Program not found for ID: " + programId));

            // Map the program entity to DTO
            return standardModelMapper.map(program, ProgramResponseDTO.class);
        } catch (ProgramNotFoundException e) {
            logger.error("Program not found for ID: {}", programId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving program with ID: {}", programId, e);
            throw new DataFetchingException("Error retrieving program with ID: " + programId, e);
        }
    }

    @Transactional(readOnly = true)
    public List<ProgramResponseDTO> findProgramByCategory(Long categoryId) {
        try {
            // Fetch the programs by category id
            List<Program> programs = programRepository.findProgramsByCategoryId(categoryId);

            // Map each program entity to its DTO representation
            return programs.stream()
                    .map(program -> standardModelMapper.map(program, ProgramResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving programs for category with ID {}", categoryId, e);
            throw new DataFetchingException("Error retrieving programs for category with ID: " + categoryId, e);
        }
    }
}
