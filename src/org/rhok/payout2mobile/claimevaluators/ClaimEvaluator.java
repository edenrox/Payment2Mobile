/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.claimevaluators;

import org.rhok.payout2mobile.model.*;

public interface ClaimEvaluator {
	
	public Claim evaluatePolicy(Policy item);
	
}
