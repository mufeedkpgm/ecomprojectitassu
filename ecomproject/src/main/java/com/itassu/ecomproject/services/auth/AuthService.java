package com.itassu.ecomproject.services.auth;

import com.itassu.ecomproject.dto.SignupRequest;
import com.itassu.ecomproject.dto.UserDto;

public interface AuthService{
	
	UserDto createUser (SignupRequest signupRequest);
	
	Boolean hasUserWithEmail(String email);

}
