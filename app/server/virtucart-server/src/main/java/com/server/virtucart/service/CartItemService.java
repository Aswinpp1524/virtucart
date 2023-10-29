package com.server.virtucart.service;

import com.server.virtucart.exception.CartItemException;
import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.Cart;
import com.server.virtucart.model.CartItem;
import com.server.virtucart.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
