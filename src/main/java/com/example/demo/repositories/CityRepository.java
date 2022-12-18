package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

	@Query("SELECT c FROM City c WHERE c.name = :city")
	List<City> findByCity(String city);

}