package com.impetus.ogos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.impetus.ogos.models.User;
import com.impetus.ogos.repository.RoleRepository;
import com.impetus.ogos.repository.UserRepository;
import com.impetus.ogos.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserAuthenticationServiceApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@Test
	public void getAllUsers() {
		when(userRepository.findAll()).thenReturn(Stream.of(new User("B$00002","raghavrathore073@gmail.com","Raghav","Rathore","7878787878")).collect(Collectors.toList()));
	
		assertEquals(1, userService.getAllUsers().size());
	}
	
	
	
	
	}
	

