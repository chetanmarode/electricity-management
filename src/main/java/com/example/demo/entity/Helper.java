package com.example.demo.entity;

import java.util.Base64;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Helper {
	@Id
	private String email;
	private String password;
	private String name;
	public Helper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Helper(String email, String password, String name) {
		super();
		this.email = email;
		setPassword(password);
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Helper [email=" + email + ", password=" + password + ", name=" + name + "]";
	}
	
}