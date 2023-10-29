package com.server.virtucart.service;

import org.springframework.security.authentication.BadCredentialsException;

import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.User;
import com.server.virtucart.request.LoginRequest;
import com.server.virtucart.response.AuthResponse;

public interface AuthService {

	public AuthResponse createUser(User user) throws UserException;

	public AuthResponse authenticateUser(LoginRequest loginRequest) throws BadCredentialsException;
}
