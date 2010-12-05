package org.rhok.payout2mobile.controllers;

import javax.jdo.PersistenceManager;
import org.rhok.payout2mobile.model.*;
import com.google.appengine.api.datastore.Key;

public class IdentityController extends AppController {
	
	public Identity create(Identity actor, String identifier, String name, IdentityType type) {
		
		// Validate the arguments
		if (actor == null) {
			throw new NullPointerException("Actor cannot be null");
		}
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
			pm.makePersistent(item);
		} finally {
			pm.close();
		}

		return item;
	}
	
	public Identity find(String identifier) {
		Identity rv = null;
		
		// try to find the item in the DB
		Key key = KeyMaker.key(Identity.class, identifier);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			rv = (Identity) pm.getObjectById(key);
		} finally {
			pm.close();
		}
		
		return rv;
	}
	
}
