package com.stockmanager.product.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.dto.response.ProductFindResponse;
import com.stockmanager.product.exception.NotFoundProductException;
import com.stockmanager.ProductFixture;
import com.stockmanager.product.service.ProductDomainService;

@SpringBootTest
class ProductServiceImplTest {
	@Autowired
	private ProductDomainService productServiceImpl;
	private final ProductFixture productFixture = new ProductFixture();

	@Test
	@DisplayName("등록과 조회 모두 성공적으로 수행되면, 조회한 상품의 정보는 등록한 상품의 정보와 같다.")
	@Transactional
	void productAddAndFindSuccess() {
		checkTotalCount(0L);
		ProductAddRequest productAddRequest = productFixture.getProductAddRequest();
		ProductAddResponse productAddResponse = productServiceImpl.add(productAddRequest);
		checkTotalCount(1L);

		ProductFindResponse productFindResponse = productServiceImpl.findDto(productAddResponse.getProductId());
		checkSameProduct(productFindResponse, productAddRequest);
	}

	@Test
	@DisplayName("존재하지 않는 상품을 조회하면, 조회는 실패하고 예외가 발생한다.")
	void productFindFailWithNotFound() {
		assertThrows(NotFoundProductException.class, ()-> productServiceImpl.findDto(100L));
	}

	private void checkTotalCount(Long expectedCount) {
		Long currentCount = productServiceImpl.countAll();
		assertThat(currentCount).isEqualTo(expectedCount);
	}

	private void checkSameProduct(ProductFindResponse productFindResponse, ProductAddRequest productAddRequest) {
		assertThat(productFindResponse.getName()).isEqualTo(productAddRequest.getName());
		assertThat(productFindResponse.getPrice()).isEqualTo(productAddRequest.getPrice());
		assertThat(productFindResponse.getStockQuantity()).isEqualTo(productAddRequest.getStockQuantity());
	}
}