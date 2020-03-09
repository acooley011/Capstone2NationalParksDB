package com.techelevator.model.jdbc;

import java.text.DecimalFormat;
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
		String sqlGetAllCampgroundsFromPark = "SELECT * "
				+ "FROM campground JOIN park ON park.park_id = campground.park_id " + "WHERE park.park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgroundsFromPark, park_id);
		while (results.next()) {
			Campground theCampground = mapRowToCampground(results);
			allCampgroundsFromPark.add(theCampground);
		}
		DecimalFormat dollar = new DecimalFormat("$#.00");
		String months[] = {"January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};
		
		System.out.println("\t Name \t\t Open \t\t Close \t\t Daily Fee");
		for (int i = 0; i < allCampgroundsFromPark.size(); i++) {
			System.out.print("#" + (i+1) + " " + allCampgroundsFromPark.get(i).getName());
			System.out.print("\t\t" + months[allCampgroundsFromPark.get(i).getOpen_from_mm() - 1]);
			System.out.print("\t\t" + months[allCampgroundsFromPark.get(i).getOpen_to_mm() - 1]);
			System.out.print("\t\t" + dollar.format(allCampgroundsFromPark.get(i).getDaily_fee()));
			System.out.println();
		}
		return allCampgroundsFromPark;
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampground_id(results.getLong("campground_id"));
		theCampground.setPark_id(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpen_from_mm((results.getInt("open_from_mm")));
		theCampground.setOpen_to_mm((results.getInt("open_to_mm")));
		theCampground.setDaily_fee(results.getDouble("daily_fee"));
		return theCampground;
	}

}
