package com.example.demo.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BillResponse;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Area;
import com.example.demo.entity.Bill;
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
	
	//add admin - will be removed later
	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}
	
	//add city using city object from requestbody
	public ResponseEntity<String> addCity(City city) {
		if(cityRepository.existsById(city.getId())) {
			// Exception 
			return new ResponseEntity<String>("City with the same ID already exists!!!", HttpStatus.BAD_REQUEST);
			
		} else {
			cityRepository.save(city);
			return new ResponseEntity<String>("City Added Successfully!!!", HttpStatus.ACCEPTED);
		}
	}
	
	//using for the below written function addArea()
	public Optional<City> getCityById(int city_id) {
		return cityRepository.findById(city_id);
	}
	
	//add new area in database
	public ResponseEntity<String> addArea(int area_id, String area_name, int city_id) {
		if(areaRepository.existsById(area_id)) {
			// Exception 
//			System.out.println("Area with the same ID already exists!!!");
			return new ResponseEntity<String>("Area with the same ID already exists!!!", HttpStatus.BAD_REQUEST);
		}else if(!(cityRepository.findById(city_id).isPresent())) {
			// Exception 
			return new ResponseEntity<String>("City Not Found", HttpStatus.NOT_FOUND);
		}
		else {
			City city = getCityById(city_id).get();
			Area area = new Area(area_id, area_name, city);
			areaRepository.save(area);
//			System.out.println("Area Added Successfully!!!");
			return new ResponseEntity<String>("Area Added Successfully!!!", HttpStatus.ACCEPTED);
		}
	}
	
	//view all cities
	public List<Map<String, String>> viewAllCities() {
		
		List<Map<String, String>> result = new ArrayList<>();
		
		for (City city : cityRepository.findAll()) {
			Map<String, String> hm = new LinkedHashMap<>();
			hm.put("id", String.valueOf(city.getId()));
			hm.put("name", city.getName());
			
			result.add(hm);
		}
		
		return result;
	}
	
	//view all areas
	public List<Map<String, String>> viewAllAreas() {
		
		List<Map<String, String>> result = new ArrayList<>();
		
		for (Area area : areaRepository.findAll()) {
			Map<String, String> hm = new LinkedHashMap<>();
			hm.put("id", String.valueOf(area.getId()));
			hm.put("name", area.getAreaName());
			hm.put("city_name",  area.getCity().getName());
			result.add(hm);
		}
		return result;
	}
	
	//view all areas by city name
	public List<Map<String, String>> viewAreaByCityName(String cityName) {
		City city = cityRepository.findByCity(cityName);
		
		List<Map<String, String>> result = new ArrayList<>();
		
		for (Area area : areaRepository.findAreaByCity(city)) {
			Map<String, String> hm = new LinkedHashMap<>();
			hm.put("city_name", area.getCity().getName());
			hm.put("areaId", String.valueOf(area.getId()));
			hm.put("area_name", area.getAreaName());
			result.add(hm);
		}
		
		return result;
	}
	
	//administrator login
	public ResponseEntity<String> loginAdmin(String email, String password) {
		if(adminRepository.findById(email).isPresent()) {
			if(adminRepository.findById(email).get().getPassword().equals(Base64.getEncoder().encodeToString(password.getBytes()))) {
				return new ResponseEntity<String>("Successful Login", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>("Wrong Password", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//update existing city name to new name
	public ResponseEntity<String> modifyCity(String cityName, String newCityName) {
		//city name will be provided in the dropdown so no need to worry about case sensitivity
		City city = cityRepository.findByCity(cityName);
		city.setName(newCityName);
		cityRepository.save(city);
		return new ResponseEntity<String>("City Updated Successfully!!!", HttpStatus.ACCEPTED);
		
	}
	
	//update existing area name to the new area name
	public ResponseEntity<String> modifyAreaName(String areaName, String newAreaName) {
		//area name will be provided in the dropdown so no need to worry about case sensitivity
		Area area = areaRepository.findByArea(areaName);
		area.setAreaName(newAreaName);
		areaRepository.save(area);
		return new ResponseEntity<String>("Area Name Updated Successfully!!!", HttpStatus.ACCEPTED);
	}
	
	//update area with a new city name
	public ResponseEntity<String> modifyAreaByCityName(String areaName, String newCityName) {
		
		Area area = areaRepository.findByArea(areaName);
		City city = cityRepository.findByCity(newCityName);
		
		area.setCity(city);
		areaRepository.save(area);
		return new ResponseEntity<String>("City Name Updated for "+ areaName+" to "+ newCityName, HttpStatus.ACCEPTED);
	}
	
	//modify consumer type rate
	public ResponseEntity<String> modifyConsumerTypeRate(int id, double rate){
		
		for (ConsumerType ct : consumerTypeRepository.findAll()) {
			if(ct.getId() == id) {
				ct.setRate(rate);
				consumerTypeRepository.save(ct);
				return new ResponseEntity<String>("Consumer Type : " + ct.getTypeName() + "'s rate was changed to " + rate, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<String>("Consumer Type with " + id + " was not found.", HttpStatus.EXPECTATION_FAILED);
	}
	
	//add helper
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
	
	//view all helpers
	public List<Helper> viewAllHelpers(){
		return helperRepository.findAll();
	}
	
	//view all consumer types
	public List<ConsumerType> viewAllConsumerTypes(){
		return consumerTypeRepository.findAll();
	}
	
	//view all consumers
	public List<Consumer> viewAllConsumers(){
		return consumerRepository.findAll();
	}
	
	//remove a consumer
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
	
	
	//View all Bills by passing Consumer Email
	public List<Map<String,String>> viewAllBillsByConsumerId(String email){
		List<Bill> records = new ArrayList<Bill>();
		if(consumerRepository.findById(email).isPresent()) {
			records =  billRepository.findAll().stream()
					.filter(bill -> bill.getConsumer().getEmail().equalsIgnoreCase(email))
					.collect(Collectors.toList());
		}
		List<Map<String, String>> result = new ArrayList<>();
		for (Bill b : records) {
			
			Map<String, String> hm = new LinkedHashMap<>();
			
			hm.put("Bill Id", String.valueOf(b.getId()));
			hm.put("email", email);
			hm.put("billDate", String.valueOf(b.getBillDate()));
			hm.put("UnitsConsumed", String.valueOf(b.getUnitsConsumed()));
			hm.put("TotalAmount", String.valueOf(b.getTotalAmount()));	
			
			result.add(hm);
		}
		
		return result;
	}
	
		//View all Bills present in Bill records
		public List<Map<String,String>> viewAllBills(){
			List<Bill> records = new ArrayList<Bill>();
			records =  billRepository.findAll();

			List<Map<String, String>> result = new ArrayList<>();
			for (Bill b : records) {
				
				Map<String, String> hm = new LinkedHashMap<>();
				
				hm.put("Bill Id", String.valueOf(b.getId()));
				hm.put("email", String.valueOf(b.getConsumer().getEmail()));
				hm.put("billDate", String.valueOf(b.getBillDate()));
				hm.put("UnitsConsumed", String.valueOf(b.getUnitsConsumed()));
				hm.put("TotalAmount", String.valueOf(b.getTotalAmount()));	
				
				result.add(hm);
			}
			
			return result;
		}
		
	
		//Just a trial method - working but will not use
//		//View all Bills by passing Consumer City Name
		//Returning BillResponse Objects toString
		public List<String> viewAllBillsByCity2(String city){
			List<Bill> records = new ArrayList<Bill>();
			
			if(cityRepository.findByCity(city) != null) {
				records =  billRepository.findAll().stream()
						.filter(bill -> bill.getConsumer().getArea().getCity().getName().equalsIgnoreCase(city))
						.collect(Collectors.toList());
			}
			List<String> result = new ArrayList<>();
			for (Bill b : records) {
				
				BillResponse br = new BillResponse(b.getId(), b.getConsumer().getEmail(), 
						b.getConsumer().getArea().getCity().getName(), b.getConsumer().getArea().getAreaName(), 
						b.getBillDate(), b.getUnitsConsumed(), b.getTotalAmount());
				result.add(br.toString());
//				System.out.println(br);
				
			}
			
			return result;
		}
		
		//dummy method to get complete response of bills - to be deleted later
		public List<Bill> viewBills(){
			return billRepository.findAll();
		}
		
		
		//view all bills by passing Area Name
		public List<Map<String, String>> viewAllBillsByArea(String area) {
			List<Bill> records = new ArrayList<Bill>();
			
			if(areaRepository.findByArea(area) != null) {
				records =  billRepository.findAll().stream()
						.filter(bill -> bill.getConsumer().getArea().getAreaName().equalsIgnoreCase(area))
						.collect(Collectors.toList());
			}
			List<Map<String, String>> result = new ArrayList<>();
			for (Bill b : records) {
				
				Map<String, String> hm = new LinkedHashMap<>();
				
				hm.put("Area", area);
				hm.put("Bill Id", String.valueOf(b.getId()));
				hm.put("Email", b.getConsumer().getEmail());
				hm.put("billDate", String.valueOf(b.getBillDate()));
				hm.put("UnitsConsumed", String.valueOf(b.getUnitsConsumed()));
				hm.put("TotalAmount", String.valueOf(b.getTotalAmount()));	
				
				result.add(hm);
			}
			
			return result;
		}
	
		
		//view all bills by passing City Name - Main Method to get bills by city
		public List<Map<String, String>> viewAllBillsByCity(String city) {
			List<Bill> records = new ArrayList<Bill>();
			
			if(cityRepository.findByCity(city) != null) {
				records =  billRepository.findAll().stream()
						.filter(bill -> bill.getConsumer().getArea().getCity().getName().equalsIgnoreCase(city))
						.collect(Collectors.toList());
			}
			List<Map<String, String>> result = new ArrayList<>();
			for (Bill b : records) {
				
				Map<String, String> hm = new LinkedHashMap<>();
				
				hm.put("City", city);
				hm.put("Bill Id", String.valueOf(b.getId()));
				hm.put("Email", b.getConsumer().getEmail());
				hm.put("billDate", String.valueOf(b.getBillDate()));
				hm.put("UnitsConsumed", String.valueOf(b.getUnitsConsumed()));
				hm.put("TotalAmount", String.valueOf(b.getTotalAmount()));	
				
				result.add(hm);
			}
			
			return result;
		}
		
		//view all bills by passing month and year
		public List<Map<String, String>> viewAllBillsByMonthAndYear(int month, int year) {
			List<Bill> records = new ArrayList<Bill>();
			
			
			if(billRepository.findByMonthAndYear(month, year).size() > 0) {
				records =  billRepository.findByMonthAndYear(month, year);
			}
			List<Map<String, String>> result = new ArrayList<>();
			for (Bill b : records) {
				
				Map<String, String> hm = new LinkedHashMap<>();
				
				hm.put("Month", String.valueOf(month));
				hm.put("Year", String.valueOf(year));
				hm.put("Bill Id", String.valueOf(b.getId()));
				hm.put("Email", b.getConsumer().getEmail());
				hm.put("billDate", String.valueOf(b.getBillDate()));
				hm.put("UnitsConsumed", String.valueOf(b.getUnitsConsumed()));
				hm.put("TotalAmount", String.valueOf(b.getTotalAmount()));	
				
				result.add(hm);
			}
			System.out.println(result);
			return result;
		}
	
}








