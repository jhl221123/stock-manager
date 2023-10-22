package com.stockmanager.product.exception;

public class NotFoundProductException extends RuntimeException {
	private static final String MESSAGE = "요청한 제품이 존재하지 않습니다.";

	public NotFoundProductException() {
		super(MESSAGE);
	}

	public NotFoundProductException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
