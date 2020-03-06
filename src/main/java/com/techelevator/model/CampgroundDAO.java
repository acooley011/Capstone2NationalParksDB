package com.techelevator.model;

import java.util.List;

public interface CampgroundDAO {

	public List<Campground> viewCampgroundsFromPark(Long park_id);
	
	
}
