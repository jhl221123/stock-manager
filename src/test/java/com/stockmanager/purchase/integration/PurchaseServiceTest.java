package com.stockmanager.purchase.integration;

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
import com.stockmanager.product.service.ProductDomainService;
import com.stockmanager.purchase.dto.request.SinglePurchaseRequest;
import com.stockmanager.product.exception.OutOfStockQuantityException;
import com.stockmanager.ProductFixture;
import com.stockmanager.purchase.service.PurchaseService;

@SpringBootTest
class PurchaseServiceTest {
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ProductDomainService productServiceImpl;
	private ProductFixture productFixture = new ProductFixture();

	@Test
	@DisplayName("상품 구매에 성공하면, 해당 상품의 재고는 구매한 수량 만큼 감소한다.")
	@Transactional
	void purchaseProductSuccess() {
		//given
		ProductAddRequest productAddRequest = productFixture.getProductAddRequest();
		ProductAddResponse productAddResponse = productServiceImpl.add(productAddRequest);

		SinglePurchaseRequest singlePurchaseRequest = new SinglePurchaseRequest(productAddResponse.getProductId(), 5);

		//when
		purchaseService.purchaseWithoutSync(singlePurchaseRequest);

		//then
		ProductFindResponse productFindResponse = productServiceImpl.findDto(productAddResponse.getProductId());
		assertThat(productFindResponse.getStockQuantity()).isEqualTo(productAddRequest.getStockQuantity() - 5);
	}

	@Test
	@DisplayName("상품 재고가 부족하면, 구매에 실패하고 예외가 발생한다.")
	void purchaseProductFailOutOfStockQuantity() {
		//given
		ProductAddRequest productAddRequest = productFixture.getProductAddRequest();
		ProductAddResponse productAddResponse = productServiceImpl.add(productAddRequest);

		SinglePurchaseRequest singlePurchaseRequest = new SinglePurchaseRequest(productAddResponse.getProductId(), 50);

		//expected
		assertThrows(OutOfStockQuantityException.class, () -> purchaseService.purchaseWithoutSync(singlePurchaseRequest));
	}

	@Test
	@DisplayName("존재하지 않는 상품을 구매하려고 하면, 구매에 실패하고 예외가 발생한다.")
	void purchaseProductFailNotFoundQuantity() {
		ProductAddRequest productAddRequest = productFixture.getProductAddRequest();
		ProductAddResponse productAddResponse = productServiceImpl.add(productAddRequest);

		SinglePurchaseRequest singlePurchaseRequest = new SinglePurchaseRequest(productAddResponse.getProductId()+100, 5);
		// TODO: 2023-10-15 존재하지 않는 id로 조회하는 상황을 위해 +100 하는 방식이 좋은 방식인가?, 모든 테스트에 @Transactional을 선언해야할까?
		//expected
		assertThrows(NotFoundProductException.class, () -> purchaseService.purchaseWithoutSync(singlePurchaseRequest));
	}
}