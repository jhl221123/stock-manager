package com.stockmanager.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stockmanager.product.service.ProductService;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;

@Configuration
public class MetricConfig {

	@Bean
	public MeterBinder meterBinder(ProductService productService) {
		return registry -> Gauge.builder("custom.product", productService, ProductService::countAll).register(registry);
	}
}
