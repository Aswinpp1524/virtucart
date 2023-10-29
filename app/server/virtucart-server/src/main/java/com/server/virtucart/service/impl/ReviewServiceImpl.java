package com.server.virtucart.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Product;
import com.server.virtucart.model.Review;
import com.server.virtucart.model.User;
import com.server.virtucart.repository.ProductRepository;
import com.server.virtucart.repository.ReviewRepository;
import com.server.virtucart.request.ReviewRequest;
import com.server.virtucart.service.ProductService;
import com.server.virtucart.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());

		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());

		productRepository.save(product);
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {

		return reviewRepository.getAllProductsReview(productId);
	}

}
