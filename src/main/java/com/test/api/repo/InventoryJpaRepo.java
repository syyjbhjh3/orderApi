package com.test.api.repo;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.api.entity.Inventory;

@Repository
@DynamicUpdate
public interface InventoryJpaRepo extends JpaRepository<Inventory, String> {
}
