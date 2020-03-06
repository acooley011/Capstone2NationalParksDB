package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

	@Override
	public void createReservationFromSite(int selectedSite, String name, LocalDate userToDate, LocalDate userFromDate) {
		
		List<Reservation> output = new ArrayList<Reservation>();
		
		String sqlCreateReservation = "INSERT INTO reservation (SiteId, name, "
				+ "fromDate, toDate, createDate) VALUES (?, ?, ?, ?) RETURNING reservation_id";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCreateReservation, selectedSite, name, userToDate, userFromDate);
	
		while(results.next()) {
			Site nextReservation = new Site();
			
			
		}
		
	}

}
