package com.server.virtucart.service;

import com.server.virtucart.exception.UserException;
import com.server.virtucart.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;

}
