package com.stockmanager.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmanager.FixtureRegistry;
import com.stockmanager.product.dto.request.ProductAddRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductCrudTest {
	@Autowired
	private MockMvc mockMvc;
	private FixtureRegistry fixtureRegistry = new FixtureRegistry();
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("상품 등록에 성공하면, 구매한 상품에 대한 응답과 상태코드를 반환한다.")
	@Transactional
	void productPurchaseSuccess() throws Exception{
		//given
		ProductAddRequest productAddRequest = fixtureRegistry.getProductAddRequest();
		String requestJson = objectMapper.writeValueAsString(productAddRequest);

		//expected
		mockMvc.perform(MockMvcRequestBuilders.post("/product")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson)
		)
			.andExpect(MockMvcResultMatchers.status().isCreated());
		// TODO: 2023-10-15 여기서 생성된 id는 어떻게 확인할 수 있을까?
	}
}
