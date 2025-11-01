package com.corneille.product.mapper;

import com.corneille.product.dto.ProductDto;
import com.corneille.product.dto.ProductRequest;
import com.corneille.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    Product requestRoEntity(ProductRequest request);

    ProductDto entityToDto(Product product);
}
