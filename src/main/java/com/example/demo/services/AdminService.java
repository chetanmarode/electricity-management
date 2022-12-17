package com.example.demo.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Area;
import com.example.demo.entity.City;
import com.example.demo.entity.Consumer;
import com.example.demo.entity.ConsumerType;
import com.example.demo.entity.Helper;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.AreaRepository;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.ConsumerRepository;
import com.example.demo.repositories.ConsumerTypeRepository;
import com.example.demo.repositories.HelperRepository;


@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired
	ConsumerTypeRepository consumerTypeRepository;
	
	@Autowired
	HelperRepository helperRepository;
	
	@Autowired
	ConsumerRepository consumerRepository;
	
	@Autowired
	BillRepository billRepository;
	
	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}
	
	public ResponseEntity<String> addCity(City city) {
		if(cityRepository.existsById(city.getId())) {
			// Exception 
			return new ResponseEntity(new Exception("City with the same ID already exists!!!").getMessage(), HttpStatus.BAD_REQUEST);
			
		} else {
			cityRepository.save(city);
			return new ResponseEntity<String>("City Added Successfully!!!", HttpStatus.ACCEPTED);
		}
	}
	
	public ResponseEntity<String> addArea(int area_id, String area_name, int city_id) {
		if(areaRepository.existsById(area_id)) {
			// Exception 
			System.out.println("Area with the same ID already exists!!!");
			return new ResponseEntity(new Exception("Area with the same ID already exists!!!").getMessage(), HttpStatus.BAD_REQUEST);
		}else if(!(cityRepository.findById(city_id).isPresent())) {
			// Exception 
			return new ResponseEntity(new Exception("City Not Found").getMessage(), HttpStatus.BAD_REQUEST);
		}
		else {
			City city = getCityById(city_id).get();
			Area area = new Area(area_id, area_name, city);
			areaRepository.save(area);
			System.out.println("Area Added Successfully!!!");
			return new ResponseEntity<String>("Area Added Successfully!!!", HttpStatus.ACCEPTED);
		}
	}
	
	public ResponseEntity<HashMap<Integer, String>> viewAllCities() {
		List<HashMap<Integer, String>> cities = new ArrayList<HashMap<Integer, String>>();
		for (City city : cityRepository.findAll()) {
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			map.put(city.getId(), city.getName());
			cities.add(map);
		}
		return new ResponseEntity(cities, HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<HashMap<Integer, String>> viewAllAreas() {
		List<HashMap<Integer, String>> areas = new ArrayList<HashMap<Integer, String>>();
		for (Area area : areaRepository.findAll()) {
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			map.put(area.getId(), area.getAreaName());
			areas.add(map);
		}
		return new ResponseEntity(areas, HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<HashMap<Integer, String>> viewAreaByCityName(String cityName) {
		List<HashMap<Integer, String>> areas = new ArrayList<HashMap<Integer, String>>();
		List<City> cities = cityRepository.findAll();
		City city = null;
		for (City c : cities) {
			if(c.getName().equalsIgnoreCase(cityName)) {
				city = c;
				break;
			}
		}
		
		if(city == null) {
			
		}
		
		for (Area area : areaRepository.findAll()) {
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			if(area.getCity().getId() == city.getId()) {
				map.put(area.getId(), area.getAreaName());
				areas.add(map);
			}
		}
		
		return new ResponseEntity(areas, HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<String> loginAdmin(String email, String password) {
		if(adminRepository.findById(email).isPresent()) {
			if(adminRepository.findById(email).get().getPassword().equals(Base64.getEncoder().encodeToString(password.getBytes()))) {
				return new ResponseEntity("Successful Login", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity("Wrong Password", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity("Invalid Credentials", HttpStatus.BAD_REQUEST);
		}
	}
	
	public Optional<City> getCityById(int city_id) {
		return cityRepository.findById(city_id);
	}
	
	public ResponseEntity<String> modifyCity(String cityName, String newCityName) {
		City city = null;
		for(City c: cityRepository.findAll()) {
			if(c.getName().equalsIgnoreCase(cityName)) {
				city = c;
				break;
			}
		}
		
		if(city == null) {
			return new ResponseEntity<String>("City with the name not found!!! Please enter Correct City Name", HttpStatus.EXPECTATION_FAILED);
		} else {
			city.setName(newCityName);
			cityRepository.save(city);
			return new ResponseEntity<String>("City Updated Successfully!!!", HttpStatus.ACCEPTED);
		}
	}
	
	public ResponseEntity<String> modifyAreaName(String areaName, String newAreaName) {
		Area area = null;
		for(Area a: areaRepository.findAll()) {
			if(a.getAreaName().equalsIgnoreCase(areaName)) {
				area = a;
				break;
			}
		}
		
		if(area == null) {
			return new ResponseEntity<String>("Area with the name not found!!! Please enter Correct Area Name", HttpStatus.EXPECTATION_FAILED);
		} else {
			area.setAreaName(newAreaName);
			areaRepository.save(area);
			return new ResponseEntity<String>("Area Name Updated Successfully!!!", HttpStatus.ACCEPTED);
		}
	}
	
	public ResponseEntity<String> modifyAreaByCityName(String areaName, String newCityName) {
		City city = null;
		Area area = null;
		
		for(Area a: areaRepository.findAll()) {
			if(a.getAreaName().equalsIgnoreCase(areaName)) {
				area = a;
				break;
			}
		}
		
		for(City c: cityRepository.findAll()) {
			if(c.getName().equalsIgnoreCase(newCityName)) {
				city = c;
				break;
			}
		}
		
		if(area == null) {
			return new ResponseEntity<String>("Area with the name not found!!! Please enter Correct Area Name", HttpStatus.EXPECTATION_FAILED);
		}
		
		if(city == null) {
			return new ResponseEntity<String>("City with the name not found!!! Please enter Correct City Name", HttpStatus.EXPECTATION_FAILED);
		}
		
		area.setCity(city);
		areaRepository.save(area);
		return new ResponseEntity<String>("City Name Updated for that particular Area Name Successfully", HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<String> modifyConsumerTypeRate(int id, double rate){
		
		for (ConsumerType ct : consumerTypeRepository.findAll()) {
			if(ct.getId() == id) {
				ct.setRate(rate);
				return new ResponseEntity<String>("Consumer Type : " + ct.getTypeName() + "'s rate was changed to " + rate, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<String>("Consumer Type with " + id + " was not found.", HttpStatus.EXPECTATION_FAILED);
	}
	
	public ResponseEntity<String> addHelper(String email, String password, String name) {
		if(helperRepository.existsById(email)) {
			return new ResponseEntity<String>("Helper with given email already exists.", HttpStatus.EXPECTATION_FAILED);
		}
		else {
			Helper helper = new Helper(email, password, name);
			helperRepository.save(helper);
			return new ResponseEntity<String>("Helper added successfully.", HttpStatus.ACCEPTED);
		}
		
	}
	
	public List<Helper> viewAllHelpers(){
		List<Helper> helper = new ArrayList<>();
		for(Helper h : helperRepository.findAll()) {
			helper.add(h);
		}
		return helper;
	}
	
	public List<ConsumerType> viewAllConsumerTypes(){
		List<ConsumerType> ct = new ArrayList<>();
		for(ConsumerType c : consumerTypeRepository.findAll()) {
			ct.add(c);
		}
		return ct;
	}
	
	public List<Consumer> viewAllConsumers(){
		List<Consumer> consumers = new ArrayList<>();
		for(Consumer c : consumerRepository.findAll()) {
			consumers.add(c);
		}
		return consumers;
	}
	
	public ResponseEntity<String> removeConsumer(String email){
		if(consumerRepository.findById(email).isPresent()) {
			if((billRepository.findByEmail(new Consumer(email))).size() > 0){
				billRepository.deleteAllByEmail(new Consumer(email));
				consumerRepository.deleteById(email);
				return new ResponseEntity<String>("Consumer Bills & Account Deleted successfully.", HttpStatus.ACCEPTED);
			}else {
				consumerRepository.deleteById(email);
				return new ResponseEntity<String>("Consumer Deleted successfully.", HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<String>("Consumer Not Found.", HttpStatus.BAD_REQUEST);
	}
	
	
	
}








