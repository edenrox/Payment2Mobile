/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.policyproviders;

import org.rhok.payout2mobile.model.*;

public interface PolicyProvider {
	
	public Identity getIdentity();
	
	public Quote getQuote(PolicyDetails details);
	
	public void purchase(Policy policy);
}
