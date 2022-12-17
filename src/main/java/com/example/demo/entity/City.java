package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class City {
	
	@Id
	@Column(name="city_id")	
	private int id;
	private String name;
	
	@OneToMany(targetEntity = Area.class, cascade = CascadeType.ALL)
	private Set<Area> city_areas = new HashSet<Area>(); 
	
	
	public Set<Area> getCity_areas() {
		return city_areas;
	}
	public void setCity_areas(Set<Area> city_areas) {
		this.city_areas = city_areas;
	}
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	public City(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + "]";
	}
	
	
}