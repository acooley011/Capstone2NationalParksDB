package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> viewAllParksMenu(){
		ArrayList<Park> allParks = new ArrayList<>();
		String sqlGetAllParks = "SELECT * "+
							   "FROM park ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while(results.next()) {
			Park thePark = mapRowToPark(results);
			allParks.add(thePark);
		}
		return allParks;
	}
	
	@Override
	public List<Park> viewParkInfo(Long selectedPark){
		return null;
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

/*
private Long park_id;
private String name;
private String location;
private LocalDate establish_date;
private double area;
private int visitors;
private String description;
*/