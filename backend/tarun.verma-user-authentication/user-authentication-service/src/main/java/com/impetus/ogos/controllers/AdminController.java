package com.impetus.ogos.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.impetus.ogos.dto.UserCreateDto;
import com.impetus.ogos.dto.UserDto;
import com.impetus.ogos.payload.responses.MessageResponse;
import com.impetus.ogos.services.UserService;

@RestController
@RequestMapping("/auth/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	/**
	 * @return List<UserDto>
	 * 
	 * This method returns list of users
	 */
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers() {
		logger.info("Inside getUsers of AdminController");
		List<UserDto> body = userService.getAllUsers();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
	
	/**
	 * @param userCreateDto
	 * @return MessageResponse
	 * 
	 * This method is used to add user by admin
	 */
	@PostMapping("/addUser")
	public ResponseEntity<MessageResponse> createUser(@RequestBody UserCreateDto userCreateDto){
		logger.info("Inside createUser of AdminController");
		return userService.createUser(userCreateDto);
	}
	
	
}
