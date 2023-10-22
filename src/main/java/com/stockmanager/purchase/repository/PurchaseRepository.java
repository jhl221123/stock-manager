package com.stockmanager.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmanager.purchase.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
