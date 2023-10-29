package com.server.virtucart.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.virtucart.config.JwtTokenProvider;
import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.User;
import com.server.virtucart.repository.UserRepository;
import com.server.virtucart.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public User findUserById(Long userId) throws UserException {

		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id: " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {

		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("User not exist with email: " + email);
		}
		return user;
	}

}
