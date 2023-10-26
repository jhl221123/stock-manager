package com.stockmanager.product.service;

import com.stockmanager.product.domain.Product;

public interface ProductDomainService extends ProductService {
	public Product findDomain(Long productId);
}
