package com.server.virtucart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Product;
import com.server.virtucart.request.ProductRequest;
import com.server.virtucart.response.ProductResponse;
import com.server.virtucart.service.ProductService;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/products")
	public ResponseEntity<ProductResponse> createMultipleProduct(@RequestBody List<ProductRequest> products)
			throws ProductException {
		ProductResponse productResponse = productService.createProduct(products);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.ACCEPTED);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> findAllProduct() {

		List<Product> products = productService.getAllProducts();

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProductHandler(@RequestBody Product req, @PathVariable Long productId)
			throws ProductException {

		Product updatedProduct = productService.updateProduct(productId, req);

		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ProductResponse> deleteProductHandler(@PathVariable Long productId) throws ProductException {

		String msg = productService.deleteProduct(productId);
		ProductResponse res = new ProductResponse(msg, productId);

		return new ResponseEntity<ProductResponse>(res, HttpStatus.ACCEPTED);

	}

}
