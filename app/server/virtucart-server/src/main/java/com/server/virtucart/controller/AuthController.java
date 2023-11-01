package com.server.virtucart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.User;
import com.server.virtucart.request.LoginRequest;
import com.server.virtucart.response.AuthResponse;
import com.server.virtucart.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {

		AuthResponse authResponse = authService.createUser(user);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

		AuthResponse authResponse = authService.authenticateUser(loginRequest);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

}
