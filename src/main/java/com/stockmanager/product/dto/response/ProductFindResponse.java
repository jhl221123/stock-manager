package com.stockmanager.product.dto.response;

import com.stockmanager.product.domain.Product;

import lombok.Getter;

@Getter
public class ProductFindResponse {
	private Long id;
	private String name;
	private Integer price;
	private Integer stockQuantity;

	public ProductFindResponse(Product aProduct) {
		this.id = aProduct.getId();
		this.name = aProduct.getName();
		this.price = aProduct.getPrice();
		this.stockQuantity = aProduct.getStockQuantity();
	}
}
