package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> searchForSiteInDateRange(Long selectedCampgroundID, LocalDate userToDate,
			LocalDate userFromDate) {

		List<Site> output = new ArrayList<Site>();
		
		String sqlSearchForSite = "SELECT site.* FROM site JOIN reservation ON site.site_id = reservation.site_id "
				+ "WHERE ? NOT BETWEEN reservation.to_date AND reservation.from_date "
				+ "AND ? NOT BETWEEN reservation.to_date AND reservation.from_date ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForSite, userToDate, userFromDate);
		
		while(results.next()) {
			Site nextSite = new Site();
			nextSite.setSiteId(results.getLong("site_id"));
			nextSite.setCampgroundId(results.getLong("campground_id"));
			nextSite.setSiteNumber(results.getInt("site_number"));
			nextSite.setMaxOccupancy(results.getInt("max_occupancy"));
			nextSite.setAccessible(results.getBoolean("site_number"));
			nextSite.setMaxRVLength(results.getInt("max_rv_length"));
			nextSite.setSiteNumber(results.getInt("site_number"));
			nextSite.setUtilities(results.getBoolean("utilities"));
			
			output.add(nextSite);
		}
		
		return output;
		
	}

}
