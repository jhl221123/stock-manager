package com.stockmanager.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping("/products")
	public ResponseEntity<ProductAddResponse> add(@Valid @RequestBody ProductAddRequest productAddRequest) {
		ProductAddResponse productAddResponse = productService.add(productAddRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(productAddResponse);
	}
}
