package com.itassu.ecomproject.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itassu.ecomproject.dto.AuthenticationRequest;
import com.itassu.ecomproject.dto.SignupRequest;
import com.itassu.ecomproject.dto.UserDto;
import com.itassu.ecomproject.entity.User;
import com.itassu.ecomproject.repository.UserRepository;
import com.itassu.ecomproject.services.auth.AuthService;
import com.itassu.ecomproject.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsService userDetailsService;
	
	private final UserRepository userRepository;
	
	private final JwtUtil jwtUtil;
	
	private final AuthService authService;
	
	public static final String HEADER_STRING = "Bearer ";
	public static final String TOKEN_PREFIX = "Authorization";
	
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authentiationRequest, 
			HttpServletResponse response) throws IOException, JSONException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentiationRequest.getUsername(),
					authentiationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect Username or Password");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authentiationRequest.getUsername());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		
		if(optionalUser.isPresent()) {
			
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("userId", optionalUser.get().getId());
			jsonResponse.put("role", optionalUser.get().getRole());
			jsonResponse.put("token", jwt);
			
			response.setContentType("application/json");
			response.getWriter().write(jsonResponse.toString());
			
			response.addHeader("Access-Control-Expose-Headers", "Authorization");
			response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, origin, " +
					"X-Requested-With, Content-Type, Accept, X-Custom-header");
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}
		
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		if(authService.hasUserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("User Already Exist", HttpStatus.NOT_ACCEPTABLE);
		}
		
	UserDto userDto = authService.createUser(signupRequest);
	return new ResponseEntity<>(userDto, HttpStatus.OK);
		
		
		
	}

}