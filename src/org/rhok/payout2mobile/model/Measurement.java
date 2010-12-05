package org.rhok.payout2mobile.model;

import java.util.Date;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Measurement {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@NotPersistent
	private Identity station; // which station is this from
	
	@NotPersistent
	//@Embedded
	private Location location; // where was the measurement taken?
	
	@Persistent
	private String metric; // what are we measuring?
	
	@Persistent
	private double magnitude; // how much?
	
	@Persistent
	private String units; // units the magnitude is in
	
	@Persistent
	private Date created;
	
	public long getId() { return id.longValue(); }
	public Identity getStation() { return station; }
	public Location getLocation() { return location; }
	public String getMetric() { return metric; }
	public double getMagnitude() { return magnitude; }
	public String getUnits() { return units; }
	public Date getCreated() { return created; }
	
	public Measurement(Identity pStation, Location pLocation, String pMetric, double pMagnitude, String pUnits) {
		station = pStation;
		location = pLocation;
		metric = pMetric;
		magnitude = pMagnitude;
		units = pUnits;
		created = new Date();
	}
	
}
