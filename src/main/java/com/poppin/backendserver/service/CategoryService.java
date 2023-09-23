package com.poppin.backendserver.service;

import com.poppin.backendserver.entity.Category;
import com.poppin.backendserver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }
    @Transactional
    public Long saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.getId();
    }

    @Transactional
    public Long deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return id;
    }
}
