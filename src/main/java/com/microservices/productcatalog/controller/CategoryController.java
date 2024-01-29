package com.microservices.productcatalog.controller;

import com.microservices.productcatalog.domain.category.Category;
import com.microservices.productcatalog.domain.category.dto.CategoryDTO;
import com.microservices.productcatalog.service.CategoryService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> createCategoryData(@RequestBody CategoryDTO categoryData) {
        Category createdCategory = this.service.createCategory(categoryData);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> ListAllCategoryData() {
        return ResponseEntity.ok().body(this.service.listAllCategory());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> UpdateCategory(@PathVariable("id") String id, @RequestBody CategoryDTO categoryData) {
        return ResponseEntity.ok().body(this.service.updateCategory(id, categoryData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> DeleteCategory(@PathVariable("id") String id) {
        this.service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
