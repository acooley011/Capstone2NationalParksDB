package com.techelevator;

import com.techelevator.view.*;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
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
	
	private static final String CGMENU_OPTION_VIEW_CAMPGROUND = "View Campgrounds";
	private static final String CGMENU_OPTION_SEARCH_RESERVATION = "Search for Reservation";
	private static final String CGMENU_OPTION_RETURN_TO_PREV = "Return to Previous Screen";
	private static final String[] CGMENU_OPTIONS = new String[] {CGMENU_OPTION_VIEW_CAMPGROUND,
																CGMENU_OPTION_SEARCH_RESERVATION,
																CGMENU_OPTION_RETURN_TO_PREV};

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
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
	}

	public void run() {
		while (true) {
			printHeading("Select a park for further details:");
			List<Park> allParks = parkDAO.viewAllParksMenu();
			final String[] parkMenuOptions = listParks(allParks);
			String choice = (String) menu.getChoiceFromOptions(parkMenuOptions);
			if (choice.equals(parkMenuOptions[0])) {
				parkDAO.viewParkInfo(1L);
				parkInfoScreen(1L);
			} else if (choice.equals(parkMenuOptions[1])) {
				parkDAO.viewParkInfo(2L);
				parkInfoScreen(2L);
			} else if (choice.equals(parkMenuOptions[2])) {
				parkDAO.viewParkInfo(3L);
				parkInfoScreen(3L);
			} else if (choice.equals(parkMenuOptions[parkMenuOptions.length - 1])) {
				System.exit(0);
			}
		}
	}

	public void parkInfoScreen(Long selected_park_id) {
		while (true) {
			printHeading("Select a command:");
			String choice = (String) menu.getChoiceFromOptions(CGMENU_OPTIONS);
			if (choice.equals(CGMENU_OPTION_VIEW_CAMPGROUND)) {
				campgroundDAO.viewCampgroundsFromPark(selected_park_id);
			} else if (choice.equals(CGMENU_OPTION_SEARCH_RESERVATION)) {
				parkDAO.viewParkInfo(2L);
			} else if (choice.equals(CGMENU_OPTION_RETURN_TO_PREV)) {
				parkDAO.viewParkInfo(3L);
			}
		}
	}

	private String[] listParks(List<Park> parks) {
		final String[] parkMenuOptions = new String[parks.size() + 1];
		for (int i = 0; i < parks.size(); i++) {
			parkMenuOptions[i] = parks.get(i).toString();
			if (i == parks.size() - 1) {
				parkMenuOptions[i + 1] = "Exit";
			}
		}
		return parkMenuOptions;
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
