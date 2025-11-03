package com.corneille.product.service;

import com.corneille.product.dto.CategoryDto;
import com.corneille.product.dto.ProductDto;
import com.corneille.product.dto.ProductRequest;
import com.corneille.product.entity.Product;
import com.corneille.product.exception.AttributeAlreadyExistException;
import com.corneille.product.mapper.CategoryMapper;
import com.corneille.product.mapper.ProductMapper;
import com.corneille.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final EntityManager entityManager;
    private final CategoryService categoryService;

    @Transactional
    public ProductDto createProduct(ProductRequest request) {
        //Vérifier si l'objet category existe
        CategoryDto categoryDto = categoryService.getCategoryById(request.getCategoryId());

        //Contrôle de doublon sur le nom du produit
        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new AttributeAlreadyExistException("Erreur de doublon! Le produit " + request.getName() + " existe déjà");
        }

        //Mapping
        Product product = productMapper.requestRoEntity(request);
        //Liaison avec l'entité catégorie
        product.setCategory(categoryMapper.dtoToEntity(categoryDto));

        //Enregistrer en base de données
        Product productSaved = productRepository.saveAndFlush(product);

        //Mettre à jour le cache grâce à entityManager
        entityManager.refresh(productSaved);

        return productMapper.entityToDto(productSaved);
    }

    @Transactional
    public Page<ProductDto> productList(Pageable pageable) {
        return productRepository.productList(pageable).map(productMapper::entityToDto);
    }
}
