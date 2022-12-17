package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="consumer")
	private Consumer consumer;
	
	private Date billDate;
	private int unitsConsumed;
	private double totalAmount;
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bill(int id, Consumer consumer, Date billDate, int unitsConsumed, double totalAmount) {
		super();
		this.id = id;
		this.consumer = consumer;
		this.billDate = billDate;
		this.unitsConsumed = unitsConsumed;
		this.totalAmount = totalAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public int getUnitsConsumed() {
		return unitsConsumed;
	}
	public void setUnitsConsumed(int unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", consumer=" + consumer + ", billDate=" + billDate + ", unitsConsumed="
				+ unitsConsumed + ", totalAmount=" + totalAmount + "]";
	}
	
	
}