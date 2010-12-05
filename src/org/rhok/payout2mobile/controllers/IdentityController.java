package org.rhok.payout2mobile.controllers;

import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.rhok.payout2mobile.model.*;
import com.google.appengine.api.datastore.Key;

public class IdentityController extends AppController {
	
	public Identity create(Identity actor, String identifier, String name, IdentityType type) {
		
		// Validate the arguments
		//if (actor == null) {
		//	throw new NullPointerException("Actor cannot be null");
		//}
		if ((identifier == null) || (identifier.length() < 1)) {
			throw new NullPointerException("Identifier cannot be null or empty");
		}
		String action = String.format("create-%s", type.toString());
		ensureCan(actor, action);
		if (find(identifier) != null) {
			throw new RuntimeException("Identifier not unique");
		}
		
		// create the item
		Identity item = new Identity(actor, identifier, name, type);
		
		// save the item
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			item = pm.makePersistent(item);
		} finally {
			pm.close();
		}

		return item;
	}
	
	public Identity find(String pPhoneNumber) {
		Identity rv = null;
		
		// try to find the item in the DB
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			rv = (Identity) pm.getObjectById(Identity.class, pPhoneNumber);
		} catch (JDOObjectNotFoundException ex) {
			rv = null;
		} finally {
			pm.close();
		}
		
		return rv;
	}
	
	public List<Identity> list(IdentityType type) {
		List<Identity> items = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(Identity.class);
		q.setFilter("type == typeParam");
		q.setOrdering("name");
		q.declareParameters("int typeParam");
		
		try {
			items = (List<Identity>) q.execute(type.ordinal());
		} finally {
			q.closeAll();
		}
		
		return items;
	}
	
}
