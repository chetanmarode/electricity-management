package com.example.demo.repositories;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Consumer;

@Transactional
@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>{

	
	@Modifying
	@Query("DELETE Bill b WHERE b.consumer = :consumer")
	int deleteAllByEmail(Consumer consumer);
	
	
	@Query("SELECT b FROM Bill b WHERE b.consumer = :consumer")
	List<Bill> findByEmail(Consumer consumer);

	@Query("SELECT b FROM Bill b WHERE Year(b.billDate) = :year AND Month(b.billDate) = :month")
	List<Bill> findByMonthAndYear(int month, int year);
	
	

}
