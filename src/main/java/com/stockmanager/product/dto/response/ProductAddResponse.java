package com.stockmanager.product.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductAddResponse {
	@NotNull
	private Long productId;

	public ProductAddResponse(Long productId) {
		this.productId = productId;
	}
}
