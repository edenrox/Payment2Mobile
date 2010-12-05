package org.rhok.payout2mobile.policyproviders;

import org.rhok.payout2mobile.controllers.IdentityController;
import org.rhok.payout2mobile.model.*;

public class RandomQuoteProvider implements PolicyProvider {

	public static final double PREMIUM_MAX = 0.35;
	public static final double PREMIUM_MIN = 0.03;
	public static final String PROVIDER_IDENTITY = "+1 555-123-4567";
	
	public Identity getIdentity() {
		return (new IdentityController()).find(PROVIDER_IDENTITY);
	}
	
	public Quote getQuote(PolicyDetails details) {
		double premium = Math.random() * (PREMIUM_MAX - PREMIUM_MIN) + PREMIUM_MIN;
		String referenceNumber = String.format("Policy %d", (long) Math.floor(Math.random() * Long.MAX_VALUE) );
		
		return new Quote(details, getIdentity(), referenceNumber, premium);
	}

	@Override
	public Policy purchase(Identity who, Quote quote) {
		Policy item = new Policy(quote, who);
		return item;
	}

}
