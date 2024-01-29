package com.microservices.productcatalog.service;

import com.microservices.productcatalog.domain.category.Category;
import com.microservices.productcatalog.domain.category.dto.CategoryDTO;
import com.microservices.productcatalog.domain.category.exception.CategoryNotFoundException;
import com.microservices.productcatalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category createCategory(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        return newCategory;
    }

    public List<Category> listAllCategory() {
        return this.repository.findAll();
    }

    public Optional<Category> findByCategoryId(String id) {
        return this.repository.findById(id);
    }

    public Category updateCategory(String id, CategoryDTO categoryData) {

        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());

        this.repository.save(category);
        return category;
    }

    public void deleteCategory(String id) {

        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(category);
    }
}
