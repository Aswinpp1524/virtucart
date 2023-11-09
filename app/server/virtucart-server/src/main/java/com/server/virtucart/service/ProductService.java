package com.server.virtucart.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Product;
import com.server.virtucart.request.ProductRequest;
import com.server.virtucart.response.ProductResponse;

public interface ProductService {

	// only for admin
	public ProductResponse createProduct(List<ProductRequest> products) throws ProductException;

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product product) throws ProductException;

	public List<Product> getAllProducts();

	// for user and admin both
	public Product findProductById(Long id) throws ProductException;

	public List<Product> findProductByCategory(String category);

	public List<Product> searchProduct(String query);

	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

}
