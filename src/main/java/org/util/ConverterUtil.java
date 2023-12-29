package org.util;

import org.modelmapper.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class ConverterUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConverterUtil.class);
    public static <T, ID> Converter<Set<T>, Set<ID>> entitySetToId(Class<T> entityType, Class<ID> idType) {
        return ctx -> {
            if (ctx.getSource() == null) {
                logger.info("Source entity set is null, returning null");
                return null;
            }

            return ctx.getSource().stream().map(entity -> {
                try {
                    return entityType.getMethod("getId").invoke(entity);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.error("Error accessing 'getId' method on entity of type {}", entityType.getName(), e);
                    throw new RuntimeException("Error during entity to ID conversion", e);
                }
            }).map(idType::cast).collect(Collectors.toSet());
        };
    }

    public static <T, ID> Converter<Set<ID>, Set<T>> idSetToEntitySetConverter(JpaRepository<T, ID> repository) {
        return ctx -> {
            if (ctx.getSource() == null) {
                logger.info("Source ID set is null, returning null");
                return null;
            }

            return ctx.getSource().stream().map(id -> {
                try {
                    return repository.findById(id).orElseThrow(() ->
                            new EntityNotFoundException("Entity not found for ID: " + id));
                } catch (EntityNotFoundException e) {
                    logger.error("Entity not found for ID: {}", id, e);
                    throw new RuntimeException("Entity not found during conversion", e);
                }
            }).collect(Collectors.toSet());
        };
    }
}
