package com.stockmanager.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmanager.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
