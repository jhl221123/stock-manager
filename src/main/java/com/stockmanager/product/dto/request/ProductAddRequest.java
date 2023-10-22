package com.stockmanager.product.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAddRequest {
	@NotBlank
	@Length(min = 1, max = 20)
	private String name;
	@NotNull
	@Positive
	@Max(Integer.MAX_VALUE)
	private Integer price;
	@NotNull
	@Positive
	@Max(Integer.MAX_VALUE)
	private Integer stockQuantity;

	@Builder
	public ProductAddRequest(String name, Integer price, Integer stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
}
