package com.stockmanager.purchase.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmanager.product.domain.Product;
import com.stockmanager.purchase.domain.Purchase;
import com.stockmanager.purchase.dto.request.SinglePurchaseRequest;
import com.stockmanager.purchase.dto.response.SinglePurchaseResponse;
import com.stockmanager.product.exception.NotFoundProductException;
import com.stockmanager.product.repository.ProductRepository;
import com.stockmanager.purchase.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	private final ProductRepository productRepository;
	private final PurchaseRepository purchaseRepository;

	@Transactional
	public SinglePurchaseResponse purchase(SinglePurchaseRequest singlePurchaseRequest) {
		Product findProduct = getProduct(singlePurchaseRequest.getProductId());
		findProduct.reduceStockQuantity(singlePurchaseRequest.getQuantity()); // OutOfStockQuantityException 발생시 롤백

		Purchase newPurchase = new Purchase(LocalDateTime.now());
		Purchase savedPurchase = purchaseRepository.save(newPurchase);
		return new SinglePurchaseResponse(savedPurchase.getId());
	}

	private Product getProduct(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(NotFoundProductException::new);
	}
}
