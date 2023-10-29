package com.server.virtucart.service;

import java.util.List;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Review;
import com.server.virtucart.model.User;
import com.server.virtucart.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
