package com.example.demo.dto;

import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class BillResponse {
	private int bill_id;
	private String email;
	private String city;
	private String area;
	private Date date;
	private int units;
	private double amount;
	
	public BillResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public BillResponse(int bill_id, String email, String city, String area, Date date, int units,
			double amount) {
		super();
		this.bill_id = bill_id;
		this.email = email;
		this.city = city;
		this.area = area;
		this.date = date;
		this.units = units;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "BillResponse [bill_id=" + bill_id + ", email=" + email + ", city=" + city + ", area=" + area + ", date="
				+ date + ", units=" + units + ", amount=" + amount + "]";
	}
	
	
}
