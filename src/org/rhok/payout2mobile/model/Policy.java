package org.rhok.payout2mobile.model;

import java.util.Date;

public class Policy {

	private Quote quote;
	private Date expiry;
	private String policyId;
	private Identity customer;
	private String description;
	
	public Quote getQuote() { return quote;	}
	public Identity getCustomer() { return customer; }
	public Date getExpiry() { return expiry; }
	public String getPolicyId() { return policyId; }
	public String getDescription() { return description; }
	
	public Policy(Quote pQuote, Identity pCustomer) {
		quote = pQuote;
		customer = pCustomer;
	}
	
	
	
}
