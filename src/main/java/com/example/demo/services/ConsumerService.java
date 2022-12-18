package com.example.demo.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Area;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Consumer;
import com.example.demo.entity.ConsumerType;
import com.example.demo.repositories.AreaRepository;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.ConsumerRepository;
import com.example.demo.repositories.ConsumerTypeRepository;

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
	
	public void registration(String email, String name, Integer area_id, Integer consumer_type_id, String password) {
		Area area = areaRepository.getById(area_id);
		ConsumerType ct = consumerTypeRepository.getById(consumer_type_id);
		
		Consumer consumer = new Consumer(email, name, area, ct, password, null);
		
		consumerRepository.save(consumer);
	}
	
	
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
	
	//To change consumer name
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
	
	//To update consumer password
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
