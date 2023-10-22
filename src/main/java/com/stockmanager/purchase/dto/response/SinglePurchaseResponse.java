package com.stockmanager.purchase.dto.response;

import lombok.Getter;

@Getter
public class SinglePurchaseResponse {
	private Long purchaseId;

	public SinglePurchaseResponse(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
}
