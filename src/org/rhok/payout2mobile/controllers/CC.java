/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.controllers;

import org.rhok.payout2mobile.policyproviders.ExactQuoteProvider;

public class CC {
	
	private static CC s_instance;
	public static CC get() {
		if (s_instance == null) {
			s_instance = new CC();
			s_instance.scenario.init();
			s_instance.policy().addProvider(new ExactQuoteProvider(0.25));
		}
		return s_instance; 
	}
	
	private AppScenario scenario;
	private ClaimController c_claim;
	private IdentityController c_identity;
	private MeasurementController c_measurement;
	private PolicyController c_policy;
	

	public AppScenario scenario() { return scenario; }
	public ClaimController claim() { return c_claim; }
	public IdentityController identity() { return c_identity; }
	public MeasurementController measurement() { return c_measurement; }
	public PolicyController policy() { return c_policy; }
	
	protected CC() {
		c_claim = new ClaimController();
		c_identity = new IdentityController();
		c_measurement = new MeasurementController();
		c_policy = new PolicyController();
		scenario = new AppScenario();
	}
	
}
