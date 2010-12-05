package org.rhok.payout2mobile.policyproviders;

import org.rhok.payout2mobile.model.*;

public interface PolicyProvider {
	
	public Identity getIdentity();
	
	public Quote getQuote(PolicyDetails details);
	
	public Policy purchase(Identity who, Quote quote);
}
