package com.techelevator;

import com.techelevator.view.*;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.SiteDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;

public class CampgroundCLI {	
	private Menu menu;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;

	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();	
	}

	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/capstone-2");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		parkDAO = new JDBCParkDAO(dataSource);
	}

	public void run() {
		while(true) {
			printHeading("Main Menu");
			List<Park> allParks = parkDAO.viewAllParksMenu();
			final String[] parkMenuOptions = listParks(allParks);
			String choice = (String)menu.getChoiceFromOptions(parkMenuOptions);
			if(choice.equals(parkMenuOptions[0])) {
				parkDAO.viewParkInfo(1L);
			} else if(choice.equals(parkMenuOptions[1])) {
				parkDAO.viewParkInfo(2L);
			} else if(choice.equals(parkMenuOptions[2])) {
				parkDAO.viewParkInfo(3L);
			} else if(choice.equals(parkMenuOptions[parkMenuOptions.length - 1])) {
				System.exit(0);
			}
		}
	}
	
	private String[] listParks(List<Park> parks) {
		final String[] parkMenuOptions = new String[parks.size()+1];
			for (int i = 0; i < parks.size(); i++) {
				parkMenuOptions[i] = parks.get(i).toString();
				if (i == parks.size() - 1) {
					parkMenuOptions[i+1] = "Exit";
				}
			}
		return parkMenuOptions;
	}
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	
}
