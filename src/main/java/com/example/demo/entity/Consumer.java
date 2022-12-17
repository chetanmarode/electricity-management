package com.example.demo.entity;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity()
public class Consumer {
	
	@Id
	@Column(name = "consumer_id")
	private String email;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "area_id")
	private Area area;
	
	@ManyToOne
	@JoinColumn(name = "consumer_type")
	private ConsumerType consumer_type;
	
	private String password;
	
	@OneToMany(targetEntity = Bill.class, cascade = CascadeType.ALL)
	private Set<Bill> bills = new HashSet<Bill>();
	
	public Set<Bill> getBills() {
		return bills;
	}
	public void setConsumer_bills(Set<Bill> bills) {
		this.bills = bills;
	}
	public Consumer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Consumer(String email, String name, Area area, ConsumerType consumer_type, String password,
			Set<Bill> bills) {
		super();
		this.email = email;
		this.name = name;
		this.area = area;
		this.consumer_type = consumer_type;
		setPassword(password);
		this.bills = bills;
	}
	
	public Consumer(String email) {
		this.email = email;
	}
	
	public Consumer(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public ConsumerType getConsumer_type() {
		return consumer_type;
	}
	public void setConsumer_type(ConsumerType consumer_type) {
		this.consumer_type = consumer_type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = Base64.getEncoder().encodeToString(password.getBytes());
	}
	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}
	@Override
	public String toString() {
		return "Consumer [email=" + email + ", name=" + name + ", area=" + area + ", consumer_type=" + consumer_type
				+ ", password=" + password + ", bills=" + bills + "]";
	}
	
	
}