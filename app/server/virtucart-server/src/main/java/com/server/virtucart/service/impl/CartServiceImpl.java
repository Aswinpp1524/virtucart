package com.server.virtucart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.model.Cart;
import com.server.virtucart.model.CartItem;
import com.server.virtucart.model.Product;
import com.server.virtucart.model.User;
import com.server.virtucart.repository.CartRepository;
import com.server.virtucart.request.AddItemRequest;
import com.server.virtucart.service.CartItemService;
import com.server.virtucart.service.CartService;
import com.server.virtucart.service.ProductService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductService productService;

	@Override
	public Cart createCart(User user) {

		Cart cart = new Cart();
		cart.setUser(user);
		Cart createdCart = cartRepository.save(cart);
		return createdCart;
	}

	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;
		for (CartItem cartsItem : cart.getCartItems()) {
			totalPrice += cartsItem.getPrice();
			totalDiscountedPrice += cartsItem.getDiscountedPrice();
			totalItem += cartsItem.getQuantity();
		}

		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(cart.getCartItems().size());
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);
		cart.setTotalItem(totalItem);

		return cartRepository.save(cart);

	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());

		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);

			int price = req.getQuantity() * product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());

			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}

		return "Item Add To Cart";
	}

}
