package com.impetus.ogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impetus.ogos.model.Inventory;
import com.impetus.ogos.model.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer>
{
	List<InventoryItem> findByInventoryAndProductId(Inventory inventory, int productId);
	
	boolean existsInventoryItemByProductId(int productId);
	
	List<InventoryItem> findByProductId(int productId);
}
