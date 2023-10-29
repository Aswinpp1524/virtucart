package com.server.virtucart.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.server.virtucart.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
