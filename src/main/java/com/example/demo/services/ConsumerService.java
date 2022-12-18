package com.example.demo.services;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.*;
import com.example.demo.repositories.*;

@Service
public class ConsumerService {
	
	@Autowired
	ConsumerRepository consumerRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired
	ConsumerTypeRepository consumerTypeRepository;
	
	@Autowired
	BillRepository billRepository;
	
	//function for registration of consumer
	public void registration(String email, String name, Integer area_id, Integer consumer_type_id, String password) {
		//will take area_id and consumer_type_id from dropdown, so no need of exception handling
		Area area = areaRepository.getById(area_id);
		ConsumerType ct = consumerTypeRepository.getById(consumer_type_id);
		
		Consumer consumer = new Consumer(email, name, area, ct, password, null);
		
		consumerRepository.save(consumer);
	}
	
	//function for consumer Login
	public ResponseEntity<String> consumerLogin(Consumer consumer) {
		if(consumerRepository.findById(consumer.getEmail()).isPresent()) {
			if(consumerRepository.findById(consumer.getEmail()).get().getPassword().equals(Base64.getEncoder().encodeToString(consumer.getPassword().getBytes()))) {
				System.out.println("Successful Login");
				return new ResponseEntity<String>("Successful Login", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>("Wrong Password", HttpStatus.UNAUTHORIZED);
			}
		}else {
			return new ResponseEntity<String>("Invalid Email/ Password", HttpStatus.UNAUTHORIZED);
		}
	}
	
	//function if user wants to remove their account
	public ResponseEntity<String> removeAccount(String email){
		if(consumerRepository.findById(email).isPresent()) {
			if((billRepository.findByEmail(new Consumer(email))).size() > 0){
				billRepository.deleteAllByEmail(new Consumer(email));
				consumerRepository.deleteById(email);
				return new ResponseEntity<String>("Your Bills & Account Deleted successfully.", HttpStatus.ACCEPTED);
			}else {
				consumerRepository.deleteById(email);
				return new ResponseEntity<String>("Your Account was deleted successfully, we are sorry to see you go.", HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<String>("You are not logged in. Log In First", HttpStatus.BAD_REQUEST);
	}
	
	//To update consumer name, take email from session
	public ResponseEntity<String> updateName(String email, String name){
		for (Consumer c : consumerRepository.findAll()) {
			if(c.getEmail().equals(email)) {
				c.setName(name);
				consumerRepository.save(c);
				return new ResponseEntity<String>("Consumer Name was changed." , HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<String>("You are not logged in. Please Log In.", HttpStatus.NOT_FOUND);
	}
	
	//To update consumer password, take email from session
	public ResponseEntity<String> updatePassword(String email, String password){
		for (Consumer c : consumerRepository.findAll()) {
			if(c.getEmail().equals(email)) {
				c.setPassword(password);
				consumerRepository.save(c);
				return new ResponseEntity<String>("Consumer Password was changed." , HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<String>("You are not logged in. Please Log In.", HttpStatus.NOT_FOUND);
	}
}
