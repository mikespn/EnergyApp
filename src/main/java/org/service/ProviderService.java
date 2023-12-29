package org.service;

import org.dto.ConsumerMappingException;
import org.dto.ConsumerResponseDTO;
import org.dto.ProviderRequestDTO;
import org.dto.ProviderResponseDTO;
import org.exceptions.DataFetchingException;
import org.exceptions.consumerExceptions.ConsumerCreationException;
import org.exceptions.consumerExceptions.ConsumerDeletionException;
import org.exceptions.consumerExceptions.ConsumerNotFoundException;
import org.exceptions.consumerExceptions.ConsumerUpdateException;
import org.exceptions.providerExceptions.ProviderCreationException;
import org.exceptions.providerExceptions.ProviderMappingException;
import org.exceptions.providerExceptions.ProviderNotFoundException;
import org.exceptions.providerExceptions.ProviderUpdateException;
import org.model.Consumer;
import org.model.Program;
import org.model.ProgramEnrollment;
import org.model.Provider;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.repository.ProgramEnrollmentRepository;
import org.repository.ProgramRepository;
import org.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.util.PasswordUtil;
import org.util.ServiceUtil;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final ProgramRepository programRepository;
    private final PasswordUtil passwordUtil;
    private final ServiceUtil serviceUtil;
    private final ModelMapper standardModelMapper;
    private final ModelMapper modelMapperNullSkip;
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    public ProviderService(ProviderRepository providerRepository, ProgramRepository programRepository,
                           PasswordUtil passwordUtil, ServiceUtil serviceUtil,
                           @Qualifier("standardModelMapper") ModelMapper standardModelMapper,
                           @Qualifier("modelMapperNullSkip") ModelMapper modelMapperNullSkip){
        this.providerRepository = providerRepository;
        this.programRepository = programRepository;
        this.passwordUtil = passwordUtil;
        this.serviceUtil = serviceUtil;
        this.standardModelMapper = standardModelMapper;
        this.modelMapperNullSkip = modelMapperNullSkip;
    }

    @Transactional
    @PreAuthorize("@ProviderService.isCurrentUserAdmin()")
    public ProviderResponseDTO createProvider(ProviderRequestDTO requestDTO){
        try{
            if(requestDTO.getUsername().isEmpty() || requestDTO.getUsername() == null ||
                    requestDTO.getPassword().isEmpty() || requestDTO.getPassword() == null){
                throw new ProviderCreationException("Cannot create a provider without username or password.");
            }

            Provider provider = standardModelMapper.map(requestDTO, Provider.class)
            String encodedPassword = passwordUtil.encodePassword(requestDTO.getPassword());
            provider.setPassword(encodedPassword);

            //Set programs.
            Set<Program> programs = new HashSet<>(programRepository.findByProviderID(requestDTO.getId()));
            provider.setPrograms(programs);

            //Save the new Provider.
            Provider savedProvider = providerRepository.save(provider);
            return standardModelMapper.map(savedProvider, ProviderResponseDTO.class);
        } catch (DataIntegrityViolationException e){
            logger.error("Data integrity violation during provider creation");
            throw new ConsumerCreationException("Provider data violates integrity constraints", e);
        }catch (EntityNotFoundException e) {
            logger.error("Entity not found during consumer creation", e);
            throw new ProviderNotFoundException("Required entity not found", e);
        }catch (MappingException e) {
            logger.error("Error mapping DTO to entity or vice versa");
            throw new ProviderMappingException("Error in data mapping", e);
        }catch (Exception e) {
            logger.error("General error during creation");
            throw new ConsumerCreationException("general error during provider creation", e);
        }

    }

    @Transactional
    @PreAuthorize("@ProviderService.isCurrentUser(#providerId) or hasRole('ROLE_ADMIN')")
    public ProviderResponseDTO updateProvider(Long providerId, ProviderRequestDTO requestDTO){
        try{
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
            return modelMapperNullSkip.map(savedProvider , ProviderResponseDTO.class);

        } catch (ProviderNotFoundException e) {
            logger.error("Provider not found with id {}", providerId, e);
            throw e; // Rethrow
        } catch (Exception e) {
            logger.error("Error updating provider with ID: {}", providerId, e);
            throw new ProviderUpdateException("Error updating provider with ID: " + providerId, e);
        }
    }

    @PreAuthorize("@providerService.isCurrentUser(#providerId) or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public ProviderResponseDTO getProviderById(Long providerId){
        try{
            //Fetching provider
            logger.info("Fetching provider by id");
            Provider provider = providerRepository.findById(providerId)
                    .orElseThrow(() -> new ProviderNotFoundException("Provider with ID: {} "+ providerId + "was not found"));

            ProviderResponseDTO responseDTO = standardModelMapper.map(provider, ProviderResponseDTO.class);
            logger.info("Provider with ID {} was successfully retrieved");
            return responseDTO;
        }catch (ProviderNotFoundException e){
            logger.error("Provider with ID: " + providerId + " was not found");
            throw e;
        }catch (Exception e){
            logger.error("Error during retrieving provider with ID: "+ providerId, e);
            throw new DataFetchingException("Error fetching provider with ID: " + providerId, e);
        }
    }

    @PreAuthorize("@providerService.isCurrentUser(#providerId) or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public void deleteProviderById(Long providerId){
        try{
            logger.info("Deleting provider with ID: {}", providerId);
            Provider provider = providerRepository.findById(providerId)
                    .orElseThrow(() -> new ProviderNotFoundException("Provider with ID:" + providerId + "was not found"));

            //Dissociate programs
            provider.getPrograms().forEach(program ->
            {
                program.setProvider(null);
                providerRepository.save(provider);
            });

            // Dissociate programEnrollments.
            consumer.getProgramEnrollments().forEach(programEnrollment ->
            {
                programEnrollment.setConsumer(null);
                programEnrollmentRepository.save(programEnrollment);
            });

            consumerRepository.delete(consumer);
            logger.info("Consumer with ID: {} successfully deleted", consumerId);
        }catch (ConsumerNotFoundException e) {
            logger.error("Consumer not found with id {}", consumerId, e);
            throw e; // Rethrow the exception
        } catch (Exception e) {
            logger.error("Error deleting consumer with ID: {}", consumerId, e);
            throw new ConsumerDeletionException("Error deleting consumer with ID: " + consumerId, e);
        }
    }






// END OF CODE
    public boolean isCurrentUser(Long providerId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException("The provider with the ID: "+ providerId +"was not found"));
        return provider.getUsername().equals(currentUsername);
    }

}
