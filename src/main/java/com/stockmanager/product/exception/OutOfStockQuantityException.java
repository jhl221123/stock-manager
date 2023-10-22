package com.stockmanager.product.exception;

public class OutOfStockQuantityException extends RuntimeException{
	private static final String MESSAGE = "재고 수량이 부족합니다.";

	public OutOfStockQuantityException() {
		super(MESSAGE);
	}

	public OutOfStockQuantityException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
