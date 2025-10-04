package org.example.taungoo.dao;

import org.example.taungoo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String CategoryName);
}
