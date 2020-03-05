package com.techelevator.model;

import java.time.LocalDate;

public class Park {
	private Long campground_id;
	private Long park_id;
	private String name;
	private LocalDate open_from_mm;
	private LocalDate open_to_mm;
	private double daily_fee;
	
	public Long getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}
	public Long getPark_id() {
		return park_id;
	}
	public void setPark_id(Long park_id) {
		this.park_id = park_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getOpen_from_mm() {
		return open_from_mm;
	}
	public void setOpen_from_mm(LocalDate open_from_mm) {
		this.open_from_mm = open_from_mm;
	}
	public LocalDate getOpen_to_mm() {
		return open_to_mm;
	}
	public void setOpen_to_mm(LocalDate open_to_mm) {
		this.open_to_mm = open_to_mm;
	}
	public double getDaily_fee() {
		return daily_fee;
	}
	public void setDaily_fee(double daily_fee) {
		this.daily_fee = daily_fee;
	}
	
}