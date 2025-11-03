package com.corneille.product.repository;

import com.corneille.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p")
    Page<Product> productList(Pageable pageable);
}
