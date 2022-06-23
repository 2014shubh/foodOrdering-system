package com.impetus.ogos.services;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.impetus.ogos.dto.*;
import com.impetus.ogos.exception.CustomException;
import com.impetus.ogos.exception.CustomIOException;
import com.impetus.ogos.models.Address;
import com.impetus.ogos.models.Customer;
import com.impetus.ogos.models.ERole;
import com.impetus.ogos.models.Role;
import com.impetus.ogos.models.User;
import com.impetus.ogos.payload.responses.CustomerJwtResponse;
import com.impetus.ogos.payload.responses.JwtResponse;
import com.impetus.ogos.payload.responses.MessageResponse;
import com.impetus.ogos.repository.CustomerRepository;
import com.impetus.ogos.repository.RoleRepository;
import com.impetus.ogos.repository.UserRepository;
import com.impetus.ogos.repository.AddressRepository;
import com.impetus.ogos.security.jwt.JwtUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.client.RestTemplate;

/**
 * @author raghvendra.rathore
 *
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private JavaMailSender mailSender;

	private static final String KEY = "70j4Wb2abZjTJNoLAfRjAAOz9utLjYk8";
	private static final String API_KEY = "AsVO5LxwWA1fIvptwaVuWWcFjlZvFv94F40SHr8clyCIS_GNMhndyX38APEGZ4em";
	private static final String sourceLatLng = "22.720385,75.86821";

	static Logger logger = LoggerFactory.getLogger(UserService.class);

	int flag = 0;
	int negativePoints = 0;

	/**
	 * @param signUpDto
	 * @throws CustomException
	 * 
	 *                         This method is used for storing user's details in the
	 *                         database It checks if a email already exists then it
	 *                         will return a bad request to the user It checks roles
	 *                         of the user and default role is user If the role is
	 *                         user then it will initialize a cart in cart
	 *                         microservice If all checks are passed then it will a
	 *                         success response
	 * 
	 */
	public ResponseEntity<MessageResponse> signUp(SignupDto signUpDto) throws CustomException {
		logger.info("Inside signUp method of UserService");
		Boolean exist = userRepository.existsByEmail(signUpDto.getEmail());
		if (Boolean.TRUE.equals(exist)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpDto.getFirstname(), signUpDto.getLastname(), signUpDto.getEmail(),
				encoder.encode(signUpDto.getPassword()), signUpDto.getMobile());

		Set<String> strRoles = signUpDto.getRole();

		Set<Role> roles = new HashSet<>();
		Customer customer = new Customer();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			flag = 1;
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				case "delivery":
					Role deliveryRole = roleRepository.findByName(ERole.ROLE_DELIVERY)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(deliveryRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
					flag = 1;
				}
			});
		}

		try {
			user.setRoles(roles);
			userRepository.save(user);
			if (flag == 1) {
				customer.setNegativePoints(negativePoints);
				customer.setUser(user);
				customerRepository.save(customer);
				RequestEntity<?> request = RequestEntity
						.post(new URI("http://localhost:8087/cart/add-cart/" + user.getUserId())).build();
				restTemplate.exchange(request, void.class);
			}
		} catch (Exception e) {
			// handle signup error
			throw new CustomException(e.getMessage());
		}

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	/**
	 * @param loginDto
	 * @throws CustomException
	 * 
	 *                         This method is used for user's login authentication
	 *                         It checks if provided details are correct If correct
	 *                         then it will generate JWT token At last it returns
	 *                         logged in user's details with JWT token
	 */
	public ResponseEntity<?> signIn(LoginDto loginDto) throws CustomException {
		logger.info("Inside signIn method of UserService");
		try {
			Authentication authentication = authenticationManager.authenticate(

					new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(loginDto.getEmail());

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			if (roles.contains("ROLE_USER")) {
				User user = new User();
				user.setUserId(userDetails.getUserId());
				Customer customer = customerRepository.findByUserId(userDetails.getUserId()).orElse(null);
				int cid = customer.getCid();
				return ResponseEntity.ok(new CustomerJwtResponse(jwt, userDetails.getUserId(), userDetails.getEmail(),
						roles, cid, userDetails.getFirstname()));
			}

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getFirstname(),
					userDetails.getEmail(), roles));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong username or password!"));
		}

	}

	/**
	 * This method returns all users details
	 */
	public List<UserDto> getAllUsers() {
		logger.info("Inside getAllUsers method of UserService");
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			Role role = user.getRoles().stream().filter(n -> n.getName() == ERole.ROLE_ADMIN).findAny().orElse(null);
			if (role == null) {
				UserDto userDto = getDtoFromUser(user);
				userDtos.add(userDto);
			}
		}

		return userDtos;
	}

	/**
	 * @param user
	 * 
	 *             This method converts user object to userDto object
	 */
	public static UserDto getDtoFromUser(User user) {
		logger.info("Inside getDtoFromUser method of UserService");
		return new UserDto(user);
	}

	/**
	 * 
	 * @param userUpdateDto
	 * 
	 *                      This method is used for updating user profile
	 */
	public ResponseEntity<MessageResponse> updateProfile(String userId, UserUpdateDto userUpdateDto) {
		logger.info("Inside updateProfile method of UserService");

		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {

			user.setFirstname(userUpdateDto.getFirstname());
			user.setLastname(userUpdateDto.getLastname());
			user.setMobile(userUpdateDto.getMobile());
			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
	}

	/**
	 * @param userCreateDto
	 * 
	 *                      This method is used to create user by ADMIN It checks if
	 *                      a email already exists It only adds inventory manager
	 *                      and delivery partner If not it returns success response
	 */
	public ResponseEntity<MessageResponse> createUser(UserCreateDto userCreateDto) {
		logger.info("Inside createUser method of UserService");
		Boolean exist = userRepository.existsByEmail(userCreateDto.getEmail());
		if (Boolean.TRUE.equals(exist)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(userCreateDto.getFirstname(), userCreateDto.getLastname(), userCreateDto.getEmail(),
				encoder.encode(userCreateDto.getPassword()), userCreateDto.getMobile());

		Set<String> strRoles = userCreateDto.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Please provide a role!"));
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				case "delivery":
					Role deliveryRole = roleRepository.findByName(ERole.ROLE_DELIVERY)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(deliveryRole);

					break;
				}
			});
		}
		try {
			user.setRoles(roles);
			userRepository.save(user);
		} catch (Exception e) {
			// handle create user error
			throw new CustomException(e.getMessage());
		}

		return ResponseEntity.ok(new MessageResponse("User Added successfully!"));

	}

	/**
	 * @param addressDto
	 * @param cid
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<MessageResponse> addNewAddress(AddressDto addressDto, int cid) throws IOException {
		if (UserService.checkDistance(addressDto)) {
			Address address = new Address();
			address.setHouseNum(addressDto.getHouseNum());
			address.setStreet(addressDto.getStreet());
			address.setArea(addressDto.getArea());
			address.setCity(addressDto.getCity());
			address.setPincode(addressDto.getPincode());
			Customer customer = new Customer();
			customer.setCid(cid);
			address.setCustomer(customer);
			addressRepository.save(address);

			return ResponseEntity.ok(new MessageResponse("Address Added Successfully"));
		} else {

			return ResponseEntity.badRequest().body(new MessageResponse("Address Should not be more than 30km!"));
		}
	}

	/**
	 * @param addressId
	 * @param addressUpdateDto
	 * 
	 *                         This method is used to update existing address of a
	 *                         user
	 * @throws IOException
	 */
	public ResponseEntity<MessageResponse> updateAddress(int addressId, AddressUpdateDto addressUpdateDto)
			throws IOException {
		logger.info("Inside updateAddress method of UserService");
		AddressDto addressDto = new AddressDto(addressUpdateDto);
		if (UserService.checkDistance(addressDto)) {
			Address address = addressRepository.getByAddressId(addressId);
			address.setHouseNum(addressUpdateDto.getHouseNum());
			address.setStreet(addressUpdateDto.getStreet());
			address.setArea(addressUpdateDto.getArea());
			address.setCity(addressUpdateDto.getCity());
			address.setPincode(addressUpdateDto.getPincode());

			addressRepository.save(address);
			return ResponseEntity.ok(new MessageResponse("Address Updated Successfully"));
		} else {

			return ResponseEntity.badRequest().body(new MessageResponse("Address Should not be more than 30km!"));
		}

	}

	/**
	 * @param cid
	 * @return List<GetAddressDto>
	 * 
	 * 
	 *         This method is used to return to all addresses of a user
	 */
	public List<GetAddressDto> getUserAddress(int cid) {
		logger.info("Inside getUserAddress method of UserService");
		List<Address> addresses = addressRepository.findAllByCid(cid);
		List<GetAddressDto> getAddressDtos = new ArrayList<>();
		for (Address address : addresses) {
			GetAddressDto getAddressDto = getDtoFromAddress(address);
			getAddressDtos.add(getAddressDto);
		}

		return getAddressDtos;
	}

	/**
	 * @param address
	 * @return GetAddressDto
	 * 
	 *         This method converts Address object into GetAddressDto object
	 */
	public static GetAddressDto getDtoFromAddress(Address address) {
		logger.info("Inside getDtoFromUser method of UserService");
		return new GetAddressDto(address);

	}

	/**
	 * @param addressId
	 * @return
	 * 
	 *         This method is used to delete a address
	 */
	public ResponseEntity<MessageResponse> deleteAddress(int addressId) {
		logger.info("Inside deleteAddress method of UserService");
		Address address = addressRepository.findById(addressId).orElse(null);
		address.setActive(false);
		addressRepository.save(address);
		return ResponseEntity.ok(new MessageResponse("Address Deleted Successfully"));
	}

	/**
	 * @param cid
	 * @return
	 * 
	 *         This method is used to get negative points of a user
	 */
	public CustomerDto getNegativePoints(int cid) {
		logger.info("Inside getNegativePoints method of UserService");
		Customer customer = customerRepository.findById(cid).orElseThrow(NullPointerException::new);
		CustomerDto customerDto = new CustomerDto();
		customerDto.setNegativePoints(customer.getNegativePoints());
		return customerDto;

	}

	/**
	 * @param addressDto
	 * @return
	 * @throws IOException
	 * 
	 *                     This method is used to check if a address is in 30 KM of
	 *                     store's location First it will convert the address into
	 *                     latitude and longitude using MapQuestApi Then it will
	 *                     find the distance between the store's location and the
	 *                     given address If the distance is more than 30km then it
	 *                     will return bad request If all checks are passed it will
	 *                     return success response
	 */
	public static boolean checkDistance(AddressDto addressDto) throws IOException {
		logger.info("Inside getDistance method of UserService");

		String street = addressDto.getHouseNum() + "+" + addressDto.getStreet().replace(" ", "+") + "+"
				+ addressDto.getArea().replace(" ", "+");
		String city = addressDto.getCity();
		String pincode = addressDto.getPincode();

		String latLngUrl = "http://www.mapquestapi.com/geocoding/v1/address?key=" + KEY + "&street=" + street + "&city="
				+ city + "&postalCode=" + pincode;

		String address = latLngUrl;

		String content = null;
		String destinationLatLng = "";

		try {
			URL url = new URL(address);

			InputStream stream = url.openStream();

			try {
				int available = stream.available();

				byte[] bytes = new byte[available];
				if (stream.read(bytes) == -1) {
					throw new EOFException();
				}
				content = new String(bytes);
			} finally {
				stream.close();
			}

			destinationLatLng = content;
		} catch (IOException e) {
			throw new CustomIOException(e);
		}
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(destinationLatLng);
			JSONObject jsonobj = (JSONObject) obj;

			JSONArray dist = (JSONArray) jsonobj.get("results");
			JSONObject obj1 = (JSONObject) dist.get(0);
			JSONArray obj2 = (JSONArray) obj1.get("locations");
			JSONObject obj3 = (JSONObject) obj2.get(0);
			JSONObject obj4 = (JSONObject) obj3.get("latLng");
			destinationLatLng = obj4.get("lat") + "," + obj4.get("lng");
		} catch (Exception e) {
			logger.warn("Parsing data failed");
			e.printStackTrace();
		}
		String url = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?origins=" + sourceLatLng
				+ "&destinations=" + destinationLatLng + "&travelMode=driving&key=" + API_KEY;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();

		String distanceJson = response.body().string();
		String finalDistance = "";
		try {

			Object obj = parser.parse(distanceJson);
			JSONObject jsonobj = (JSONObject) obj;

			JSONArray dist = (JSONArray) jsonobj.get("resourceSets");
			JSONObject obj2 = (JSONObject) dist.get(0);
			JSONArray disting = (JSONArray) obj2.get("resources");
			JSONObject obj3 = (JSONObject) disting.get(0);
			JSONArray obj4 = (JSONArray) obj3.get("results");
			JSONObject obj5 = (JSONObject) obj4.get(0);

			finalDistance = obj5.get("travelDistance").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		double eligible = 30;
		boolean isEligible = true;
		double distance = Double.parseDouble(finalDistance);
		if (eligible < distance) {
			isEligible = false;
		}
		return isEligible;
	}

	/**
	 * @param userId
	 * @return UserProfileDto
	 * 
	 *         This method returns user details for showing data on update user page
	 */
	public UserProfileDto getUserProfile(String userId) {
		Optional<User> value = userRepository.findById(userId);

		if (value.isEmpty()) {
			throw new NullPointerException("User not found");
		}
		User user = value.get();

		return getUserProfileDtoFromUser(user);

	}

	/**
	 * @param user
	 * @return UserProfileDto
	 * 
	 *         This method converts User to UserProfileDto
	 */
	public static UserProfileDto getUserProfileDtoFromUser(User user) {

		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setFirstname(user.getFirstname());
		userProfileDto.setLastname(user.getLastname());
		userProfileDto.setMobile(user.getMobile());
		userProfileDto.setEmail(user.getEmail());
		return userProfileDto;
	}

	/**
	 * @param recipientEmail
	 * @param token
	 * @return
	 */
	public ResponseEntity<EmailTokenDto> sendEmail(String recipientEmail, String token) {
		String subject = "Here's the link to confirm your email";
		SimpleMailMessage simplemail = new SimpleMailMessage();
		simplemail.setFrom("ogosmanagement@gmail.com");
		simplemail.setTo(recipientEmail);
		simplemail.setText("Token : " + token);
		simplemail.setSubject(subject);
		mailSender.send(simplemail);
		return ResponseEntity
				.ok(new EmailTokenDto(token, "A token has been sent to your registered email check your spam also"));
	}

	/**
	 * @param addressId
	 * @return
	 */
	public GetAddressDto getAddress(int addressId) {
		logger.info("Inside getUserAddress method of UserService");
		Optional<Address> value = addressRepository.findById(addressId);
		if (value.isEmpty()) {
			throw new NullPointerException("Address not found");
		}
		Address address = value.get();

		return getDtoFromAddress(address);
	}

	/**
	 * @param userId
	 * @return
	 */
	public CustomerDto getNegativePoints(String userId) {
		logger.info("Inside getNegativePoints method of UserService");
		Optional<Customer> value = customerRepository.findByUserId(userId);
		if (value.isEmpty()) {
			throw new NullPointerException("Customer not found");
		}
		Customer customer = value.get();
		CustomerDto customerDto = new CustomerDto();
		customerDto.setNegativePoints(customer.getNegativePoints());

		return customerDto;
	}

}
