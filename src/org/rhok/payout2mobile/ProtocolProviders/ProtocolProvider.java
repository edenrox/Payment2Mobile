package org.rhok.payout2mobile.ProtocolProviders;

import org.rhok.payout2mobile.model.*;

public interface ProtocolProvider extends Runnable {
	
	public void confirmCoverage(Policy item);
	
}
