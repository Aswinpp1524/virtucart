package com.server.virtucart.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Product;
import com.server.virtucart.model.Rating;
import com.server.virtucart.model.User;
import com.server.virtucart.repository.RatingRepository;
import com.server.virtucart.request.RatingRequest;
import com.server.virtucart.service.ProductService;
import com.server.virtucart.service.RatingServices;

@Service
public class RatingServiceImpl implements RatingServices {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ProductService productService;

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());

		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		return ratingRepository.getAllProductsRating(productId);
	}

}
