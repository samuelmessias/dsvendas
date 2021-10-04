package com.dvsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.dvsuperior.dscatalog.entities.Product;
import com.dvsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;
	
	private long exintingId;
	
	private long nonExintingId;
	
	private long countTotalProducts;	
	
	
	@BeforeEach
	void setUp() throws Exception{
		exintingId = 1L;
		nonExintingId = 1000L;
		countTotalProducts = 25L;
	}
	
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1 , product.getId());
	}
	
	
	@Test
	public void findByIdWhenIdExist() {
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdWhenIdExistNot() {
		Optional<Product> result = repository.findById(nonExintingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExist() {		
		
		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNotExist() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExintingId);
		});
	}
	
	
}
