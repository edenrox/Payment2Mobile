package org.rhok.payout2mobile.model;

import java.util.Date;

public class Quote {

	private PolicyDetails details;
	private Identity provider;
	private Date created;
	private Date policyExpiry;
	private String referenceNumber;
	private double premium;
	
	public PolicyDetails getDetails() { return details; }
	public Identity getProvider() { return provider; }
	public String getReferenceNumber() { return referenceNumber; }
	public Date getCreated() { return created; }
	public double getPremium() { return premium; }
	public Date getPolicyExpiry() { return policyExpiry; }
	
	public Quote(PolicyDetails pDetails, Identity pProvider, String pReferenceNumber, double pPremium, Date pPolicyExpiry) {
		details = pDetails;
		provider = pProvider;
		referenceNumber = pReferenceNumber;
		premium = pPremium;
		created = new Date();
		policyExpiry = pPolicyExpiry;
	}
}
