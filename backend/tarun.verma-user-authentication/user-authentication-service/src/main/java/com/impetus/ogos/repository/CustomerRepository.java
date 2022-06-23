package com.impetus.ogos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.impetus.ogos.models.Customer;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	@Query(value="SELECT * FROM customer c WHERE c.user_id=:userId",nativeQuery=true)
	Optional<Customer> findByUserId(@Param("userId") String userId);
}
