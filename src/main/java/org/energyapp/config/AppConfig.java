//package org.energyapp.config;
//
//import org.energyapp.model.*;
//import org.energyapp.repository.*;
//import org.energyapp.model.*;
//import org.energyapp.repository.*;
//import org.energyapp.*;
//import org.modelmapper.Conditions;
//import org.modelmapper.Converter;
//import org.modelmapper.ModelMapper;
//import org.energyapp.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Configuration
//public class AppConfig {
//
//    private final CounterRepository counterRepository;
//    private final ProgramEnrollmentRepository programEnrollmentRepository;
//    private final ProgramRepository programRepository;
//    private final ConsumerRepository consumerRepository;
//    private final ProviderRepository providerRepository;
//    private final CategoryRepository categoryRepository;
//
//
//    public AppConfig(CounterRepository counterRepository,
//                     ProgramEnrollmentRepository programEnrollmentRepository,
//                     ProgramRepository programRepository,
//                     ConsumerRepository consumerRepository,
//                     ProviderRepository providerRepository,
//                     CategoryRepository categoryRepository) {
//        this.counterRepository = counterRepository;
//        this.programEnrollmentRepository = programEnrollmentRepository;
//        this.programRepository = programRepository;
//        this.consumerRepository = consumerRepository;
//        this.providerRepository = providerRepository;
//        this.categoryRepository = categoryRepository;
//    }
//
//    @Bean
//    @Qualifier("standardModelMapper")
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper =  new ModelMapper();
//        modelMapper.addConverter(Converters.entitySetToId(Provider.class, Long.class));
//        modelMapper.addConverter(Converters.idSetToEntitySet(providerRepository));
//        modelMapper.addConverter(Converters.entitySetToId(Counter.class, Long.class));
//        modelMapper.addConverter(Converters.idSetToEntitySet(counterRepository));
//        modelMapper.addConverter(Converters.entitySetToId(Program.class, Long.class));
//        modelMapper.addConverter(Converters.idSetToEntitySet(programRepository));
//        modelMapper.addConverter(Converters.entitySetToId(ProgramEnrollment.class, Long.class));
//        modelMapper.addConverter(Converters.idSetToEntitySet(programEnrollmentRepository));
//        modelMapper.addConverter(Converters.entitySetToId(Consumer.class, Long.class));
//        modelMapper.addConverter(Converters.idSetToEntitySet(consumerRepository));
//        modelMapper.addConverter(Converters.entitySetToId(Category.class, Long.class));
//        modelMapper.addConverter(Converters.idSetToEntitySet(categoryRepository));
//        return modelMapper;
//
//    }
//
//    @Bean
//    @Qualifier("modelMapperNullSkip")
//    public ModelMapper modelMapperNullSkip() {
//        ModelMapper modelMapperNullSkip = new ModelMapper();
//        modelMapperNullSkip.getConfiguration().setSkipNullEnabled(true)
//                .setPropertyCondition(Conditions.isNotNull());
//
//        modelMapperNullSkip.getConfiguration()
//                .setFieldMatchingEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//
//        modelMapperNullSkip.addConverter(Converters.entitySetToId(Provider.class, Long.class));
//        modelMapperNullSkip.addConverter(Converters.idSetToEntitySet(providerRepository));
//        modelMapperNullSkip.addConverter(Converters.entitySetToId(Counter.class, Long.class));
//        modelMapperNullSkip.addConverter(Converters.idSetToEntitySet(counterRepository));
//        modelMapperNullSkip.addConverter(Converters.entitySetToId(Program.class, Long.class));
//        modelMapperNullSkip.addConverter(Converters.idSetToEntitySet(programRepository));
//        modelMapperNullSkip.addConverter(Converters.entitySetToId(ProgramEnrollment.class, Long.class));
//        modelMapperNullSkip.addConverter(Converters.idSetToEntitySet(programEnrollmentRepository));
//        modelMapperNullSkip.addConverter(Converters.entitySetToId(Consumer.class, Long.class));
//        modelMapperNullSkip.addConverter(Converters.idSetToEntitySet(consumerRepository));
//        modelMapperNullSkip.addConverter(Converters.entitySetToId(Category.class, Long.class));
//        modelMapperNullSkip.addConverter(Converters.idSetToEntitySet(categoryRepository));
//
//        return modelMapperNullSkip;
//    }
//
//
//    public static class Converters {
//
//        private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
//
//        public static <T, ID> Converter<Set<T>, Set<ID>> entitySetToId(Class<T> entityType, Class<ID> idType) {
//            return ctx -> {
//                if (ctx.getSource() == null) {
//                    logger.info("Source entity set is null, returning null");
//                    return null;
//                }
//
//                return ctx.getSource().stream().map(entity -> {
//                    try {
//                        return entityType.getMethod("getId").invoke(entity);
//                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                        logger.error("Error accessing 'getId' method on entity of type {}", entityType.getName(), e);
//                        throw new RuntimeException("Error during entity to ID conversion", e);
//                    }
//                }).map(idType::cast).collect(Collectors.toSet());
//            };
//        }
//
//        public static <T, ID> Converter<Set<ID>, Set<T>> idSetToEntitySet(JpaRepository<T, ID> repository) {
//            return ctx -> {
//                if (ctx.getSource() == null) {
//                    logger.info("Source ID set is null, returning null");
//                    return null;
//                }
//
//                return ctx.getSource().stream().map(id -> {
//                    try {
//                        return repository.findById(id).orElseThrow(() ->
//                                new EntityNotFoundException("Entity not found for ID: " + id));
//                    } catch (EntityNotFoundException e) {
//                        logger.error("Entity not found for ID: {}", id, e);
//                        throw new RuntimeException("Entity not found during conversion", e);
//                    }
//                }).collect(Collectors.toSet());
//            };
//        }
//    }
//
//}
