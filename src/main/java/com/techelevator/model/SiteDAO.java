package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

	public List<Site> searchForSiteInDateRange(Long selectedCampgroundID, LocalDate userToDate, LocalDate userFromDate);
	//return a list of Site number, Max Occupancy, Accessible bool, max RV length, and utilities bool
	//only return sites in selected campground that is not in the date range of any other reservations
	
}
