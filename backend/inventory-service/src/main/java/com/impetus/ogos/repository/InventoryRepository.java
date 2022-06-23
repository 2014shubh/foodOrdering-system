package com.impetus.ogos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impetus.ogos.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, String>
{
	//Optional<Inventory> findByManagerId(String managerId);
	//Optional<Inventory> findByDeliveryPartnerId(String deliveryPartnerId);
}
