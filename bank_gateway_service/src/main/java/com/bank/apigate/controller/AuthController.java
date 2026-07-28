package com.bank.apigate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.apigate.bean.LoginRequest;
import com.bank.apigate.bean.RegisterRequest;
import com.bank.apigate.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authservice;
	
	public AuthController(AuthService authservice){
		this.authservice = authservice;
	}
	
	@GetMapping("/test")
	public String test(){
		return "Hello world !!!!";
	}
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request){
		return authservice.register(request);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest request){
		return authservice.login(request);
	}
}
