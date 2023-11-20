package com.stockmanager.purchase.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmanager.product.domain.Product;
import com.stockmanager.product.service.ProductDomainService;
import com.stockmanager.purchase.domain.Purchase;
import com.stockmanager.purchase.dto.request.SinglePurchaseRequest;
import com.stockmanager.purchase.dto.response.SinglePurchaseResponse;
import com.stockmanager.purchase.repository.PurchaseRepository;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Timed("custom.purchase")
public class PurchaseService {
	private final ProductDomainService productDomainService;
	private final PurchaseRepository purchaseRepository;

	@Transactional
	public SinglePurchaseResponse purchaseWithoutSync(SinglePurchaseRequest singlePurchaseRequest) {
		Product findProduct = productDomainService.findDomain(singlePurchaseRequest.getProductId());
		findProduct.reduceStockQuantity(singlePurchaseRequest.getQuantity()); // OutOfStockQuantityException 발생시 롤백

		Purchase newPurchase = new Purchase(LocalDateTime.now());
		Purchase savedPurchase = purchaseRepository.save(newPurchase);
		return new SinglePurchaseResponse(savedPurchase.getId());
	}

	public long count() {
		return purchaseRepository.count();
	}
}

