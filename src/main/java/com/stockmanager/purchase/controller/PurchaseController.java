package com.stockmanager.purchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.service.ProductService;
import com.stockmanager.purchase.dto.request.SinglePurchaseRequest;
import com.stockmanager.purchase.dto.response.SinglePurchaseResponse;
import com.stockmanager.purchase.service.PurchaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
	private final PurchaseService purchaseService;

	@PostMapping("/purchases")
	public ResponseEntity<SinglePurchaseResponse> purchase(@Valid @RequestBody SinglePurchaseRequest singlePurchaseRequest) {
		SinglePurchaseResponse singlePurchaseResponse = purchaseService.purchaseWithoutSync(singlePurchaseRequest);
		return ResponseEntity.status(HttpStatus.OK) // TODO: 2023-10-27 구매 도메인을 생성하니까 isCreated가 더 적절한가?
			.body(singlePurchaseResponse);
	}
}
