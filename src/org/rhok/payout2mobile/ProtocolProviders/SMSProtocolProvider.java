package org.rhok.payout2mobile.ProtocolProviders;

import java.text.SimpleDateFormat;
import java.util.Vector;

import org.rhok.payout2mobile.controllers.CC;
import org.rhok.payout2mobile.model.*;

public class SMSProtocolProvider
	implements ProtocolProvider {

	public SMSProtocolProvider() {
		// This is the constructor, here we need to setup handling SMS messages
		// when you receive a message, call "parseMessage"
		
	}
	
	public void sendMessage(String phoneTo, String message) {
		// needs to be implemented 
		// this should send a text to the phone number specified
		// with the message specified
	}
	
	public void parseMessage(String phoneFrom, String message) {
		String tokens[] = message.split(",");
		
		if (tokens[0].equals("quote")) {
			quote(phoneFrom, tokens);
		} else if (tokens[1].equals("purchase")) {
			purchase(phoneFrom, tokens);
		}
	}
	
	// quote, <Customer.Phone>, <Location>, <Qty> <Product> [, <Qty> <Product>...]
	public void quote(String phoneVendor, String[] tokens) {
		String phoneCustomer = tokens[1];
		PolicyDetails details = new PolicyDetails(Location.parse(tokens[2]));
		
		for(int i = 3; i < tokens.length; i++) {
			String parts[] = tokens[i].split(" ");
			details.getProducts().add(new ProductQuantity(Integer.parseInt(parts[0]), parts[1]));
		}
		
		// get the customer
		Identity who = CC.get().identity().find(phoneCustomer);
		if (who == null) {
			// create the customer
			Identity vendor = CC.get().identity().find(phoneVendor);
			who = CC.get().identity().create(vendor, phoneCustomer, "", IdentityType.Customer);
		}
		
		// now we need to pull a best quote
		Quote bestQuote = CC.get().policy().getBestQuote(who, details);
		
		// send the quote to the vendor
		if (bestQuote != null) {
			sendMessage(phoneVendor, quoteResponse(phoneCustomer, bestQuote));
		} else {
			sendMessage(phoneVendor, "No insurance available.");
		}
	}
	
	// premium: <premium> customer: <phoneCustomer>
	public String quoteResponse(String phoneCustomer, Quote quote) {
		return String.format("premium: %.4f customer: %s", 
						quote.getPremium(), phoneCustomer);
	}
	
	// purchase 
	public void purchase(String phoneVendor, String[] tokens) {
		
		// We need to find the vendor's identity
		Identity vendor = CC.get().identity().find(phoneVendor);
		
		// We need to find the last quote and customer
		Quote lastQuote = CC.get().policy().getLastQuote(vendor);
		Identity lastCustomer = CC.get().policy().getLastCustomer(vendor);
		String phoneCustomer = lastCustomer.getPhoneNumber();
		
		// purchase the quote
		Policy policy = null;// CC.get().policy().purchaseQuote(lastQuote);
		
		if (policy != null) {
			sendMessage(phoneCustomer, purchaseResponse(policy));
		} else {
			sendMessage(phoneVendor, "Policy Not Purchased");
			sendMessage(phoneCustomer, "Policy Not Purchased");
		}
	}
	
	// coverage <PolicyId> <Expiry> <Risk> <Qty><Product>[, <Qty><Product>]
	protected String purchaseResponse(Policy policy) {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		sb.append(String.format("coverage %s %s %s ", 
				policy.getPolicyId(), 
				sdf.format(policy.getExpiry()),
				policy.getDescription()
					));
		
		// Demeter is going to kill me for this line, LOL.
		for (ProductQuantity product : policy.getQuote().getDetails().getProducts()) {
			sb.append(String.format("%d%s", 
					product.getQuantity(), product.getProduct()));
		}
		
		return sb.toString();
	}

	public void run() {
		// noop
	}

	public void confirmCoverage(Policy policy) {
		sendMessage(policy.getCustomer().getPhoneNumber(), purchaseResponse(policy));
	}
	
}
