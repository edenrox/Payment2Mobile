/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.policyproviders;

import java.util.Calendar;
import org.rhok.payout2mobile.controllers.CC;
import org.rhok.payout2mobile.model.Identity;
import org.rhok.payout2mobile.model.Policy;
import org.rhok.payout2mobile.model.PolicyDetails;
import org.rhok.payout2mobile.model.Quote;

public class ExactQuoteProvider  implements PolicyProvider {

	private double premium;
	public static final String PROVIDER_IDENTITY = "+1 555-240-1112";
	public static final String PROVIDER_NAME = "Exact Quote Provider";
	
	public ExactQuoteProvider(double pPremium) {
		premium = pPremium;
	}
	
	public Identity getIdentity() {
		return CC.get().identity().find(PROVIDER_IDENTITY);
	}
	
	public Quote getQuote(PolicyDetails details) {
		String referenceNumber = String.format("Policy %.4f", premium);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011); // May 1, 2011 (remember months are off by 1, oh Java)
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return new Quote(details, getIdentity(), referenceNumber, premium, cal.getTime());
	}

	@Override
	public void purchase(Policy policy) {
		// noop
		policy.setPolicyId("4017");
	}

}
