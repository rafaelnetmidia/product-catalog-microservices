package com.microservices.productcatalog.domain.product.dto;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId){

}
