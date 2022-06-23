package com.impetus.ogos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.impetus.ogos.models.User;



@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Boolean existsByEmail(String email);

	Optional<User> findByUserId(String userId);

	Optional<User> findByEmail(String username);
	
	@Query("select u from User u where u.email=:email")
	public User getUserByUsername(@Param("email")String email);

	@Query("select u from User u where u.resetPasswordToken=:token")
	Optional<User> findByResetPasswordToken(@Param("token") String token);
}
