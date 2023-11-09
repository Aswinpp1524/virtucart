package com.server.virtucart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.virtucart.response.ProductResponse;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<ProductResponse> homeController() {

		ProductResponse res = new ProductResponse("Welcome To E-Commerce System");

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
