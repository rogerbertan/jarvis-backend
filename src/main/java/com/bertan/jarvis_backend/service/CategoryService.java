package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.category.CategoryRequestDTO;
import com.bertan.jarvis_backend.dto.category.CategoryResponseDTO;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<CategoryResponseDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toResponseDTO);
    }

    @Transactional
    public CategoryResponseDTO save(CategoryRequestDTO requestDTO) {
        Category category = new Category();
        category.setName(requestDTO.name());
        category.setType(requestDTO.type());

        Category savedCategory = categoryRepository.save(category);
        return toResponseDTO(savedCategory);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        category.setName(requestDTO.name());
        category.setType(requestDTO.type());

        Category updatedCategory = categoryRepository.save(category);
        return toResponseDTO(updatedCategory);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType()
        );
    }
}