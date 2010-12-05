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
	private String phoneNumber;
	
	//@Persistent
	private String parent;
	
	@Persistent
	private String name;
	
	@Persistent
	private int type;
	
	@Persistent
	private Date created;
	
	public IdentityType getType() {
		for(IdentityType item : IdentityType.values()) {
			if (item.ordinal() == type) {
				return item;
			}
		}
		return null;
	}
	
	public String getPhoneNumber() { return phoneNumber; }
	public Date getCreated() { return created; }
	public String getName() { return name; }
	
	public Identity(Identity pParent, String pPhoneNumber, String pName, IdentityType pType) {
		if (pParent != null) {
			parent = pParent.getPhoneNumber();
		}
		phoneNumber = pPhoneNumber;
		name = pName;
		type = pType.ordinal();
		created = new Date();
	}
	
	
}
