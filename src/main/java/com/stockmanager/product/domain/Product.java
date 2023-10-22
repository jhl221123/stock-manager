package com.stockmanager.product.domain;

import com.stockmanager.product.exception.OutOfStockQuantityException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer price;
	private Integer stockQuantity;

	@Builder
	public Product(String name, Integer price, Integer stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public void reduceStockQuantity(Integer quantity) {
		int afterReduce = this.stockQuantity - quantity;
		if (afterReduce < 0) {
			throw new OutOfStockQuantityException();
		}
		this.stockQuantity = afterReduce;
	}
}
