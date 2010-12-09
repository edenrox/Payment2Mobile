/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.model;

import javax.jdo.annotations.*;
import javax.persistence.Embeddable;

@PersistenceCapable
@Embeddable
public class Location {

	@Persistent
	private double lat;
	@Persistent 
	private double lng;
	
	public double getLatitude() { return lat; }
	
	public double getLongitude() { return lng; }
	
	public Location(double pLat, double pLng) {
		lat = pLat;
		lng = pLng;
	}
	
	public String toString() {
		return String.format("{%.3f %.3f}", lat, lng); 
	}
	
	public static Location parse(String str) {
		str = str.trim();
		
		// parse off the {}
		str = str.substring(1, str.length() - 2);
		
		// the parts of the location
		String parts[] = str.split(" ");
		
		return new Location(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
	}
}
