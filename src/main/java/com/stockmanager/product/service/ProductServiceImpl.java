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
public class ProductServiceImpl implements ProductDomainService{
	private final ProductRepository productRepository;

	@Override
	public ProductAddResponse add(ProductAddRequest request) {
		Product newProduct = Product.builder()
			.name(request.getName())
			.price(request.getPrice())
			.stockQuantity(request.getStockQuantity())
			.build();
		Product savedProduct = productRepository.save(newProduct);
		return new ProductAddResponse(savedProduct.getId());
	}

	@Override
	public ProductFindResponse findDto(Long productId) {
		Product findProduct = findDomain(productId);
		return new ProductFindResponse(findProduct);
	}

	@Override
	public Product findDomain(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(NotFoundProductException::new);
	}

	@Override
	public Long countAll() {
		return productRepository.count();
	}
}
