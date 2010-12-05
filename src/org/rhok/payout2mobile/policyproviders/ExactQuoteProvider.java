package org.rhok.payout2mobile.policyproviders;

import org.rhok.payout2mobile.controllers.IdentityController;
import org.rhok.payout2mobile.model.Identity;
import org.rhok.payout2mobile.model.Policy;
import org.rhok.payout2mobile.model.PolicyDetails;
import org.rhok.payout2mobile.model.Quote;

public class ExactQuoteProvider  implements PolicyProvider {

	private double premium;
	public static final String PROVIDER_IDENTITY = "+1 455-123-4568";
	
	public ExactQuoteProvider(double pPremium) {
		premium = pPremium;
	}
	
	public Identity getIdentity() {
		return (new IdentityController()).find(PROVIDER_IDENTITY);
	}
	
	public Quote getQuote(PolicyDetails details) {
		String referenceNumber = String.format("Policy %.4f", premium);
		
		return new Quote(details, getIdentity(), referenceNumber, premium);
	}

	@Override
	public Policy purchase(Identity who, Quote quote) {
		Policy item = new Policy(quote, who);
		return item;
	}

}
