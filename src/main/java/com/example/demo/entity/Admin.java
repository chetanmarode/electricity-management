package com.example.demo.entity;

import java.util.Base64;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;


@Entity
@Table(name="admin")
public class Admin {
	
	@Id
	@Email
	private String email;
	private String password;
	private String name;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(String email, String password, String name) {
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
        this.password = Base64.getEncoder().encodeToString(password.getBytes());;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Admin [email=" + email + ", password=" + password + ", name=" + name + "]";
	}
}