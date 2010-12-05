package org.rhok.payout2mobile.model;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Identity {

	@PrimaryKey
	@Persistent
	private Key key;
	
	@Persistent
	private Key parent;
	
	@Persistent
	private String name;
	
	@Persistent
	private IdentityType type;
	
	@Persistent
	private Date created;
	
	
	public Key getKey() {
		return key;
	}
	
	public IdentityType getType() {
		return type;
	}
	
	public String getPhoneNumber() {
		return "";
	}
	
	public Identity(Identity pParent, String identity, String pName, IdentityType pType) {
		parent = pParent.getKey();
		key = KeyFactory.createKey(Identity.class.getSimpleName(), identity);
		name = pName;
		type = pType;
	}
	
	
}
