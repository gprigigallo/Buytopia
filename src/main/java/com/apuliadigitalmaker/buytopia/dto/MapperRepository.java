package com.apuliadigitalmaker.buytopia.dto;

import com.apuliadigitalmaker.buytopia.product.Product;

public abstract class MapperRepository  {

    public static ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());
        dto.setImageUrl(product.getImageUrl());
        dto.setAvailable(product.getAvailable());
        return dto;
    }
    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setImageUrl(dto.getImageUrl());
        product.setAvailable(dto.getAvailable());
        return product;
    }
}
