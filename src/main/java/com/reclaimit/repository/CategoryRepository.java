package com.reclaimit.repository;

import com.reclaimit.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // find category by name (useful to avoid duplicates)
    Optional<Category> findByName(String name);

    // check if category already exists
    boolean existsByName(String name);
}
