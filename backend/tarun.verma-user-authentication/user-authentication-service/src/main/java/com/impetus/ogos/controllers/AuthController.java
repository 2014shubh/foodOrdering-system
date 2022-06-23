package com.impetus.ogos.controllers;

import java.io.IOException;

import javax.validation.Valid;

import com.impetus.ogos.models.User;
import com.impetus.ogos.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.impetus.ogos.dto.AddressDto;
import com.impetus.ogos.dto.LoginDto;
import com.impetus.ogos.dto.SignupDto;
import com.impetus.ogos.payload.responses.MessageResponse;
import com.impetus.ogos.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	/**
	 * @param loginDto
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
		logger.info("Inside updateProfile of AuthController");
		return userService.signIn(loginDto);
	}

	/**
	 * @param signUpDto
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signUpDto) {
		logger.info("Inside registerUser of AuthController");
		return userService.signUp(signUpDto);
	}
	@GetMapping("/getmail/{userId}")
	public String getEmail(@PathVariable String userId){
		User user = userRepository.findById(userId)
				.orElse(null);
		return user.getEmail();

	}
}
