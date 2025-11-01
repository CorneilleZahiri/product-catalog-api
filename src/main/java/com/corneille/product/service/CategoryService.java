package com.corneille.product.service;

import com.corneille.product.dto.CategoryDto;
import com.corneille.product.dto.CategoryRequest;
import com.corneille.product.entity.Category;
import com.corneille.product.exception.AttributeAlreadyExistException;
import com.corneille.product.exception.EntityNotFoundException;
import com.corneille.product.mapper.CategoryMapper;
import com.corneille.product.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EntityManager entityManager;

    @Transactional
    public CategoryDto createCategory(CategoryRequest request) {
        //Contrôler le doublon
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new AttributeAlreadyExistException("Erreur de doublon! Le nom " + request.getName() + " existe déjà.");
        }

        //Convertir le paramètre en entité
        Category category = categoryMapper.toEntity(request);

        //Ajouter l'objet à EntityManager dans un 1er temps avec save() puis faire l'insertion en BD immédiatement
        Category savedCategory = categoryRepository.saveAndFlush(category);

        // Vider le cache et recharger depuis la BD
        entityManager.refresh(savedCategory);

        return categoryMapper.toDto(savedCategory);
    }

    @Transactional
    public Page<CategoryDto> categoryDtoPage(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    @Transactional
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("L'id " + id + " n'existe pas.");
        }

        return categoryMapper.toDto(category);
    }
}
