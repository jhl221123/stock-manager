package com.stockmanager.purchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		SinglePurchaseResponse singlePurchaseResponse = purchaseService.purchase(singlePurchaseRequest);
		return ResponseEntity.status(HttpStatus.OK)
			.body(singlePurchaseResponse);
	}
}
