package com.server.virtucart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.virtucart.exception.ProductException;
import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.Cart;
import com.server.virtucart.model.User;
import com.server.virtucart.request.AddItemRequest;
import com.server.virtucart.response.ProductResponse;
import com.server.virtucart.service.CartService;
import com.server.virtucart.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {

		User user = userService.findUserProfileByJwt(jwt);

		Cart cart = cartService.findUserCart(user.getId());

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/cart/add")
	public ResponseEntity<ProductResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {

		User user = userService.findUserProfileByJwt(jwt);

		cartService.addCartItem(user.getId(), req);

		ProductResponse res = new ProductResponse("Item Added To Cart Successfully");

		return new ResponseEntity<ProductResponse>(res, HttpStatus.ACCEPTED);

	}

}
