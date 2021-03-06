package com.techelevator.model;

public class Site {
	private Long siteId;
	private Long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private double maxRVLength;
	private boolean utilities;
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public double getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(double maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	public String toString() {
		String siteInfo = String.valueOf(siteNumber) + "\t\t" + String.valueOf(maxOccupancy) + "\t\t" + String.valueOf(accessible) + "\t\t" + String.valueOf(maxRVLength) + "\t\t" + String.valueOf(utilities) + "\t\t";
		return siteInfo;
	}

}
