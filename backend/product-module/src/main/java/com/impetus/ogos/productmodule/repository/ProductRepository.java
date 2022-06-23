package com.impetus.ogos.productmodule.repository;

import com.impetus.ogos.productmodule.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByName(String query);
}
