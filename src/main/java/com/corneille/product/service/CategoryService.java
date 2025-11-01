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
        Category category = categoryMapper.requestRoEntity(request);

        //Ajouter l'objet à EntityManager dans un 1er temps avec save() puis faire l'insertion en BD immédiatement
        Category savedCategory = categoryRepository.saveAndFlush(category);

        // Vider le cache et recharger depuis la BD
        entityManager.refresh(savedCategory);

        return categoryMapper.entityToDto(savedCategory);
    }

    @Transactional
    public Page<CategoryDto> categoryDtoPage(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::entityToDto);
    }

    @Transactional
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("L'id " + id + " n'existe pas.");
        }

        return categoryMapper.entityToDto(category);
    }

    @Transactional
    public CategoryDto updateCategory(CategoryRequest request, Long id) {
        //Convertion en entité
        Category category = categoryMapper.dtoToEntity(getCategoryById(id));

        //Contrôler le doublon sur le nom de la catégorie
        Category searchCategoryByName = categoryRepository.findByName(request.getName()).orElse(null);
        if (searchCategoryByName != null && id != searchCategoryByName.getId()) {
            throw new AttributeAlreadyExistException("Erreur de doublon! Le nom " + request.getName() + " existe déjà.");
        }

        //Mapping
        categoryMapper.update(request, category);

        //Ajouter l'objet à EntityManager dans un 1er temps avec save() puis faire l'insertion en BD immédiatement
        Category savedCategory = categoryRepository.saveAndFlush(category);

        // Vider le cache et recharger depuis la BD
        entityManager.refresh(savedCategory);

        return categoryMapper.entityToDto(savedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        //Convertion en entité
        Category category = categoryMapper.dtoToEntity(getCategoryById(id));
        categoryRepository.delete(category);
    }
}
