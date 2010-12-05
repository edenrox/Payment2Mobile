package org.rhok.payout2mobile.controllers;

import java.util.Date;

import org.rhok.payout2mobile.model.*;

public class ClaimController extends AppController {
	
	public boolean can(Identity who, String action) {
		if (action.startsWith("report-")) {
			if (who.getType() == IdentityType.NGO) {
				return true;
			} else if (who.getType() == IdentityType.InsuranceProvider) {
				return (action == generateAction(who));
			}
		} else if (action.startsWith("evaluate")) {
			return (who.getType() == IdentityType.NGO);
		}
		return false;
	}
	
	private String generateAction(Identity who) {
		return "report-" + who.getPhoneNumber().toString();
	}

	public ClaimsReport generateClaimsReport(Identity who, Date start, Date end, Identity insuranceProvider) {
		ensureCan(who, generateAction(insuranceProvider));
		
		ClaimsReport item = new ClaimsReport(start, end);
		
		
		return item;
	}
	
	public void evaluateClaims(Identity who, Date start, Date end) {
		ensureCan(who, "evaluate");
		
		
		
		
	}
	
}
