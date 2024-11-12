package com.apuliadigitalmaker.buytopia.categories;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {


    private static final String notFoundMessage = "Category not found";

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategory() {
        return categoryRepository.findNotDeleted();
    }

    public Category findCategoryById(Long id) {
        return categoryRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        category.softDelete();
        categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Map<String, Object> update) {
        Optional<Category> optionalCategory = categoryRepository.findByIdNotDeleted(id);

        if (optionalCategory.isEmpty()) {
            throw new EntityNotFoundException(notFoundMessage);
        }

        Category category = optionalCategory.get();

        update.forEach((key, value) -> {
            switch (key) {
                case "name":
                    category.setName((String) value);
                    break;
                case "description":
                    category.setDescription((String) value);
                    break;
            }
        });

        return categoryRepository.save(category);
    }

    public List<Category> searchCategory(String query) {
        return categoryRepository.findByNameStartsWithIgnoreCaseAndDeletedAtIsNull(query);
    }
}