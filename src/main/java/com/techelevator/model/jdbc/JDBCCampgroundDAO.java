package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> viewCampgroundsFromPark(Long park_id) {
		ArrayList<Campground> allCampgroundsFromPark = new ArrayList<>();
		String sqlGetAllCampgroundsFromPark = "SELECT campground.name, open_from_mm, open_to_mm, daily_fee " +
											  "campground JOIN park ON park.park_id = campground.park_id" +
											  "WHERE park.park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgroundsFromPark, park_id);
		while (results.next()) {
			Campground theCampground = mapRowToCampground(results);
			allCampgroundsFromPark.add(theCampground);
		}
		return allCampgroundsFromPark;
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampground_id(results.getLong("campground_id"));
		theCampground.setPark_id(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		if (results.getString("open_from_mm") == null) {
			theCampground.setOpen_from_mm(null);
		} else {
			theCampground.setOpen_from_mm(LocalDate.parse(results.getString("open_from_mm")));
		}
		if (results.getString("open_to_mm") == null) {
			theCampground.setOpen_to_mm(null);
		} else {
			theCampground.setOpen_to_mm(LocalDate.parse(results.getString("open_to_mm")));
		}
		theCampground.setDaily_fee(results.getDouble("daily_fee"));
		return theCampground;
	}

}
