package com.server.virtucart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.virtucart.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
