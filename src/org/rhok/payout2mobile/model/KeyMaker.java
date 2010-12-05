package org.rhok.payout2mobile.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyMaker {

	public static Key key(Class<?> klass, String id){
		return KeyFactory.createKey(klass.getSimpleName(), id);
	}
}
