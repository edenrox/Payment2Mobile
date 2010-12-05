package org.rhok.payout2mobile.claimevaluators;

import org.rhok.payout2mobile.model.*;

public interface ClaimEvaluator {
	
	public Claim evaluatePolicy(Policy item);
	
}
