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
	
	//function to add helper just for database creation, will be removed from helperService later
	public void addHelper(Helper helper) {
		helperRepository.save(helper);
	}

	//helper Login with email and password validation encoding matching using Base64
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
	
	//Add Bill for each consumer from Helper Session
	public ResponseEntity<String> addBill(String email, Integer units){
		
		//getting consumer_type rate from consumer
		Double rate = consumerRepository.findById(email).get().getConsumer_type().getRate();
		Double totalAmount = rate * units;
		
		Date date = new Date();
		int month = date.getMonth();
		int year = date.getYear();
		
		Bill bills = null;
		for(Bill b: billRepository.findAll()) {
			if(b.getConsumer().getEmail().equalsIgnoreCase(email)) {
				bills = b;
				break;
			}
		}
		//If there are no bills for given consumer by email, add new bill to the database
		if(bills == null) {
			Bill bill = new Bill(new Consumer(email), new Date(), units, totalAmount);
			billRepository.save(bill);
		}
		//If any bill is already present in the bills table
		else {
			//check whether bill details are added for specific month and year, return that
			//bill details already added for that month
			if(bills.getBillDate().getMonth() == month && bills.getBillDate().getYear() == year)
				return new ResponseEntity<String>("Bill already calculated for the particular month of the year.", HttpStatus.BAD_REQUEST);
			else {
				//or add bill details for different month
				Bill bill = new Bill(new Consumer(email), new Date(), units, totalAmount);
				billRepository.save(bill);
			}
		}
		
		return new ResponseEntity<String>("Bill added successfully", HttpStatus.ACCEPTED);
		
	}
}