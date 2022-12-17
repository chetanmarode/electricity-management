package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ConsumerType {
	@Id
	@Column(name = "consumer_type_id")
	private int id;
	private String typeName;
	private double rate;
	
	@OneToMany(targetEntity = Consumer.class, cascade = CascadeType.ALL)
	private Set<Consumer> consumers = new HashSet<Consumer>();
	
	public Set<Consumer> getConsumers() {
		return consumers;
	}
	public void setConsumers(Set<Consumer> consumers) {
		this.consumers = consumers;
	}
	public ConsumerType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ConsumerType(int id, String typeName, double rate) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.rate = rate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "ConsumerType [id=" + id + ", typeName=" + typeName + ", rate=" + rate + ", consumers=" + consumers
				+ "]";
	}
	
}	