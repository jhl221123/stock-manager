package com.stockmanager.purchase;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmanager.FixtureRegistry;
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
	FixtureRegistry fixtureRegistry = new FixtureRegistry();

	@Test
	@DisplayName("다수의 사용자가 동시에 구매 요청을 보내면 동시성 문제가 발생한다.")
	void purchaseProductFailWithConcurrencyIssue() {
		//given
		ProductAddRequest productAddRequest = fixtureRegistry.getProductAddRequest();
		ProductAddResponse productAddResponse = productService.add(productAddRequest);

		SinglePurchaseRequest singlePurchaseRequest = new SinglePurchaseRequest(productAddResponse.getProductId(), 1);
		Runnable purchasable = () -> purchaseService.purchase(singlePurchaseRequest);

		//when
		assertThrows(Throwable.class, () -> concurrencyPurchase(purchasable));
	}

	void concurrencyPurchase(Runnable purchasable) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
				Thread thread = new Thread(purchasable);
				thread.start();
		}
		Thread.sleep(1000);
	}
}