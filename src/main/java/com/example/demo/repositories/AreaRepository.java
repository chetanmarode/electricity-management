package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{

	@Query("SELECT a FROM Area a WHERE a.areaName = :area")
	List<Area> findByArea(String area);

}
