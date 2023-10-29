package com.server.virtucart.service;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Cart;
import com.server.virtucart.model.User;
import com.server.virtucart.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

	public Cart findUserCart(Long userId);

}
