package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Admin;
import com.example.demo.entity.City;
import com.example.demo.entity.Consumer;
import com.example.demo.entity.ConsumerType;
import com.example.demo.entity.Helper;
import com.example.demo.services.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/admin/add-admin")
	public void addAdmin() {
		Admin admin1 = new Admin("admin1@gmail.com", "admin@1", "Admin 1");
		Admin admin2 = new Admin("admin2@gmail.com", "admin@2", "Admin 2");
		Admin admin3 = new Admin("admin3@gmail.com", "admin@3", "Admin 3");
		
		adminService.addAdmin(admin1);
		adminService.addAdmin(admin2);
		adminService.addAdmin(admin3);
	}
	
	@PostMapping("/admin/register-consumer")
	public void addConsumer(@RequestBody Consumer consumer) {
	}
	
	@PostMapping("/admin/add-city")
	public void addCity(@RequestBody City city) {
		adminService.addCity(city);
	}
	
	@PostMapping("/admin/add-area")
	public ResponseEntity<String> addArea(@RequestParam("area_id") Integer area_id, @RequestParam("area_name") String area_name, @RequestParam("city_id") Integer city_id) {
		return adminService.addArea(area_id, area_name, city_id);
	}
	
	@GetMapping("/admin/city")
	public ResponseEntity<HashMap<Integer, String>> viewCity() {
		return adminService.viewAllCities();
	}
	
	@GetMapping("/admin/area")
	public ResponseEntity<HashMap<Integer, String>> viewArea() {
		return adminService.viewAllAreas();
	}
	
	@GetMapping("/admin/area/{city_name}")
	public ResponseEntity<HashMap<Integer, String>> viewAreaByCityName(@PathVariable String city_name) {
		return adminService.viewAreaByCityName(city_name);
	}
	
	@PutMapping("/admin/login") 
	public ResponseEntity<String> adminLogin(@RequestParam String email, @RequestParam String password){
		return adminService.loginAdmin(email, password);
	}
	
	@PutMapping("/admin/modify-city")
	public ResponseEntity<String> modifyCity(@RequestParam String cityName, @RequestParam String newCityName) {
		return adminService.modifyCity(cityName, newCityName);
	}
	
	@PutMapping("/admin/modify-area")
	public ResponseEntity<String> modifyAreaName(@RequestParam String areaName, @RequestParam String newAreaName) {
		return adminService.modifyAreaName(areaName, newAreaName);
	}
	
	@PutMapping("/admin/modify-area-city")
	public ResponseEntity<String> modifyAreaByCityName(@RequestParam String areaName, @RequestParam String newCityName) {
		return adminService.modifyAreaByCityName(areaName, newCityName);
	}
	
	@PutMapping("/admin/modify-consumer-type-rate")
	public ResponseEntity<String> modifyConsumerTypeRate(@RequestParam int id, @RequestParam double rate){
		return adminService.modifyConsumerTypeRate(id, rate);
	}
	
	@PostMapping("/admin/add-helper")
	public ResponseEntity<String> addHelper(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
		return adminService.addHelper(email, password, name);
	}
	
	@GetMapping("/admin/view-helpers")
	public List<Helper> viewAllHelpers(){
		return adminService.viewAllHelpers();
	}
	
	@GetMapping("/admin/consumer-type")
	public List<ConsumerType> viewAllConsumerTypes(){
		return adminService.viewAllConsumerTypes();
	}
	
	@GetMapping("/admin/consumers")
	public List<Consumer> viewAllConsumers(){
		return adminService.viewAllConsumers();
	}
	
	@PostMapping("/admin/remove-consumer")
	public ResponseEntity<String> removeConsumer(@RequestParam String email){
		return adminService.removeConsumer(email);
	}
	
}














