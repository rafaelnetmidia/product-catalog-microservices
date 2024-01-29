package com.microservices.productcatalog.service;

import com.microservices.productcatalog.domain.category.Category;
import com.microservices.productcatalog.domain.category.exception.CategoryNotFoundException;
import com.microservices.productcatalog.domain.product.Product;
import com.microservices.productcatalog.domain.product.dto.ProductDTO;
import com.microservices.productcatalog.domain.product.exception.ProductNotFoundException;
import com.microservices.productcatalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product createProduct(ProductDTO productData){

        Category category = this.categoryService
                .findByCategoryId(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        return newProduct;
    }

    public Product updateProduct(String id, ProductDTO productData) {

        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.categoryService.findByCategoryId(productData.categoryId())
                .ifPresent(product::setCategory);

        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());

        this.productRepository.save(product);
        return product;
    }

    public void deleteCategory(String id) {

        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(product);
    }

    public List<Product> listAllProduct() {
        return this.productRepository.findAll();
    }


}
