package com.impetus.ogos.repository;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.impetus.ogos.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

	Address getByAddressId(int addressId);
	@Query(value="SELECT * FROM address a WHERE a.customer_cid=:cid",nativeQuery=true)
	List<Address> findAllByCid(@Param("cid") int cid);
}
