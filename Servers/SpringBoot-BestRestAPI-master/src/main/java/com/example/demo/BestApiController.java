package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BestApiController {
	
	@GetMapping
	public String bestApi() {
		return "This is the best api you've ever seen";
	}
	
	

}
