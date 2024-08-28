package com.itassu.ecomproject.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itassu.ecomproject.dto.SignupRequest;
import com.itassu.ecomproject.dto.UserDto;
import com.itassu.ecomproject.entity.User;
import com.itassu.ecomproject.enums.UserRole;
import com.itassu.ecomproject.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	public UserDto createUser (SignupRequest signupRequest) {
		
		User user = new User();	
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		User createdUser = userRepository.save(user);
		
		
		UserDto userDto = new UserDto();
		userDto.setId(createdUser.getId());
		userDto.setEmail(createdUser.getEmail());
		userDto.setName(createdUser.getName());
		userDto.setUserRole(createdUser.getRole());
		
		return userDto;
		
	}
	
	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(null == adminAccount) {
			User user = new User();
			user.setEmail("admin@test.com");
			user.setName("admin");
			user.setRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
	
	
}
