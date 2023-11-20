package com.stockmanager.product.unit;

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
import com.stockmanager.ProductFixture;
import com.stockmanager.product.controller.ProductController;
import com.stockmanager.product.dto.request.ProductAddRequest;
import com.stockmanager.product.dto.response.ProductAddResponse;
import com.stockmanager.product.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	@InjectMocks
	private ProductController productController;
	@Mock
	private ProductService productService;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private ProductFixture productFixture;

	@BeforeEach
	void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		objectMapper = new ObjectMapper();
		productFixture = new ProductFixture();
	}

	@Test
	@DisplayName("상품등록을 성공하면 등록한 상품에 대한 응답과 상태코드를 반환한다.")
	void addProductSuccess() throws Exception {
		//given
		ProductAddRequest productAddRequest = productFixture.getProductAddRequest();
		String requestJson = objectMapper.writeValueAsString(productAddRequest);

		ProductAddResponse productAddResponse = new ProductAddResponse(1L);
		String responseJson = objectMapper.writeValueAsString(productAddResponse);

		doReturn(productAddResponse).when(productService).add(any(ProductAddRequest.class));

		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
			)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
}
