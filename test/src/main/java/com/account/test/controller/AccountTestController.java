package com.account.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountTestController {
	@GetMapping("/accounttest")
	public String test(){
		return "Hello world !!!!";
	}
	

}
