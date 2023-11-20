package com.stockmanager.purchase.unit;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmanager.purchase.controller.PurchaseController;
import com.stockmanager.purchase.dto.request.SinglePurchaseRequest;
import com.stockmanager.purchase.dto.response.SinglePurchaseResponse;
import com.stockmanager.purchase.service.PurchaseService;

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTest {
	@InjectMocks
	private PurchaseController purchaseController;
	@Mock
	private PurchaseService purchaseService;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	@DisplayName("상품 구매에 성공하면 구매에 대한 응답과 상태코드를 반환한다.")
	void purchaseProductSuccess() throws Exception {
		//given
		SinglePurchaseRequest singlePurchaseRequest = new SinglePurchaseRequest(2L, 5);
		String requestJson = objectMapper.writeValueAsString(singlePurchaseRequest);

		SinglePurchaseResponse singlePurchaseResponse = new SinglePurchaseResponse(1L);
		String responseJson = objectMapper.writeValueAsString(singlePurchaseResponse);

		doReturn(singlePurchaseResponse).when(purchaseService).purchaseWithoutSync(any(SinglePurchaseRequest.class));

		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/purchases")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
}
