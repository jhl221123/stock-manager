package com.stockmanager.purchase.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmanager.ProductFixture;
import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.service.ProductDomainService;
import com.stockmanager.purchase.dto.request.SinglePurchaseRequest;
import com.stockmanager.purchase.service.PurchaseService;

@SpringBootTest
public class PurchaseConcurrencyTest {
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	ProductDomainService productService;
	ProductFixture productFixture = new ProductFixture();

	@Test
	@DisplayName("다수의 사용자가 동시에 구매 요청을 보내면 동시성 문제가 발생한다.")
	void purchaseProductFailWithConcurrencyIssue() {
		//given
		ProductAddRequest productAddRequest = productFixture.getProductAddRequest();
		ProductAddResponse productAddResponse = productService.add(productAddRequest);
		Long productId = productAddResponse.getProductId();

		SinglePurchaseRequest singlePurchaseRequest = new SinglePurchaseRequest(productId, 1);
		Runnable purchasable = () -> purchaseService.purchaseWithoutSync(singlePurchaseRequest);

		//when
		concurrencyPurchase(purchasable);

		//then
		Integer remainingSQ = productService.findDomain(productId).getStockQuantity();
		long totalPurchase = purchaseService.count();
		assertThat(remainingSQ).isNotEqualTo(0);
		assertThat(totalPurchase).isEqualTo(10);
	}

	void concurrencyPurchase(Runnable purchasable) {
		for (int i = 0; i < 10; i++) {
				Thread thread = new Thread(purchasable);
				thread.start();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}