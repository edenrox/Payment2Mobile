package org.rhok.payout2mobile.model;

import java.util.Date;
import javax.jdo.annotations.*;

@PersistenceCapable
public class Policy {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	private Quote quote;
	@Persistent
	private Date expiry;
	@Persistent
	private String policyId;
	@NotPersistent
	private Identity customer;
	@Persistent
	private String description;
	
	public long getId() { return id.longValue(); }
	public Quote getQuote() { return quote;	}
	public Identity getCustomer() { return customer; }
	public Date getExpiry() { return expiry; }
	public String getPolicyId() { return policyId; }
	public String getDescription() { return description; }
	
	public void setPolicyId(String value) { policyId = value; }
	
	public Policy(Quote pQuote, Identity pCustomer) {
		id = new Long(-1);
		quote = pQuote;
		customer = pCustomer;
		expiry = pQuote.getPolicyExpiry();
		description = "No Description";
	}
	
	
	
}
