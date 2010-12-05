package org.rhok.payout2mobile.test;

import org.junit.Test;
import org.rhok.payout2mobile.ProtocolProviders.SMSProtocolProvider;
import org.rhok.payout2mobile.controllers.PolicyController;
import org.rhok.payout2mobile.model.Location;
import org.rhok.payout2mobile.policyproviders.ExactQuoteProvider;
import org.rhok.payout2mobile.policyproviders.RandomQuoteProvider;
import static org.junit.Assert.*;

public class SMSProviderTest extends GoogleDataTest {
	
	
	public static final String CUSTOMER_LOCATION = "{43.40 79.24}";
	public static final String CUSTOMER_PHONE = "+1 555-123-4567";
	public static final String VENDOR_PHONE = "+1 222-123-4567";
	public static final double PREMIUM = 0.249;
	
	@Test
	public void testQuote() {
		PolicyController ctl = new PolicyController();
		SMSTest p = new SMSTest(ctl);
		
		
		// generate a message of the format:
		// quote, <Customer.Phone>, <Location>, <Qty> <Product>
		String quoteMessage = String.format("quote, %s, %s, %d %s",
					CUSTOMER_PHONE, Location.parse(CUSTOMER_LOCATION), 1, "Corn");
		
		// issue the request
		p.parseMessage(VENDOR_PHONE, quoteMessage);
		
		// since there are no quote providers, this should return empty
		assertEquals(VENDOR_PHONE, p.getLastRecipient());
		assertEquals("No insurance available.", p.getLastMessage());
		
		
		// add a quote provider
		ctl.addProvider(new ExactQuoteProvider(PREMIUM));
		
		// now we should get an accurate quote response
		p.parseMessage(VENDOR_PHONE, quoteMessage);
		
		// check the message
		assertEquals(VENDOR_PHONE, p.getLastRecipient());
		String response = String.format("premium: %.4f customer: %s", PREMIUM, CUSTOMER_PHONE);
		assertEquals(response, p.getLastMessage());
		
		// add a second and third provider
		ctl.addProvider(new ExactQuoteProvider(0.11));
		ctl.addProvider(new RandomQuoteProvider(0.27, 0.5));
		
		// now we should get the lowest premium as a result
		p.parseMessage(VENDOR_PHONE, quoteMessage);
		
		// check the message
		assertEquals(VENDOR_PHONE, p.getLastRecipient());
		response = String.format("premium: %.4f customer: %s", 0.11, CUSTOMER_PHONE);
		assertEquals(response, p.getLastMessage());
	}
	
	@Test
	public void testPurchase() {
		PolicyController ctl = new PolicyController();
		SMSTest p = new SMSTest(ctl);
		ctl.addProvider(new ExactQuoteProvider(PREMIUM));
		
		// generate a message of the format:
		// quote, <Customer.Phone>, <Location>, <Qty> <Product>
		String quoteMessage = String.format("quote, %s, %s, %d %s",
					CUSTOMER_PHONE, Location.parse(CUSTOMER_LOCATION), 1, "Corn");
		
		// issue the request
		p.parseMessage(VENDOR_PHONE, quoteMessage);
		
		// check the message
		assertEquals(VENDOR_PHONE, p.getLastRecipient());
		String response = String.format("premium: %.4f customer: %s", PREMIUM, CUSTOMER_PHONE);
		assertEquals(response, p.getLastMessage());
		
		// issue the purchase message
		String purchaseMessage = "purchase";
		p.parseMessage(VENDOR_PHONE, purchaseMessage);
		
		assertEquals(CUSTOMER_PHONE, p.getLastRecipient());
		String confirmMessage = String.format("coverage Policy %.4f %s 1 Corn", 
				PREMIUM, "");
		assertEquals(confirmMessage, p.getLastMessage());
	}
	
	
	/**
	 * This class just overrides the SMS Protocol provider 
	 * to store the last message rather than actually
	 * sending it (allowing us to test it).
	 */
	class SMSTest extends SMSProtocolProvider {
		private String lastRecipient;
		private String lastMessage;
		private PolicyController ctl;
		
		public String getLastRecipient() { return lastRecipient; }
		public String getLastMessage() { return lastMessage; }
		
		public SMSTest(PolicyController pCtl) {
			ctl = pCtl;
		}
		
		protected PolicyController policyController() { return ctl; }
		
		public void sendMessage(String toPhone, String message) {
			lastRecipient = toPhone;
			lastMessage = message;
		}
		
	}
	
}
