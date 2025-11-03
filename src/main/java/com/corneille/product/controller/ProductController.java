package com.corneille.product.controller;

import com.corneille.product.dto.ProductDto;
import com.corneille.product.dto.ProductRequest;
import com.corneille.product.service.ProductService;
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
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductRequest request,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        ProductDto productDto = productService.createProduct(request);

        URI location = uriComponentsBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();

        return ResponseEntity.created(location).body(productDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> productList(@RequestParam(required = false, name = "page", defaultValue = "0") int pageNumber,
                                                        @RequestParam(required = false, name = "size", defaultValue = "10") int size,
                                                        @RequestParam(required = false, name = "sort", defaultValue = "name") String sortBy,
                                                        @RequestParam(required = false, name = "direction", defaultValue = "asc") String direction) {
        //Dictionnaire des attributs sur lesquels porteront le tri
        Map<String, String> attributesAllowed = Map.of(
                "id", "id",
                "name", "name",
                "price", "price",
                "quantity", "quantity",
                "createdat", "createdAt");

        //Méthode qui renvoie le champ sur lequel portera le trie
        String field = SortField.chooseFieldToSort(attributesAllowed, sortBy);

        //Méthode qui renvoie la direction du trie sur le champ choisi
        Sort.Direction dir = SortField.directionOfSort(direction);

        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(dir, field));

        return ResponseEntity.ok().body(productService.productList(pageable));
    }

}
