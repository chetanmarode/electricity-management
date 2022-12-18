package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Consumer;
import com.example.demo.services.AdminService;
import com.example.demo.services.ConsumerService;

@RestController
@Validated
public class ConsumerController {
	
	@Autowired
	ConsumerService consumerService;
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/login")
	public ResponseEntity<String> consumerLogin(@RequestParam String email, @RequestParam String password) {
		return consumerService.consumerLogin(new Consumer(email, password));
	}

	@PostMapping("/register")
	public void register(@RequestParam("email") String email, @RequestParam String name, @RequestParam Integer area_id, 
			@RequestParam Integer consumer_type_id, @RequestParam String password) {
		consumerService.registration(email, name, area_id, consumer_type_id, password);
	}
	
	@GetMapping("/view-bills")
	public List<Map<String,String>> viewAllBills(@RequestParam String email){
		return adminService.viewAllBillsByConsumerId(email);
	}
	
	@PutMapping("/update-name")
	public ResponseEntity<String> updateName(@RequestParam String email, @RequestParam String name){
		return consumerService.updateName(email, name);
	}
	
	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestParam String password){
		return consumerService.updatePassword(email, password);
	}
}