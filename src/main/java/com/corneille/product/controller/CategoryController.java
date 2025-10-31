package com.corneille.product.controller;

import com.corneille.product.dto.CategoryDto;
import com.corneille.product.dto.CategoryRequest;
import com.corneille.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

        URI location = uriComponentsBuilder.path("category/{id}").buildAndExpand(categoryDto.getId()).toUri();

        return ResponseEntity.created(location).body(categoryDto);
    }
}
