package com.stockmanager;

import com.stockmanager.product.dto.request.ProductAddRequest;

public class FixtureRegistry {
	private final String NAME = "product_name";
	private final Integer PRICE = 10000;
	private final Integer STOCK_QUANTITY = 10;

	public ProductAddRequest getProductAddRequest() {
		return ProductAddRequest.builder()
			.name(NAME)
			.price(PRICE)
			.stockQuantity(STOCK_QUANTITY)
			.build();
	}
}
