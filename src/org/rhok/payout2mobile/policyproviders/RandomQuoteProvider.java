package org.rhok.payout2mobile.policyproviders;

import org.rhok.payout2mobile.controllers.IdentityController;
import org.rhok.payout2mobile.model.*;

public class RandomQuoteProvider implements PolicyProvider {

	public static final String PROVIDER_IDENTITY = "+1 455-123-4567";
	
	private double premiumMin;
	private double premiumMax;
	
	public RandomQuoteProvider(double pPremiumMin, double pPremiumMax) {
		premiumMin = pPremiumMin;
		premiumMax = pPremiumMax;
	}
	
	public Identity getIdentity() {
		return (new IdentityController()).find(PROVIDER_IDENTITY);
	}
	
	public Quote getQuote(PolicyDetails details) {
		double premium = Math.random() * (premiumMax - premiumMin) + premiumMin;
		String referenceNumber = String.format("Policy %d", (long) Math.floor(Math.random() * Long.MAX_VALUE) );
		
		return new Quote(details, getIdentity(), referenceNumber, premium);
	}

	@Override
	public Policy purchase(Identity who, Quote quote) {
		Policy item = new Policy(quote, who);
		return item;
	}

}
