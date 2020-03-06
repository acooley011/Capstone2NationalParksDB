package com.techelevator;

import com.techelevator.view.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;
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
	private static final String[] CGMENU_OPTIONS = new String[] { CGMENU_OPTION_VIEW_CAMPGROUND,
			CGMENU_OPTION_SEARCH_RESERVATION, CGMENU_OPTION_RETURN_TO_PREV };

	private static final String RESMENU_SEARCH_AVAILABLE = "Search for Available Reservation";
	private static final String RESMENU_RETURN_TO_PREV = "Return to Previous Screen";
	private static final String[] RESMENU_OPTIONS = new String[] { RESMENU_SEARCH_AVAILABLE, RESMENU_RETURN_TO_PREV };

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
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
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
				campgroundReserveScreen(selected_park_id);
			} else if (choice.equals(CGMENU_OPTION_SEARCH_RESERVATION)) {
				campgroundDAO.viewCampgroundsFromPark(selected_park_id);
			} else if (choice.equals(CGMENU_OPTION_RETURN_TO_PREV)) {
				run();
			}
		}
	}

	public void campgroundReserveScreen(Long selected_park_id) {
		while (true) {
			printHeading("Select a command:");
			String choice = (String) menu.getChoiceFromOptions(RESMENU_OPTIONS);
			if (choice.equals(RESMENU_SEARCH_AVAILABLE)) {
				campgroundDAO.viewCampgroundsFromPark(selected_park_id);
				checkAvailabilityPrompt(selected_park_id);
			} else if (choice.equals(RESMENU_RETURN_TO_PREV)) {
				parkInfoScreen(selected_park_id);
			}
		}
	}

	public void checkAvailabilityPrompt(Long selected_park_id) {
		Scanner input = new Scanner(System.in);
		Scanner inputDate = new Scanner(System.in);
		System.out.println("Which campground? (enter 0 to cancel): ");
		Long inputCampground = input.nextLong();
		if (inputCampground == 0) {
			campgroundReserveScreen(selected_park_id);
		}
		System.out.println("What is the arrival date? (format as YYYY-MM-DD): ");
		LocalDate inputArrival = LocalDate.parse(inputDate.nextLine());
		System.out.println("What is the departure date? (format as YYYY-MM-DD): ");
		LocalDate inputDeparture = LocalDate.parse(inputDate.nextLine());
		input.close();
		inputDate.close();

		List<Site> sitesInRange = siteDAO.searchForSiteInDateRange(inputCampground, inputArrival, inputDeparture);
		System.out.println("Site no.\tMax Occup.\tAccessible?\tMax RV Length\tUtility");
		listSites(sitesInRange);
		reservationPrompt(selected_park_id, inputArrival, inputDeparture);
	}

	public void reservationPrompt(Long selected_park_id, LocalDate inputArrival, LocalDate inputDeparture) {
		Scanner input = new Scanner(System.in);
		System.out.println("Which site will you reserve? (enter 0 to cancel): ");
		int inputSite = input.nextInt();
		if (inputSite == 0) {
			campgroundReserveScreen(selected_park_id);
		}
		System.out.println("What name should the reservation be made under?: ");
		String inputName = input.nextLine();
		
		reservationDAO.createReservationFromSite(inputSite, inputName, inputArrival, inputDeparture);
		System.out.println("The reservation has been made and the reservation id is ");
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

	private void listSites(List<Site> sitesInRange) {
		for (int i = 0; i < sitesInRange.size(); i++) {
			System.out.println(sitesInRange.get(i).toString());
		}
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
