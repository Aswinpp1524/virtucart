package com.server.virtucart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.virtucart.exception.CartItemException;
import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.CartItem;
import com.server.virtucart.model.User;
import com.server.virtucart.response.ProductResponse;
import com.server.virtucart.service.CartItemService;
import com.server.virtucart.service.UserService;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private UserService userService;

	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ProductResponse> deleteCartItemHandler(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws CartItemException, UserException {

		User user = userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);

		ProductResponse res = new ProductResponse("Item Removed From Cart");

		return new ResponseEntity<ProductResponse>(res, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem,
			@RequestHeader("Authorization") String jwt) throws CartItemException, UserException {

		User user = userService.findUserProfileByJwt(jwt);

		CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

		return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
	}
}
