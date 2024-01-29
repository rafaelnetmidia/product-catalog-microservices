package com.microservices.productcatalog.controller;

import com.microservices.productcatalog.domain.product.Product;
import com.microservices.productcatalog.domain.product.dto.ProductDTO;
import com.microservices.productcatalog.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> createProductData(@RequestBody ProductDTO productData) {
        Product createdProduct = this.service.createProduct(productData);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> ListAllProductData() {
        return ResponseEntity.ok().body(this.service.listAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable("id") String id, @RequestBody ProductDTO productData) {
        return ResponseEntity.ok().body(this.service.updateProduct(id, productData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> DeleteProduct(@PathVariable("id") String id) {
        this.service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
