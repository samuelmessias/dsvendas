package com.dvsuperior.dscatalog.tests;

import java.time.Instant;

import com.dvsuperior.dscatalog.dto.ProductDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img/1-big.jpg", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(2L, "Electronic"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}

}
