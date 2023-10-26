package com.stockmanager.product.service;

import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.dto.response.ProductFindResponse;

public interface ProductService {
	public ProductAddResponse add(ProductAddRequest request);

	public ProductFindResponse findDto(Long productId);

	public Long countAll();
}
