package com.test.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.api.entity.Order;

@Repository
public interface OrderJpaRepo extends JpaRepository<Order, String> {
}