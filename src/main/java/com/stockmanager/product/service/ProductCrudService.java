package com.stockmanager.product.service;

import org.springframework.stereotype.Service;

import com.stockmanager.product.domain.Product;
import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.dto.response.ProductFindResponse;
import com.stockmanager.product.exception.NotFoundProductException;
import com.stockmanager.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCrudService {
	private final ProductRepository productRepository;

	public ProductAddResponse add(ProductAddRequest request) {
		Product newProduct = Product.builder()
			.name(request.getName())
			.price(request.getPrice())
			.stockQuantity(request.getStockQuantity())
			.build();
		Product savedProduct = productRepository.save(newProduct);
		return new ProductAddResponse(savedProduct.getId());
	}

	public ProductFindResponse find(Long productId) {
		Product findProduct = productRepository.findById(productId)
			.orElseThrow(NotFoundProductException::new);
		return new ProductFindResponse(findProduct);
	}

	public Long countAll() {
		return productRepository.count();
	}
}
