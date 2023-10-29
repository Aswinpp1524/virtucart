package com.server.virtucart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.virtucart.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
