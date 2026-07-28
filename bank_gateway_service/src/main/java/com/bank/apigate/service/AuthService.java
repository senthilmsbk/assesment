package com.bank.apigate.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.apigate.bean.LoginRequest;
import com.bank.apigate.bean.RegisterRequest;
import com.bank.apigate.entity.User;
import com.bank.apigate.repository.UserRepository;
import com.bank.apigate.util.JWTUtil;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTUtil jwtUtil;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JWTUtil jwtUtil){
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtUtil=jwtUtil;
	}
	
	public String register(RegisterRequest registerRequest){
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setUserName(registerRequest.getUserName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setRole(registerRequest.getRole());
		userRepository.save(user);
		return "User Registered successfully";
	}
	
	public String login(LoginRequest request){
		User user = userRepository.findByUserName(request.getUserName());
		if(user==null) throw new RuntimeException("user not found");
		boolean valid = passwordEncoder.matches(request.getPassword(), user.getPassword());
		if(!valid){
			 throw new RuntimeException("Invalid Credential");
			
		}
		return jwtUtil.generateToken();
	}
}
