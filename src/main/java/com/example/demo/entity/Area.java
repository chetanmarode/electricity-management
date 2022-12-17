package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="area")
public class Area {
	
	@Id
	@Column(name="area_id")
	private int id;
	private String areaName;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;
	
	@OneToMany(targetEntity = Consumer.class, cascade = CascadeType.ALL)
	private Set<Consumer> consumers = new HashSet<Consumer>();
	
	
	public Set<Consumer> getConsumers() {
		return consumers;
	}
	public void setConsumers(Set<Consumer> consumers) {
		this.consumers = consumers;
	}
	public Area() {
		super();
	}
	public Area(int id, String areaName, City city) {
		super();
		this.id = id;
		this.areaName = areaName;
		this.city = city;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Area [id=" + id + ", areaName=" + areaName + ", city=" + city + ", consumers=" + consumers + "]";
	}
	
		
}