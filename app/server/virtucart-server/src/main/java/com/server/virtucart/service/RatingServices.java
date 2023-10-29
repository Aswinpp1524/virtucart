package com.server.virtucart.service;

import java.util.List;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Rating;
import com.server.virtucart.model.User;
import com.server.virtucart.request.RatingRequest;

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
