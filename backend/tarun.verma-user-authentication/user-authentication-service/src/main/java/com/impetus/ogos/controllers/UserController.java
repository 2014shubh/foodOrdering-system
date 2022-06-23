package com.impetus.ogos.controllers;

import com.impetus.ogos.models.User;
import com.impetus.ogos.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import com.impetus.ogos.dto.AddressDto;
import com.impetus.ogos.dto.AddressUpdateDto;
import com.impetus.ogos.dto.CustomerDto;
import com.impetus.ogos.dto.ForgotPasswordDto;
import com.impetus.ogos.dto.GetAddressDto;
import com.impetus.ogos.dto.UserProfileDto;
import com.impetus.ogos.dto.UserUpdateDto;
import com.impetus.ogos.payload.responses.MessageResponse;
import com.impetus.ogos.services.UserService;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/auth/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
			UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(UserController.class);

	/**

	 * @param userUpdateDto
	 * @return
	 */
	@PutMapping("/update/{userId}")
	public ResponseEntity<MessageResponse> updateProfile(@PathVariable String userId,
			@RequestBody UserUpdateDto userUpdateDto) {
		logger.info("Inside updateProfile of UserController");
		return userService.updateProfile(userId, userUpdateDto);
	}

	/**
	 * @param addressId
	 * @param addressUpdateDto
	 * @return
	 * @throws IOException 
	 */
	@PutMapping("/updateAddress/{addressId}")
	public ResponseEntity<MessageResponse> updateAddress(@PathVariable("addressId") int addressId,
			@RequestBody AddressUpdateDto addressUpdateDto) throws IOException {
		logger.info("Inside updateAddress of UserController");
		return userService.updateAddress(addressId, addressUpdateDto);
	}

	/**
	 * @param cid
	 * @return
	 */
	@GetMapping("/getAddress/{cid}")
	public ResponseEntity<List<GetAddressDto>> getUserAddress(@PathVariable("cid") int cid) {
		logger.info("Inside getUserAddress of UserController");
		List<GetAddressDto> getAddressDto = userService.getUserAddress(cid);
		return new ResponseEntity<List<GetAddressDto>>(getAddressDto, HttpStatus.OK);
	}

	/**
	 * @param addressDto
	 * @param cid
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/addAddress/{cid}")
	public ResponseEntity<MessageResponse> addAddress(@RequestBody AddressDto addressDto, @PathVariable("cid") int cid) throws IOException {
		logger.info("Inside addAddress of UserController");
		return userService.addNewAddress(addressDto, cid);
	}

	/**
	 * @param addressId
	 * @return
	 */
	@PutMapping("/deleteAddress/{addressId}")
	public ResponseEntity<MessageResponse> deleteAddress(@PathVariable int addressId) {
		logger.info("Inside deleteAddress of UserController");
		return userService.deleteAddress(addressId);
	}

	@GetMapping("/getUser/{userId}")
	public UserProfileDto getUserForProfile(@PathVariable String userId) {
		logger.info("Inside getUserForProfile of UserController");
		return userService.getUserProfile(userId);
	}
	
	@PostMapping("/verifyEmail")
	public ResponseEntity<?> emailSender(@Valid @RequestBody ForgotPasswordDto user) {
		String token = RandomString.make(30);
		String email=user.getEmail();
		try {
		return userService.sendEmail(email, token);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Please provide a valid email"));
		}
	}
	@GetMapping("/addressDetails/{addressId}")
	public ResponseEntity<GetAddressDto> getAddress(@PathVariable("addressId") int addressId) {
		logger.info("Inside getUserAddress of UserController");
		GetAddressDto getAddressDto = userService.getAddress(addressId);
		return new ResponseEntity<GetAddressDto>(getAddressDto, HttpStatus.OK);
	}
	@GetMapping("/getPoints/{userId}")
	public ResponseEntity<CustomerDto> getNegativePoints(@PathVariable("userId") String userId) {
		logger.info("Inside getNegativePoints of UserController");
		CustomerDto customerDto = userService.getNegativePoints(userId);
		return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.OK);
	}


}
