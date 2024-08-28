package com.itassu.ecomproject.dto;

import com.itassu.ecomproject.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	
	private Long id;
	
	private String email;
	
	private String name;
	
	private UserRole userRole;
}
