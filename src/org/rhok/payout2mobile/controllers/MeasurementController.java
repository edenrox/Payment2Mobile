/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.controllers;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.rhok.payout2mobile.model.*;

public class MeasurementController extends AppController {
	
	public Measurement create(Identity station, Location location, String metric, double magnitude, String units) {
		Measurement item = new Measurement(station, location, metric, magnitude, units);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(item);
		} finally {
			pm.close();
		}
		return item;
	}
	
	public void iterate(String metric, Date start, Date end, MeasurementCallback cb) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Measurement> items = null;
		try {
			Query query = pm.newQuery(Measurement.class);
			query.setFilter("metric == metricParam && created >= startDateParam && created < endDateParam");
			query.declareParameters("String metricParam, java.util.Date startDateParam, java.util.Date endDateParam");
			query.setOrdering("created");
			
			items = (List<Measurement>) query.execute(metric, start, end);
			for(Measurement item : items) {
				cb.callback(item);
			}
		} finally {
			pm.close();
		}
	}
}
