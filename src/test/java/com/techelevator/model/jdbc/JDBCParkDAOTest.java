package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

import com.techelevator.model.Park;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCParkDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO dao;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	private Park getPark(long id, String name, String location, LocalDate establishDate, double area, int visitors, String description) {
		Park thePark = new Park();
		thePark.setName(name);
		thePark.setPark_id(id);
		thePark.setLocation(location);
		thePark.setEstablish_date(establishDate);
		thePark.setArea(area);
		thePark.setVisitors(visitors);
		thePark.setDescription(description);
		return thePark;
	}
	
	private void assertParksAreEqual(Park expected, Park actual) {
		assertEquals(expected.getPark_id(), actual.getPark_id());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getEstablish_date(), actual.getEstablish_date());
		assertEquals(expected.getArea(), actual.getArea());
		assertEquals(expected.getVisitors(), actual.getVisitors());
		assertEquals(expected.getDescription(), actual.getDescription());

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
		
		dao = new JDBCParkDAO(dataSource);
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
	public void viewAllParksMenuTest() {
		
		Park expected = getPark(1, "Acadia", "Maine", LocalDate.parse("1919-02-26"), 47389, 2563129, "Covering most of Mount Desert Island and other coastal islands, Acadia features the tallest mountain on the Atlantic coast of the United States, granite peaks, ocean shoreline, woodlands, and lakes. There are freshwater, estuary, forest, and intertidal habitats.");

		List<Park> actual = dao.viewAllParksMenu();
		
		assertEquals(3, actual.size());
		assertParksAreEqual(actual.get(0), expected);

	}
	
	@Test
	public void viewParkInfoTest() {
		Park expected = getPark(1, "Acadia", "Maine", LocalDate.parse("1919-02-26"), 47389, 2563129, "Covering most of Mount Desert Island and other coastal islands, Acadia features the tallest mountain on the Atlantic coast of the United States, granite peaks, ocean shoreline, woodlands, and lakes. There are freshwater, estuary, forest, and intertidal habitats.");
		
//		String[] actual = dao.viewParkInfo((long) 1);
//		
//		assertEquals(dao.viewParkInfo((long) 1),expected);
		
	}
	
}
