package com.corneille.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Le nom du produit est obligatoire")
    private String name;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être strictement supérieur à 0")
    private BigDecimal price;

    @NotNull(message = "La quantité ne peut être nulle")
    @Min(value = 0, message = "La valeur minimale est zéro")
    private Integer quantity;

    @NotNull(message = "L'id de la catégorie ne peut être null")
    private Long categoryId;
}