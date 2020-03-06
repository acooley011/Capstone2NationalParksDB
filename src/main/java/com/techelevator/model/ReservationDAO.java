package com.techelevator.model;

import java.util.Date;

public interface ReservationDAO {
	
	public void createReservationFromSite(int selectedSite, String name, Date userToDate, Date userFromDate);
	//create (insert into) a reservation to selected site (site can only be selected if availble through 
		//Site search method) with the given name. return reservation ID when created 

}
