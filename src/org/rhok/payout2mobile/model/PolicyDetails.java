package org.rhok.payout2mobile.model;

import java.util.Vector;

public class PolicyDetails {

	private Location location;
	private Vector<ProductQuantity> products;
	
	public Location getLocation() { return location; }
	public Vector<ProductQuantity> getProducts() { return products; }
	
	public PolicyDetails(Location pLocation) {
		location = pLocation;
		products = new Vector<ProductQuantity>();
	}
	
}
