package com.techelevator.model;

import java.util.List;

public interface ParkDAO {

	public List<Park> viewAllParksMenu();
	
	public List<Park> viewParkInfo(Long selectedPark);
	
}
