package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;


import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCReservationDAOTest extends DAOIntegrationTest{

	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO dao;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	private Reservation getReservation(long id, String name, long siteID, LocalDate createDate, LocalDate toDate, LocalDate fromDate) {
		Reservation theReservation = new Reservation();
		theReservation.setName(name);
		theReservation.setReservationId(id);
		theReservation.setSiteId(siteID);
		theReservation.setCreateDate(createDate);
		theReservation.setToDate(toDate);;
		theReservation.setFromDate(fromDate);
		return theReservation;
	}
	

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/capstone-2");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		
		dao = new JDBCReservationDAO(dataSource);
		
		String sqlDeleteReservation = "DELETE FROM reservation";
		jdbcTemplate.update(sqlDeleteReservation);

	}
	
	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Test
	public void createReservationFromSiteTest() {
		LocalDate testDateFrom = LocalDate.parse("2001-02-22");
		LocalDate testDateTo = LocalDate.parse("2001-09-22");
		Reservation expected = getReservation((long)1, "Joeb", (long)1, LocalDate.now(), testDateFrom, testDateTo);

		Long actualId = dao.createReservationFromSite(1, "Joeb", testDateFrom, testDateTo);
		
		String sqlGetExpectedId = "SELECT reservation_id FROM reservation WHERE reservation_id = ?";
		long expectedId = jdbcTemplate.queryForObject(sqlGetExpectedId, Long.class, actualId);
		
		assert(expectedId == actualId);
		
		
		
	}
	
}
