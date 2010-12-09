/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.ProtocolProviders;

import org.rhok.payout2mobile.model.*;

public interface ProtocolProvider extends Runnable {
	
	public void confirmCoverage(Policy item);
	
}
