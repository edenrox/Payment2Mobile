package org.rhok.payout2mobile.controllers;

import java.util.List;
import java.util.Vector;

import javax.jdo.PersistenceManager;

import org.rhok.payout2mobile.model.*;
import org.rhok.payout2mobile.policyproviders.PolicyProvider;

public class PolicyController {
	
	private Vector<PolicyProvider> m_providers;
	
	public PolicyController() {
		m_providers = new Vector<PolicyProvider>();
	}
	
	public void addProvider(PolicyProvider item) {
		m_providers.add(item);
	}

	private List<PolicyProvider> getPolicyProviders() {
		return m_providers;
	}
	
	public Quote getBestQuote(Identity vendor, Identity customer, PolicyDetails details) {
		Quote bestQuote = null;		
		
		// Loop through the insurance providers
		for(PolicyProvider provider : getPolicyProviders()) {
			Quote quote = provider.getQuote(details);
			if (quote != null) { 
				if (bestQuote == null) {
					bestQuote = quote;
				} else if (bestQuote.getPremium() > quote.getPremium() ) {
					bestQuote = quote;
				}
			}
		}
		
		// save the quote for later lookup
		if (bestQuote != null) {
			persistLastQuote(vendor, customer, bestQuote);
		}
		
		return bestQuote;
	}
	
	public Policy purchasePolicy(Identity customer, Quote quote) {
		
		if (customer == null) {
			throw new NullPointerException("Error, customer cannot be null");
		}
		if (quote == null) {
			throw new NullPointerException("Error, quote cannot be null");
		}
		
		// create the policy
		Policy policy = new Policy(quote, customer);
		
		// find the policy provider
		PolicyProvider quoteProvider = null;
		for (PolicyProvider provider : getPolicyProviders()) {
			if (quote.getProvider().equals(provider.getIdentity())) {
				quoteProvider = provider;
			}
		}
		
		// notify the provider of this purchase
		if (quoteProvider != null) {
			quoteProvider.purchase(policy);
		}
		
		// save the policy
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(policy);
		} finally {
			pm.close();
		}
		
		return policy;
	}
	
	
	
	protected void persistLastQuote(Identity vendor, Identity customer, Quote quote) {
		lastQuote = quote;
		lastCustomer = customer;
	}
	
	private Quote lastQuote;
	private Identity lastCustomer;
	
	public Quote getLastQuote(Identity vendor) {
		return lastQuote;
	}
	
	
	public Identity getLastCustomer(Identity vendor) {
		return lastCustomer;
	}
	
}
