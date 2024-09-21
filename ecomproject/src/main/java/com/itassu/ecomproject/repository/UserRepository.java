package com.itassu.ecomproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itassu.ecomproject.entity.User;
import com.itassu.ecomproject.enums.UserRole;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findFirstByEmail(String email);

	User findByRole(UserRole userRole);
	
}
