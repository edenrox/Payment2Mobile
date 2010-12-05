package org.rhok.payout2mobile.controllers;

public class CC {
	
	private static CC s_instance;
	public static CC get() {
		if (s_instance == null) {
			s_instance = new CC();
		}
		return s_instance; 
	}
	
	private ClaimController c_claim;
	private IdentityController c_identity;
	private MeasurementController c_measurement;
	private MeasurementStationController c_measurementStation;
	private PolicyController c_policy;
	

	public ClaimController claim() {
		return c_claim;
	}
	public IdentityController identity() {
		return c_identity;
	}
	public MeasurementController measurement() {
		return c_measurement;
	}
	public MeasurementStationController measurementStation() {
		return c_measurementStation;
	}
	public PolicyController policy() {
		return c_policy;
	}
	
	protected CC() {
		c_claim = new ClaimController();
		c_identity = new IdentityController();
		c_measurement = new MeasurementController();
		c_measurementStation = new MeasurementStationController();
		c_policy = new PolicyController();
	}
	
}
