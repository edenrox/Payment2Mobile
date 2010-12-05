package org.rhok.payout2mobile.controllers;

import org.rhok.payout2mobile.model.Identity;

public class AppController {

	protected void ensureCan(Identity who, String action) {
		if (!can(who, action)) {
			throw new RuntimeException(String.format("The user %s cannot perform %s.", who.toString(), action));
		}
	}
	
	protected boolean can(Identity who, String action) {
		// by default anyone can do anything, will be over-ridden in controllers
		return true;
	}
}
