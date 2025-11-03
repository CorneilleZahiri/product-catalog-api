package com.corneille.product.mapper;

import com.corneille.product.dto.ProductDto;
import com.corneille.product.dto.ProductRequest;
import com.corneille.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    Product requestRoEntity(ProductRequest request);

    ProductDto entityToDto(Product product);

    Product dtoToEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    void updateProduct(ProductRequest request, @MappingTarget Product product);
}
