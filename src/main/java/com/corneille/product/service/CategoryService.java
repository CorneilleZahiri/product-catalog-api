package com.corneille.product.service;

import com.corneille.product.dto.CategoryDto;
import com.corneille.product.dto.CategoryRequest;
import com.corneille.product.entity.Category;
import com.corneille.product.exception.AttributeAlreadyExistException;
import com.corneille.product.mapper.CategoryMapper;
import com.corneille.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto createCategory(CategoryRequest request) {
        //Contrôler le doublon
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new AttributeAlreadyExistException("Erreur de doublon! Le nom " + request.getName() + " existe déjà.");
        }
        //Convertir le paramètre en entité
        Category category = categoryMapper.toEntity(request);

        return categoryMapper.toDto(categoryRepository.save(category));
    }
}
