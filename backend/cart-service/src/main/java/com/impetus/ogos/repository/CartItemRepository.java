package com.impetus.ogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impetus.ogos.model.Cart;
import com.impetus.ogos.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>
{
	List<CartItem> findByCartAndProductId(Cart cart, int productId);
}
