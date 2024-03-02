//package org.energyapp.service;
//
//import org.energyapp.exceptions.DataFetchingException;
//import org.energyapp.model.Consumer;
//import org.energyapp.model.Counter;
//import org.energyapp.model.Program;
//import org.energyapp.model.ProgramEnrollment;
//import org.energyapp.util.PasswordUtil;
//import org.energyapp.util.ServiceUtil;
//import org.energyapp.dto.*;
//import org.energyapp.exceptions.consumerExceptions.ConsumerCreationException;
//import org.energyapp.exceptions.consumerExceptions.ConsumerDeletionException;
//import org.energyapp.exceptions.consumerExceptions.ConsumerNotFoundException;
//import org.energyapp.exceptions.consumerExceptions.ConsumerUpdateException;
//import org.energyapp.exceptions.counterExceptions.CounterNotFoundException;
//import org.energyapp.exceptions.programEnrollmentException.ProgramEnrollmentException;
//import org.energyapp.exceptions.programExceptions.ProgramNotFoundException;
//import org.modelmapper.MappingException;
//import org.modelmapper.ModelMapper;
//import org.energyapp.repository.ConsumerRepository;
//import org.energyapp.repository.CounterRepository;
//import org.energyapp.repository.ProgramEnrollmentRepository;
//import org.energyapp.repository.ProgramRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityNotFoundException;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class ConsumerService {
//
//    private final ConsumerRepository consumerRepository;
//    private final ProgramRepository programRepository;
//    private final ProgramEnrollmentRepository programEnrollmentRepository;
//    private final PasswordUtil passwordUtil;
//    private final ServiceUtil serviceUtil;
//    private final CounterRepository counterRepository;
//    private final ModelMapper standardModelMapper;
//    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
//    private final ModelMapper modelMapperNullSkip;
//
//
//    @Autowired
//    public ConsumerService(ConsumerRepository consumerRepository,
//                           ProgramRepository programRepository,
//                           ProgramEnrollmentRepository programEnrollmentRepository,
//                           CounterRepository counterRepository,
//                           ServiceUtil serviceUtil,
//                           PasswordUtil passwordUtil,
//                           @Qualifier("standardModelMapper") ModelMapper standardModelMapper,
//                           @Qualifier("modelMapperNullSkip") ModelMapper modelMapperNullSkip) {
//        this.consumerRepository = consumerRepository;
//        this.programRepository = programRepository;
//        this.programEnrollmentRepository = programEnrollmentRepository;
//        this.counterRepository = counterRepository;
//        this.serviceUtil = serviceUtil;
//        this.passwordUtil = passwordUtil;
//        this.standardModelMapper = standardModelMapper;
//        this.modelMapperNullSkip = modelMapperNullSkip;
//    }
//
//    @Transactional
//    public ConsumerResponseDTO createConsumer(ConsumerRequestDTO requestDTO) {
//        try {
//
//            if (requestDTO.getUsername() == null || requestDTO.getUsername().isEmpty() ||
//                    requestDTO.getPassword() == null || requestDTO.getPassword().isEmpty()) {
//                throw new ConsumerCreationException("Username and password must not be null or empty");
//            }
//
//            // Use mapper for simple fields
//            Consumer consumer = standardModelMapper.map(requestDTO, Consumer.class);
//
//            //Encode password and set it manually
//            String encodedPassword = passwordUtil.encodePassword(requestDTO.getPassword());
//            consumer.setPassword(encodedPassword);
//
//            //Handle sets of Counters and ProgramEnrollments
//            Set<Long> counterIds = requestDTO.getCounterIds();
//            Set<Counter> counters;
//
//            if(counterIds == null || counterIds.isEmpty()){
//                counters = new HashSet<>();
//            }else{
//                counters = new HashSet<>(counterRepository.findAllById(counterIds));
//            }
//
//            Set<Long> programEnrollmentsIds = requestDTO.getProgramEnrollmentIds();
//            Set<ProgramEnrollment> programEnrollments;
//            if(programEnrollmentsIds == null || programEnrollmentsIds.isEmpty()){
//                programEnrollments = new HashSet<>();
//            }else {
//                programEnrollments = new HashSet<>(programEnrollmentRepository.findAllById(requestDTO.getProgramEnrollmentIds()));
//            }
//
//            consumer.setCounters(counters);
//            consumer.setProgramEnrollments(programEnrollments);
//
//            // save the consumer entity
//            Consumer savedConsumer = consumerRepository.save(consumer);
//
//            //Map the saved entity to a DTO for the response
//            return standardModelMapper.map(savedConsumer, ConsumerResponseDTO.class);
//
//        } catch (DataIntegrityViolationException e) {
//            logger.error("Data integrity violation during consumer creation");
//            throw new ConsumerCreationException("Consumer data violates integrity constraints", e);
//        } catch (EntityNotFoundException e) {
//            logger.error("Entity not found during consumer creation", e);
//            throw new ConsumerCreationException("Required entity not found", e);
//        } catch (MappingException e) {
//            logger.error("Error mapping DTO to entity or vice versa");
//            throw new ConsumerMappingException("Error in data mapping", e);
//        } catch (Exception e) {
//            logger.error("General error during creation");
//            throw new ConsumerCreationException("general error during consumer creation", e);
//        }
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional(readOnly = true)
//    public ConsumerResponseDTO getConsumerById(Long consumerId) {
//        try {
//            logger.info("Fetching consumer with ID: {}", consumerId);
//            Consumer consumer = consumerRepository.findById(consumerId)
//                    .orElseThrow(() -> new ConsumerNotFoundException("Consumer with ID: " + consumerId + "was bot found"));
//
//            ConsumerResponseDTO responseDTO = standardModelMapper.map(consumer, ConsumerResponseDTO.class);
//            logger.info("Consumer with ID" + consumerId + "was successfully retrieved");
//            return responseDTO;
//        } catch (ConsumerNotFoundException e) {
//            logger.error("Consumer not found with id{} :", consumerId, e);
//            throw e;
//        } catch (Exception e) {
//            logger.error("Error while fetching consumer with ID: {}", consumerId, e);
//            throw new DataFetchingException("Error fetching consumer with ID: " + consumerId, e);
//        }
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional
//    public ConsumerResponseDTO updateConsumer(Long consumerId, ConsumerRequestDTO requestDTO) {
//        try {
//                logger.info("Updating consumer with ID: {}", consumerId);
//                Consumer existingConsumer = consumerRepository.findById(consumerId)
//                        .orElseThrow(() -> new ConsumerNotFoundException("Consumer with ID: " + consumerId + "was not found"));
//
//                //If other fields are being updated
//                modelMapperNullSkip.map(requestDTO, existingConsumer);
//
//                //If password is being updated
//                if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
//                    String encodePassword = passwordUtil.encodePassword(requestDTO.getPassword());
//                    existingConsumer.setPassword(encodePassword);
//                }
//
//                //Save the updated consumer
//                Consumer savedConsumer = consumerRepository.save(existingConsumer);
//
//                //Convert to DTO
//                return modelMapperNullSkip.map(savedConsumer, ConsumerResponseDTO.class);
//
//        } catch (ConsumerNotFoundException e) {
//            logger.error("Consumer not found with id {}", consumerId, e);
//            throw e; // Rethrow
//        } catch (Exception e) {
//            logger.error("Error updating consumer with ID: {}", consumerId, e);
//            throw new ConsumerUpdateException("Error updating consumer with ID: " + consumerId, e);
//        }
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional
//    public void deleteConsumerById(Long consumerId){
//        try{
//            logger.info("Deleting consumer with ID: {}", consumerId);
//            Consumer consumer = consumerRepository.findById(consumerId)
//                    .orElseThrow(() -> new ConsumerNotFoundException("Consumer with ID:" + consumerId + "was not found"));
//
//            //Dissociate counters
//            consumer.getCounters().forEach(counter ->
//            {
//                counter.setConsumer(null);
//                counterRepository.save(counter);
//            });
//
//            // Dissociate programEnrollments.
//            consumer.getProgramEnrollments().forEach(programEnrollment ->
//            {
//                programEnrollment.setConsumer(null);
//                programEnrollmentRepository.save(programEnrollment);
//            });
//
//            consumerRepository.delete(consumer);
//            logger.info("Consumer with ID: {} successfully deleted", consumerId);
//        }catch (ConsumerNotFoundException e) {
//            logger.error("Consumer not found with id {}", consumerId, e);
//            throw e; // Rethrow the exception
//        } catch (Exception e) {
//            logger.error("Error deleting consumer with ID: {}", consumerId, e);
//            throw new ConsumerDeletionException("Error deleting consumer with ID: " + consumerId, e);
//        }
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUserAdmin()")
//    @Transactional(readOnly = true)
//    public Page<ConsumerResponseDTO> findAllConsumers(int page, int size, String sortBy, String sortDir) {
//
//            //Initialize the direction and the pageable object
//            Sort.Direction direction = Sort.Direction.fromString(sortDir);
//            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
//
//            //Makes a Page<Consumer> from consumerRepo
//            Page<Consumer> consumerPage = consumerRepository.findAll(pageable);
//            if (consumerPage.isEmpty()) {
//               logger.info("No consumer found for page: {}, size: {}, sortBy: {}", page, size, sortBy);
//            }
//
//            //Mapping and returning the consumer list
//            return consumerPage.map(consumer -> standardModelMapper.map(consumer, ConsumerResponseDTO.class));
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional(readOnly = true)
//    public ConsumerResponseDTO findConsumerById(Long consumerId){
//        try{
//            logger.info("Fetching consumer with ID: {}", consumerId);
//            Consumer consumer = consumerRepository.findById(consumerId)
//                    .orElseThrow(() -> new ConsumerNotFoundException("Consumer with id" + consumerId + "was not found"));
//
//            return standardModelMapper.map(consumer, ConsumerResponseDTO.class);
//        }catch (ConsumerNotFoundException e){
//            logger.error("Consumer not found with ID: " + consumerId);
//            throw e;
//        }catch (Exception e){
//            logger.error("Error while fetching consumer with ID: {}", consumerId, e);
//            throw new DataFetchingException("Error fetching consumer with ID: " + consumerId, e);
//        }
//    }
//    @PreAuthorize("@consumerService.isCurrentUserAdmin()")
//    @Transactional(readOnly = true)
//    public Page<ConsumerResponseDTO> findConsumerByUsername(String username, int page, int size, String sortBy, String sortDir){
//        //Initialize the dir and sort of the page
//        Sort.Direction direction = Sort.Direction.fromString(sortDir);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
//
//        Page<Consumer> consumers = consumerRepository.findByUsernameContainingIgnoreCase(username, pageable);
//        return consumers.map(consumer -> standardModelMapper.map(consumer, ConsumerResponseDTO.class));
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional(readOnly = true)
//    public List<CounterResponseDTO> findCountersByConsumerId(Long consumerId){
//        // Fetch counters from repo
//        List<Counter> counters = consumerRepository.findCountersByConsumerID(consumerId);
//        // Convert Counter entities to DTO
//        return counters.stream()
//                .map(counter -> standardModelMapper.map(counter, CounterResponseDTO.class))
//                .collect(Collectors.toList());
//
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional
//    public void addCounterToConsumer(Long consumerId, Long counterId){
//        //Fetch consumer and counter from their repo
//        Consumer consumer = consumerRepository.findById(consumerId)
//                .orElseThrow(() -> new ConsumerNotFoundException("The consumer with ID: " + consumerId + " was not found"));
//        Counter counter = counterRepository.findById(counterId)
//                .orElseThrow(() -> new CounterNotFoundException("The counter with ID: "+ counterId + " was not found"));
//
//        consumer.addCounter(counter);
//        consumerRepository.save(consumer);
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional
//    public void removeCounterFromConsumer(Long consumerId, Long counterId){
//        //Fetch consumer from repo.
//        Consumer consumer = consumerRepository.findById(consumerId)
//                .orElseThrow(() -> new ConsumerNotFoundException("The consumer with ID: " + consumerId + " was not found"));
//
//        //Get counters and check for null return.
//        Set<Counter> counters = consumer.getCounters();
//        if (counters == null || counters.isEmpty()){
//            throw new CounterNotFoundException("No counters were found for consumer with ID: " + consumerId);
//        }
//
//        //Find and remove the counter
//        boolean isRemoved = counters.stream()
//                .filter(counter -> counter.getId().equals(counterId))
//                .findFirst()
//                .map(consumer::removeCounter)
//                .isPresent();
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional(readOnly = true)
//    public List<ProgramEnrollmentResponseDTO> findProgramEnrollmentsByConsumerId(Long consumerId){
//        //Fetch programEnrollments
//        List<ProgramEnrollment> programEnrollments = programEnrollmentRepository
//                .findByConsumerId(consumerId);
//        if(programEnrollments == null || programEnrollments.isEmpty()){
//            logger.info("No programEnrollments were retrieved for the consumer with Id: "+ consumerId);
//        }
//
//        //Convert entities to DTO
//        return programEnrollments.stream()
//                .map(programEnrollment -> standardModelMapper.map(programEnrollment, ProgramEnrollmentResponseDTO.class))
//                .collect(Collectors.toList());
//
//    }
//
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    @Transactional
//    public ProgramEnrollmentResponseDTO addProgramEnrollmentToConsumer(Long consumerId, Long programId, ProgramEnrollmentRequestDTO requestDTO) {
//        //Map common fields
//        ProgramEnrollment programEnrollment = standardModelMapper.map(requestDTO, ProgramEnrollment.class);
//
//        //Resolve relationships
//        Counter counter = counterRepository.findById(requestDTO.getCounterId())
//                .orElseThrow(() -> new CounterNotFoundException("Counter's id " + requestDTO.getCounterId() + "was not found in the requestDTO"));
//
//        // Set consumer and program.
//        Consumer consumer = consumerRepository.findById(consumerId)
//                .orElseThrow(() -> new ConsumerNotFoundException("The consumer with ID: " + consumerId + "was not found"));
//
//        Program program = programRepository.findById(programId)
//                .orElseThrow(() -> new ProgramNotFoundException("The program with ID: " + programId + "was not found"));
//
//        if (counter.getProgramEnrollment() != null &&
//                counter.getProgramEnrollment().getStatus().equalsIgnoreCase("active")) {
//            throw new ProgramEnrollmentException("Counter is already enrolled in an active program");
//        }
//
//        LocalDate currentDate = LocalDate.now();
//
//        //Set relationships
//        programEnrollment.setCounter(counter);
//        programEnrollment.setConsumer(consumer);
//        programEnrollment.setProgram(program);
//        programEnrollment.setStatus("active");
//
//        ProgramEnrollment savedEnrollment = programEnrollmentRepository.save(programEnrollment);
//        return standardModelMapper.map(savedEnrollment, ProgramEnrollmentResponseDTO.class);
//    }
//
//    @Transactional
//    @PreAuthorize("@consumerService.isCurrentUser(#consumerId) or hasRole('ROLE_ADMIN')")
//    public void removeProgramEnrollmentFromUser(Long consumerId, Long programEnrollmentId, Long counterId){
//        Consumer consumer = consumerRepository.findById(consumerId)
//                .orElseThrow(() -> new ConsumerNotFoundException("Consumer with ID: " + consumerId + " was not found"));
//
//        ProgramEnrollment programEnrollment = programEnrollmentRepository.findById(programEnrollmentId)
//                .orElseThrow(() -> new ProgramEnrollmentException("program enrollment with ID: " + programEnrollmentId + "was not found"));
//        if(!programEnrollment.getConsumer().equals(consumer)){
//            throw new ProgramEnrollmentException
//                    ("Program Enrollment with ID" + programEnrollmentId + "does not belong to consumer with id" + consumerId );
//        }
//
//        //Fetch the counter and check is it is associated with the consumer also.
//        Counter associatedCounter = programEnrollment.getCounter();
//
//        if(associatedCounter != null && consumer.getCounters().contains(associatedCounter)){
//            associatedCounter.setProgramEnrollment(null);
//            counterRepository.save(associatedCounter);
//        }
//
//        consumer.removeProgramEnrollment(programEnrollment);
//        programEnrollment.setConsumer(null);
//        programEnrollment.setStatus("Terminated");
//
//        //Save the updates.
//        consumerRepository.save(consumer);
//        programEnrollmentRepository.save(programEnrollment);
//
//        logger.info("Program Enrollment with ID: " + programEnrollmentId + " removed from consumer with ID: " + consumerId);
//
//    }
//
//    public boolean isCurrentUser(Long consumerId) {
//        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        Consumer consumer = consumerRepository.findById(consumerId)
//                .orElseThrow(() -> new ConsumerNotFoundException("User not found"));
//        return consumer.getUsername().equals(currentUsername);
//    }
//}