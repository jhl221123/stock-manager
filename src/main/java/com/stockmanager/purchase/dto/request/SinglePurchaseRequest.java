package com.stockmanager.purchase.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SinglePurchaseRequest {
	@NotNull
	private Long productId;
	@NotNull
	@Positive
	@Max(Integer.MAX_VALUE)
	private Integer quantity;

	public SinglePurchaseRequest(Long productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
