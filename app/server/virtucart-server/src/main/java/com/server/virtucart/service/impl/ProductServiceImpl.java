package com.server.virtucart.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.server.virtucart.enums.StockStatus;
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

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public ProductResponse createProduct(List<ProductRequest> products) throws ProductException {

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

	private Product createNewProducts(ProductRequest productReq) throws ProductException {

		Product savedProduct = null;

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

		try {
			savedProduct = productRepository.save(product);
		} catch (DataIntegrityViolationException ex) {
			throw new ProductException("Failed to add product. Please check your input data.");
		}
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {

		Product product = findProductById(productId);

		product.getSizes().clear();
		productRepository.delete(product);
		logger.info("Product deleted successfully. Product ID: {}", productId);
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {

		Product product = findProductById(productId);

		if (req.getTitle() != null) {
			product.setTitle(req.getTitle());
		}
		if (req.getDescription() != null) {
			product.setDescription(req.getDescription());
		}
		if (req.getPrice() > 0) {
			product.setPrice(req.getPrice());
		}
		if (req.getDiscountedPrice() >= 0) {
			product.setDiscountedPrice(req.getDiscountedPrice());
		}
		if (req.getDiscountPercent() >= 0 && req.getDiscountPercent() <= 100) {
			product.setDiscountPercent(req.getDiscountPercent());
		}
		if (req.getQuantity() >= 0) {
			product.setQuantity(req.getQuantity());
		}
		if (req.getBrand() != null) {
			product.setBrand(req.getBrand());
		}
		if (req.getImageUrl() != null) {
			product.setImageUrl(req.getImageUrl());
		}

		try {
			return productRepository.save(product);
		} catch (DataIntegrityViolationException ex) {
			throw new ProductException("Failed to update product. Please try again.");
		}
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
		throw new ProductException("Product not found with ID: " + id);
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

		if (!CollectionUtils.isEmpty(colors)) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());

		}

		if (stock != null) {

			if (StockStatus.IN_STOCK.getValue().equals(stock)) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (StockStatus.OUT_OF_STOCK.getValue().equals(stock)) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}

		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

		return filteredProducts;

	}

}
