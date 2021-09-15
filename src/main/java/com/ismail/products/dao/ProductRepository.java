package com.ismail.products.dao;

import com.ismail.products.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    public Page<Product> findByDesignationContains(String keyword, Pageable pageable);
}
