package org.rhok.payout2mobile.controllers;

import javax.jdo.PersistenceManager;

import org.rhok.payout2mobile.model.*;

public class MeasurementController extends AppController {
	
	public Measurement create(Identity who, String metric, double magnitude) {
		MeasurementStation station = CC.get().measurementStation().find(who);
		Measurement item = new Measurement(station, metric, magnitude);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(item);
		} finally {
			pm.close();
		}
		return item;
	}
}
