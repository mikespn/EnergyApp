package org.repository;


import org.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String Name);

    @Query("SELECT c FROM Category c WHERE c.programs IS EMPTY")
    List<Category> findEmptyCategories();

    @Query("SELECT DISTINCT c FROM Category c JOIN c.programs p WHERE p.provider.id = :providerId")
    List<Category> findCategoriesByProviderId(Long providerId);

}
