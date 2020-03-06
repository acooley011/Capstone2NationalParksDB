package com.techelevator.model;

import java.time.LocalDate;

public interface ReservationDAO {
	
	public Long createReservationFromSite(int selectedSite, String name, LocalDate userToDate, LocalDate userFromDate);

	//create (insert into) a reservation to selected site (site can only be selected if availble through 
		//Site search method) with the given name. return reservation ID when created 

}
