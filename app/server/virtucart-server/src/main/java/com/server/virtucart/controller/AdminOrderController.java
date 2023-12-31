package com.server.virtucart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.virtucart.exception.OrderException;
import com.server.virtucart.model.Order;
import com.server.virtucart.response.ProductResponse;
import com.server.virtucart.service.OrderService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrdersHandler() {

		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
	}

	@PutMapping("/orders/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/orders/{orderId}/shipped")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/orders/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/orders/{orderId}/cancel")
	public ResponseEntity<Order> canceledOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.cancledOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/orders/{orderId}/delete")
	public ResponseEntity<ProductResponse> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		orderService.deleteOrder(orderId);
		ProductResponse res = new ProductResponse("Order Deleted Successfully");
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}

}
