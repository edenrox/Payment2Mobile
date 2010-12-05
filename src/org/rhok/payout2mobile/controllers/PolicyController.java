package org.rhok.payout2mobile.controllers;

import java.util.List;
import java.util.Vector;

import javax.jdo.PersistenceManager;

import org.rhok.payout2mobile.model.*;
import org.rhok.payout2mobile.policyproviders.PolicyProvider;

public class PolicyController {
	
	private List<PolicyProvider> m_providers;
	
	public void addProvider(PolicyProvider item) {
		m_providers.add(item);
	}

	private List<PolicyProvider> getPolicyProviders() {
		return m_providers;
	}
	
	public Quote getBestQuote(Identity who, PolicyDetails details) {
		Quote bestQuote = null;		
		
		// Loop through the insurance providers
		for(PolicyProvider provider : getPolicyProviders()) {
			Quote quote = null; //provider.getQuote(policyType, location);
			if (quote != null) { 
				if (bestQuote == null) {
					bestQuote = quote;
				} else if (bestQuote.getPremium() > quote.getPremium() ) {
					bestQuote = quote;
				}
			}
		}
		
		
		return bestQuote;
	}
	
	public Quote getLastQuote(Identity vendor) {
		return null;
	}
	
	
	public Identity getLastCustomer(Identity vendor) {
		return null;
	}
	
}
