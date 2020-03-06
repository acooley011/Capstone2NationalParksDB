package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {

	public List<Campground> viewCampgroundsFromPark() {
		return null;
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
