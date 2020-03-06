package com.techelevator.model.jdbc;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

	@Override
	public Long createReservationFromSite(int selectedSite, String name, LocalDate userToDate, LocalDate userFromDate) {	
		
		String sqlCreateReservation = "INSERT INTO reservation (site_id, name, "
				+ "from_date, to_date, create_date) VALUES (?, ?, ?, ?, ?) RETURNING reservation_id";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCreateReservation, selectedSite, name, userToDate, userFromDate, LocalDate.now());
		results.next();
		return results.getLong("reservation_id");
		
	}

}
