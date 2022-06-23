package com.impetus.ogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impetus.ogos.model.Cart;

public interface CartRepository extends JpaRepository<Cart,String>
{
	
}
