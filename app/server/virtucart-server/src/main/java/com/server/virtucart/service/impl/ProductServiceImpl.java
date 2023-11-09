package com.server.virtucart.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Category;
import com.server.virtucart.model.Product;
import com.server.virtucart.repository.CategoryRepository;
import com.server.virtucart.repository.ProductRepository;
import com.server.virtucart.request.ProductRequest;
import com.server.virtucart.response.ProductResponse;
import com.server.virtucart.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ProductResponse createProduct(List<ProductRequest> products) {

		List<Long> productsId = new ArrayList<>();
		for (ProductRequest product : products) {
			Product savedProduct = createNewProducts(product);
			productsId.add(savedProduct.getId());
		}
		ProductResponse productResponse = new ProductResponse();
		productResponse.setMessage("Products added successfully");
		productResponse.setProductsId(productsId);
		return productResponse;
	}

	private Product createNewProducts(ProductRequest productReq) {
		Category topLevel = categoryRepository.findByName(productReq.getTopLevelCategory());

		if (topLevel == null) {

			Category topLavelCategory = new Category();
			topLavelCategory.setName(productReq.getTopLevelCategory());
			topLavelCategory.setLevel(1);

			topLevel = categoryRepository.save(topLavelCategory);
		}

		Category secondLevel = categoryRepository.findByNameAndParant(productReq.getSecondLevelCategory(),
				topLevel.getName());

		if (secondLevel == null) {

			Category secondLavelCategory = new Category();
			secondLavelCategory.setName(productReq.getSecondLevelCategory());
			secondLavelCategory.setParentCategory(topLevel);
			secondLavelCategory.setLevel(2);
			secondLevel = categoryRepository.save(secondLavelCategory);
		}

		Category thirdLevel = categoryRepository.findByNameAndParant(productReq.getThirdLevelCategory(),
				secondLevel.getName());

		if (thirdLevel == null) {

			Category thirdLavelCategory = new Category();
			thirdLavelCategory.setName(productReq.getThirdLevelCategory());
			thirdLavelCategory.setParentCategory(secondLevel);
			thirdLavelCategory.setLevel(3);
			thirdLevel = categoryRepository.save(thirdLavelCategory);
		}

		Product product = new Product();
		product.setTitle(productReq.getTitle());
		product.setColor(productReq.getColor());
		product.setDescription(productReq.getDescription());
		product.setDiscountedPrice(productReq.getDiscountedPrice());
		product.setDiscountPercent(productReq.getDiscountPercent());
		product.setImageUrl(productReq.getImageUrl());
		product.setBrand(productReq.getBrand());
		product.setPrice(productReq.getPrice());
		product.setSizes(productReq.getSize());
		product.setQuantity(productReq.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());

		Product savedProduct = productRepository.save(product);

		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {

		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		String msg = "Product deleted successfully";
		return msg;
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product = findProductById(productId);

		if (req.getQuantity() != 0) {
			product.setQuantity(req.getQuantity());
		}
		if (req.getDescription() != null) {
			product.setDescription(req.getDescription());
		}

		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt = productRepository.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id: " + id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {

		List<Product> products = productRepository.findByCategory(category);

		return products;
	}

	@Override
	public List<Product> searchProduct(String query) {
		List<Product> products = productRepository.searchProduct(query);
		return products;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

		if (!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());

		}

		if (stock != null) {

			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}

		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		return filteredProducts; // If color list is empty, do nothing and return all products

	}

}
