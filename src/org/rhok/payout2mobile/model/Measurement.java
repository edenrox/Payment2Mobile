package org.rhok.payout2mobile.model;

public class Measurement {

	private MeasurementStation station; // where?
	private String metric; // what?
	private double magnitude; // how much?
	
	public MeasurementStation getStation() { return station; }
	public String getMetric() { return metric; }
	public double getMagnitude() { return magnitude; }
	
	
	public Measurement(MeasurementStation pStation, String pMetric, double pMagnitude) {
		station = pStation;
		metric = pMetric;
		magnitude = pMagnitude;
	}
	
}
