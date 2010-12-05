package org.rhok.payout2mobile.model;

public class MeasurementStation {

	private Identity who;
	private Location location;
	private String name;
	
	public Location getLocation() { return location; }
	public String getName() { return name; }
	
	public MeasurementStation(Identity pWho, Location pLocation, String pName) {
		who = pWho;
		location = pLocation;
		name = pName;
	}
}
