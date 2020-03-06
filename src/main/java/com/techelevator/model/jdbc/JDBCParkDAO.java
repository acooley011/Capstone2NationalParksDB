package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> viewAllParksMenu() {
		ArrayList<Park> allParks = new ArrayList<>();
		String sqlGetAllParks = "SELECT * " + "FROM park ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			allParks.add(thePark);
		}
		return allParks;
	}

	@Override
	public void viewParkInfo(Long selectedPark) {
		String sqlGetAllParks = "SELECT * " + "FROM park " + "WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks, selectedPark);
		results.next();
		Park thePark = mapRowToPark(results);
		System.out.println(thePark.getName());

		System.out.println("Location \t\t" + thePark.getLocation());
		System.out.println("Established \t\t" + thePark.getEstablish_date());
		System.out.println("Area \t\t\t" + thePark.getArea() + " sq km");
		System.out.println("Annual Visitors \t" + thePark.getVisitors());
		System.out.println();
		System.out.println(thePark.getDescription());
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark;
		thePark = new Park();
		thePark.setPark_id(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		if (results.getString("establish_date") == null) {
			thePark.setEstablish_date(null);
		} else {
			thePark.setEstablish_date(LocalDate.parse(results.getString("establish_date")));
		}
		thePark.setArea(results.getDouble("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}
