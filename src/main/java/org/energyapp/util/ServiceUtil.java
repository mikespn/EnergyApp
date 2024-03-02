package org.energyapp.util;

import org.energyapp.exceptions.DataFetchingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class ServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServiceUtil.class.getName());

    /**
     *
     * @param ids The set of entity IDs to fetch.
     * @param repository The JPARepository instance used for fetching the ids.
     * @return A set of entities corresponding to the provided Ids.
     * @param <T> Placeholder for the type of the entity.
     * @throws DataFetchingException if the fetch process encounters an error.
     */
    public <T> Set<T> fetchEntitiesByIds(Set<Long> ids, JpaRepository<T, Long> repository) {
        if (ids == null || ids.isEmpty()) {
            logger.error("Attempted to fetch entities with a null or empty set of IDs");
            throw new DataFetchingException("Cannot fetch entities with null or empty IDs");
        }
        try {
            return new HashSet<>(repository.findAllById(ids));
        } catch (Exception e) {
            logger.error("Error fetching entities: " + e.getMessage(), e);
            throw new DataFetchingException("Error occurred during fetching entities", e);
        }
    }


}
