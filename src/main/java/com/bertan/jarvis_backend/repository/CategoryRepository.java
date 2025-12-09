package com.bertan.jarvis_backend.repository;

import com.bertan.jarvis_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Dictionary;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
