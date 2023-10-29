package com.server.virtucart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.server.virtucart.config.JwtTokenProvider;
import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.User;
import com.server.virtucart.repository.UserRepository;
import com.server.virtucart.request.LoginRequest;
import com.server.virtucart.response.AuthResponse;
import com.server.virtucart.service.AuthService;
import com.server.virtucart.service.CartService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomUserDetails customUserDetails;

	@Override
	public AuthResponse createUser(User user) throws UserException {

		User isEmailExist = userRepository.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new UserException("Email is already used with another account");
		}

		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFirstName(user.getFirstName());
		createdUser.setLastName(user.getLastName());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepository.save(createdUser);

		cartService.createCart(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, true);
		return authResponse;

	}

	@Override
	public AuthResponse authenticateUser(LoginRequest loginRequest) throws BadCredentialsException {

		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();

		authResponse.setStatus(true);
		authResponse.setJwt(token);

		return authResponse;
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetails.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username!");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password!");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
