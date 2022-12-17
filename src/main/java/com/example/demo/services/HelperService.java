package com.example.demo.services;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Consumer;
import com.example.demo.entity.Helper;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.ConsumerRepository;
import com.example.demo.repositories.HelperRepository;

@Service
public class HelperService{

	@Autowired(required = true)
	HelperRepository helperRepository;
	
	@Autowired
	ConsumerRepository consumerRepository;
	
	@Autowired
	BillRepository billRepository;
	
	public void addHelper(Helper helper) {
		helperRepository.save(helper);
	}

	public void loginHelper(Helper helper) {
		if(helperRepository.findById(helper.getEmail()) != null) {
			if(helperRepository.findById(helper.getEmail()).get().getPassword().equals(Base64.getEncoder().encodeToString(helper.getPassword().getBytes()))) {
				System.out.println("Successful Login");
			} else {
				System.out.println("Wrong Password");
			}
		} else {
			System.out.println("Invalid Credentials");
		}
	}
	
	
	public ResponseEntity<String> addBill(String email, Integer units){
		Double rate = consumerRepository.findById(email).get().getConsumer_type().getRate();
		Double totalAmount = rate * units;
		
		Bill bill = new Bill(1, new Consumer(email), new Date(), units, totalAmount);
		billRepository.save(bill);
		System.out.println("Bill added successfully");
		return new ResponseEntity<String>("Bill added successfully", HttpStatus.ACCEPTED);
		
	}
}