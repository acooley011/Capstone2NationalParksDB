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
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

public class JDBCSiteDAOTest extends DAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	private Site getSite(long siteId, long campgroundId, int siteNumber, int maxOccupancy, boolean utilities, boolean accessible, double maxRVLength) {
		Site theSite = new Site();
		theSite.setSiteId(siteId);
		theSite.setCampgroundId(campgroundId);
		theSite.setSiteNumber(siteNumber);
		theSite.setMaxOccupancy(maxOccupancy);
		theSite.setUtilities(utilities);
		theSite.setAccessible(accessible);
		theSite.setMaxRVLength(maxRVLength);
		return theSite;
	}
	
	private void assertSitesAreEqual(Site expected, Site actual) {
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
		assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
		assert(expected.getMaxRVLength() == actual.getMaxRVLength());
		assertEquals(expected.isAccessible(), actual.isAccessible());
		assertEquals(expected.isUtilities(), actual.isUtilities());

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
		
		dao = new JDBCSiteDAO(dataSource);
		
		
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
	public void searchForSiteInDateRangeTest() {
		
		LocalDate testDateFrom = LocalDate.parse("2001-02-22");
		LocalDate testDateTo = LocalDate.parse("2001-09-22");
		
		List<Site> actual = dao.searchForSiteInDateRange((long)7, testDateFrom, testDateTo);
		
		assertEquals(5, actual.size());
	}
}
