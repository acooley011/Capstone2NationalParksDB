package com.techelevator.model;

import java.util.List;

public interface ParkDAO {

	public List<Park> viewAllParksMenu();
	
	public void viewParkInfo(Long selectedPark);
	
}
