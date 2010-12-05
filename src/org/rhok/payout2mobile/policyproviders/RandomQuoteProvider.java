package org.rhok.payout2mobile.policyproviders;

import java.util.Calendar;

import org.rhok.payout2mobile.controllers.CC;
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
		return CC.get().identity().find(PROVIDER_IDENTITY);
	}
	
	public Quote getQuote(PolicyDetails details) {
		double premium = Math.random() * (premiumMax - premiumMin) + premiumMin;
		String referenceNumber = String.format("Policy %d", (long) Math.floor(Math.random() * Long.MAX_VALUE) );
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 5);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return new Quote(details, getIdentity(), referenceNumber, premium, cal.getTime());
	}

	@Override
	public void purchase(Policy policy) {
		// noop
	}

}
