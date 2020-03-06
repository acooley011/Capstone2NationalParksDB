package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
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

import com.techelevator.DAOIntegrationTest;
import com.techelevator.model.Campground;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCCampgroundDAOTest extends DAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO dao;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	private Campground getCampground(long id, long siteId, String name, int openFromMM, int openToMM, double dailyFee) {
		Campground theCampground = new Campground();
		theCampground.setName(name);
		theCampground.setCampground_id(id);
		theCampground.setPark_id(siteId);
		theCampground.setOpen_from_mm(openFromMM);
		theCampground.setOpen_to_mm(openToMM);
		theCampground.setDaily_fee(dailyFee);
		return theCampground;
	}
	
	private void assertCampgroundAreEqual(Campground expected, Campground actual) {
		assertEquals(expected.getCampground_id(), actual.getCampground_id());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPark_id(), actual.getPark_id());
		assertEquals(expected.getOpen_from_mm(), actual.getOpen_from_mm());
		assertEquals(expected.getOpen_to_mm(), actual.getOpen_to_mm());
		assert(expected.getDaily_fee() == actual.getDaily_fee()); 


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
		
		dao = new JDBCCampgroundDAO(dataSource);
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
	public void viewCampgroundsFromParkTest() {
		
		Campground expected = getCampground(1, 1, "Blackwoods", 01, 12, 35.00);
				
		List<Campground> results = dao.viewCampgroundsFromPark((long) 1);
		
		assertEquals(3, results.size());
		assertCampgroundAreEqual(expected, results.get(0));
		
	}
	
}
