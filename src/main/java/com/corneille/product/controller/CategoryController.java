package com.corneille.product.controller;

import com.corneille.product.dto.CategoryDto;
import com.corneille.product.dto.CategoryRequest;
import com.corneille.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryRequest request,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(request.getName());

        CategoryDto categoryDto = categoryService.createCategory(request);

        URI location = uriComponentsBuilder.path("/category/{id}").buildAndExpand(categoryDto.getId()).toUri();

        return ResponseEntity.created(location).body(categoryDto);
    }

    @GetMapping
    public Page<CategoryDto> categoryDtoPage(@RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                             @RequestParam(required = false, defaultValue = "10", name = "size") int size,
                                             @RequestParam(required = false, defaultValue = "name", name = "sort") String sortBy,
                                             @RequestParam(required = false, defaultValue = "asc", name = "direction") String direction) {
        //Les attributs concernés par le trie
        Map<String, String> allowedFields = Map.of(
                "name", "name",
                "id", "id",
                "createdat", "createdAt");

        //Méthode qui renvoie le champ sur lequel portera le trie
        String field = SortField.chooseFieldToSort(allowedFields, sortBy);

        //Méthode qui renvoie la direction du trie sur le champ choisi
        Sort.Direction dir = SortField.directionOfSort(direction);

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, field));

        return categoryService.categoryDtoPage(pageable);
    }
}
