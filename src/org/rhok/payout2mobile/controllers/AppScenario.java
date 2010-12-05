package org.rhok.payout2mobile.controllers;

import javax.jdo.PersistenceManager;

import org.rhok.payout2mobile.model.*;

public class AppScenario {
	
	public Identity root, system;
	public Identity v1, v2, v3, v4, v5;
	public Identity c1, c2, c3, c4, c5;
	public Identity p1, p2;
	public Identity msNairobi, msNakuru, msMombasa;
	public Location locNairobi, locNakuru, locMombasa;
	

	public void init() {
		/*
		initSystem();
		initVendors();
		initCustomers();
		initLocations();
		initStations();
		initInsuranceProviders();*/
	}
	
	protected void initSystem() {	
		// setup the root and the system
		root = new Identity(null, "911", "Root Identity", IdentityType.System);
		system = CC.get().identity().create(root, "+1 555-000-0000", "System", IdentityType.System);
	}
	
	protected void initVendors() {
		
		// setup some vendors
		v1 = CC.get().identity().create(system, "+1 555-121-1111", "Mark's Seeds", IdentityType.Vendor);
		v2 = CC.get().identity().create(system, "+1 555-121-1112", "John's Seeds", IdentityType.Vendor);
		v3 = CC.get().identity().create(system, "+1 555-121-1113", "Smith's Fertilizer", IdentityType.Vendor);
		v4 = CC.get().identity().create(system, "+1 555-121-1114", "Thompson Chemicals", IdentityType.Vendor);
		v5 = CC.get().identity().create(system, "+1 555-121-1115", "General Farm Supplies", IdentityType.Vendor);
	}
	
	protected void initCustomers() {
		
		// setup some customers
		c1 = CC.get().identity().create(system, "+1 555-231-1111", "John Michaels", IdentityType.Customer);
		c2 = CC.get().identity().create(system, "+1 555-231-1112", "Bart Simpson", IdentityType.Customer);
		c3 = CC.get().identity().create(system, "+1 555-231-1113", "Dale Barnsworth", IdentityType.Customer);
		c4 = CC.get().identity().create(system, "+1 555-231-1114", "Deeshawn Anders", IdentityType.Customer);
		c5 = CC.get().identity().create(system, "+1 555-231-1115", "Harold Macaulay", IdentityType.Customer);
	}
	
	protected void initInsuranceProviders() {
		p1 = CC.get().identity().create(system, "+1 555-240-1111", "UAP Insurance", IdentityType.InsuranceProvider);
		p2 = CC.get().identity().create(system, "+1 555-240-1112", "Lloyd's of London", IdentityType.InsuranceProvider);
	}
	
	protected void initStations() {
		
		// setup some measurement stations
		msNairobi = CC.get().identity().create(system, "+1 555-444-1111", "Nairobi Station", IdentityType.MeasurementStation);
		msNakuru = CC.get().identity().create(system, "+1 555-444-1112", "Nakuru Station", IdentityType.MeasurementStation);
		msMombasa = CC.get().identity().create(system, "+1 555-444-1113", "Mombasa Station", IdentityType.MeasurementStation);
	}
	
	protected void initLocations() {
		
		// Nairobi 1°16'S 36°48'E
		locNairobi = Location.parse("{-1.16 36.48}");
		
		// Nakuru 0°28'S 36°06'E
		locNakuru = Location.parse("{-0.28 36.06}");
		
		// Mombasa 4°05'S 39°65'E
		locMombasa = Location.parse("{-4.05 39.65}");
	}
	
}
