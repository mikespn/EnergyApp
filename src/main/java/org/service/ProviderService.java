package org.service;

import org.dto.*;
import org.exceptions.DataFetchingException;
import org.exceptions.providerExceptions.*;
import org.model.Program;
import org.model.ProgramEnrollment;
import org.model.Provider;
import org.model.Consumer;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.repository.ConsumerRepository;
import org.repository.ProgramEnrollmentRepository;
import org.repository.ProgramRepository;
import org.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.util.PasswordUtil;
import org.util.ServiceUtil;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final ProgramRepository programRepository;
    private final PasswordUtil passwordUtil;
    private final ServiceUtil serviceUtil;
    private final ModelMapper standardModelMapper;
    private final ModelMapper modelMapperNullSkip;
    private static final Logger logger = LoggerFactory.getLogger(ProviderService.class);
    private final ProgramEnrollmentRepository programEnrollmentRepository;
    private final ConsumerRepository consumerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository, ProgramRepository programRepository,
                           PasswordUtil passwordUtil, ServiceUtil serviceUtil,
                           ConsumerRepository consumerRepository, ProgramEnrollmentRepository programEnrollmentRepository,
                           @Qualifier("standardModelMapper") ModelMapper standardModelMapper,
                           @Qualifier("modelMapperNullSkip") ModelMapper modelMapperNullSkip) {
        this.providerRepository = providerRepository;
        this.programRepository = programRepository;
        this.programEnrollmentRepository = programEnrollmentRepository;
        this.consumerRepository = consumerRepository;
        this.passwordUtil = passwordUtil;
        this.serviceUtil = serviceUtil;
        this.standardModelMapper = standardModelMapper;
        this.modelMapperNullSkip = modelMapperNullSkip;

    }

    @Transactional
    @PreAuthorize("@ProviderService.isCurrentUserAdmin()")
    public ProviderResponseDTO createProvider(ProviderRequestDTO requestDTO) {
        try {
            if (requestDTO.getUsername().isEmpty() || requestDTO.getUsername() == null ||
                    requestDTO.getPassword().isEmpty() || requestDTO.getPassword() == null) {
                throw new ProviderCreationException("Cannot create a provider without username or password.");
            }

            Provider provider = standardModelMapper.map(requestDTO, Provider.class);
            String encodedPassword = passwordUtil.encodePassword(requestDTO.getPassword());
            provider.setPassword(encodedPassword);

            //Set programs.
            Set<Program> programs = new HashSet<>(programRepository.findProgramsByProviderID(requestDTO.getId()));
            provider.setPrograms(programs);

            //Save the new Provider.
            Provider savedProvider = providerRepository.save(provider);
            return standardModelMapper.map(savedProvider, ProviderResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation during provider creation");
            throw new ProviderCreationException("Provider data violates integrity constraints", e);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found during provider creation", e);
            throw new ProviderNotFoundException("Required entity not found", e);
        } catch (MappingException e) {
            logger.error("Error mapping DTO to entity or vice versa");
            throw new ProviderMappingException("Error in data mapping", e);
        } catch (Exception e) {
            logger.error("General error during creation");
            throw new ProviderCreationException("general error during provider creation", e);
        }

    }

    @Transactional
    @PreAuthorize("@ProviderService.isCurrentProvider(#providerId) or hasRole('ROLE_ADMIN')")
    public ProviderResponseDTO updateProvider(Long providerId, ProviderRequestDTO requestDTO) {
        try {
            logger.info("Updating provider with ID: {}", providerId);
            Provider existingProvider = providerRepository.findById(providerId)
                    .orElseThrow(() -> new ProviderNotFoundException("Provider with ID: " + providerId + " was not found"));

            // If other fields are being updated
            modelMapperNullSkip.map(requestDTO, existingProvider);

            //If password is being updated
            if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
                String encodePassword = passwordUtil.encodePassword(requestDTO.getPassword());
                existingProvider.setPassword(encodePassword);
            }
            //Save and return the updated version of the provider
            Provider savedProvider = providerRepository.save(existingProvider);
            return modelMapperNullSkip.map(savedProvider, ProviderResponseDTO.class);

        } catch (ProviderNotFoundException e) {
            logger.error("Provider not found with id {}", providerId, e);
            throw e; // Rethrow
        } catch (Exception e) {
            logger.error("Error updating provider with ID: {}", providerId, e);
            throw new ProviderUpdateException("Error updating provider with ID: " + providerId, e);
        }
    }

    @PreAuthorize("@providerService.isCurrentProvider(#providerId) or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public ProviderResponseDTO getProviderById(Long providerId) {
        try {
            //Fetching provider
            logger.info("Fetching provider by id");
            Provider provider = providerRepository.findById(providerId)
                    .orElseThrow(() -> new ProviderNotFoundException("Provider with ID: {} " + providerId + "was not found"));

            ProviderResponseDTO responseDTO = standardModelMapper.map(provider, ProviderResponseDTO.class);
            logger.info("Provider with ID {} was successfully retrieved");
            return responseDTO;
        } catch (ProviderNotFoundException e) {
            logger.error("Provider with ID: " + providerId + " was not found");
            throw e;
        } catch (Exception e) {
            logger.error("Error during retrieving provider with ID: " + providerId, e);
            throw new DataFetchingException("Error fetching provider with ID: " + providerId, e);
        }
    }

    @PreAuthorize("@providerService.isCurrentProvider(#providerId) or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public void deleteProviderById(Long providerId) {
        try {
            logger.info("Deleting provider with ID: {}", providerId);
            Provider provider = providerRepository.findById(providerId)
                    .orElseThrow(() -> new ProviderNotFoundException("Provider with ID:" + providerId + "was not found"));

            //Dissociate programs
            provider.getPrograms().forEach(program ->
            {
                program.setProvider(null);
                providerRepository.save(provider);
            });

        } catch (ProviderNotFoundException e) {
            logger.error("Provider not found with id {}", providerId, e);
            throw e; // Rethrow the exception
        } catch (Exception e) {
            logger.error("Error deleting provider with ID: {}", providerId, e);
            throw new ProviderDeletionException("Error deleting Provider with ID: " + providerId, e);
        }
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<ProviderResponseDTO> findAllProviders(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Provider> providerPage = providerRepository.findAll(pageable);
        return providerPage.map(provider -> standardModelMapper.map(provider, ProviderResponseDTO.class));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public Page<ProviderResponseDTO> findProvidersByUsername(String username, int page, int size, String sortBy, String sortDir) {
        // Initialize the direction and the pageable object
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Fetch providers by username with case-insensitive search
        Page<Provider> providerPage = providerRepository.findByUsernameContainingIgnoreCase(username, pageable);
        return providerPage.map(provider -> standardModelMapper.map(provider, ProviderResponseDTO.class));
    }

    @PreAuthorize("@providerService.isCurrentProvider(#providerId) or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public List<ProgramResponseDTO> findProgramsByProviderId(Long providerId) {
        // Fetch programs associated with the provider
        List<Program> programs = programRepository.findProgramsByProviderID(providerId);

        // Convert Program entities to DTOs
        return programs.stream()
                .map(program -> standardModelMapper.map(program, ProgramResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("@providerService.isCurrentProvider(#providerId) or hasRole('ROLE_ADMIN')")
    @Transactional
    public ProgramResponseDTO addProgramToProvider(Long providerId, ProgramRequestDTO requestDTO) {
        // Fetch the provider from the repository
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException("Provider with ID: " + providerId + " not found"));

        // Map DTO to Program entity
        Program program = standardModelMapper.map(requestDTO, Program.class);

        // Set the provider to the program
        program.setProvider(provider);

        // Additional fields to be set if any, based on your Program class structure
        // e.g., setting default values or values that are not part of DTO

        // Save the program entity
        Program savedProgram = programRepository.save(program);

        // Map saved entity to DTO
        return standardModelMapper.map(savedProgram, ProgramResponseDTO.class);
    }


    @Transactional(readOnly = true)
    public List<ConsumerResponseDTO> findConsumersByProviderId(Long providerId) {
        try {
            logger.info("Fetching consumers for provider with ID: {}", providerId);
            List<Program> programs = programRepository.findProgramsByProviderID(providerId);
            Set<Consumer> consumers = new HashSet<>();

            for (Program program : programs) {
                List<ProgramEnrollment> enrollments = programEnrollmentRepository.findByProgramId(program.getId());
                enrollments.forEach(enrollment -> consumers.add(enrollment.getConsumer()));
            }

            return consumers.stream()
                    .map(consumer -> standardModelMapper.map(consumer, ConsumerResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while fetching consumers for provider with ID: {}", providerId, e);
            throw new DataFetchingException("Error fetching consumers for provider with ID: " + providerId, e);
        }
    }

    @Transactional(readOnly = true)
    public ProviderResponseDTO findProviderByUsername(String username) {
        try {
            logger.info("Fetching provider with username: {}", username);
            Provider provider = providerRepository.findProviderByUsername(username)
                    .orElseThrow(() -> new ProviderNotFoundException("Provider with username: " + username + " not found"));

            return standardModelMapper.map(provider, ProviderResponseDTO.class);
        } catch (ProviderNotFoundException e) {
            logger.error("Provider not found with username: {}", username, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching provider with username: {}", username, e);
            throw new DataFetchingException("Error fetching provider with username: " + username, e);
        }
    }

    @Transactional(readOnly = true)
    public List<ProgramEnrollmentResponseDTO> findByProgramId(Long programId) {
        try {
            logger.info("Fetching program enrollments for program ID: {}", programId);
            List<ProgramEnrollment> programEnrollments = programEnrollmentRepository.findByProgramId(programId);

            return programEnrollments.stream()
                    .map(enrollment -> standardModelMapper.map(enrollment, ProgramEnrollmentResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while fetching program enrollments for program ID: {}", programId, e);
            throw new DataFetchingException("Error fetching program enrollments for program ID: " + programId, e);
        }
    }

    public boolean isCurrentProvider(Long providerId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException("The provider with the ID: "+ providerId +"was not found"));
        return provider.getUsername().equals(currentUsername);
    }
}
