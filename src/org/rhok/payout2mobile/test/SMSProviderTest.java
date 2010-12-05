package org.rhok.payout2mobile.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.*;
import org.rhok.payout2mobile.ProtocolProviders.SMSProtocolProvider;
import org.rhok.payout2mobile.controllers.CC;
import org.rhok.payout2mobile.controllers.IdentityController;
import org.rhok.payout2mobile.controllers.PolicyController;
import org.rhok.payout2mobile.model.*;
import org.rhok.payout2mobile.policyproviders.ExactQuoteProvider;
import org.rhok.payout2mobile.policyproviders.RandomQuoteProvider;
import static org.junit.Assert.*;

public class SMSProviderTest extends GoogleDataTest {
	
	
	public static final String CUSTOMER_LOCATION = "{43.40 79.24}";
	public static final String CUSTOMER_PHONE = "+1 555-123-4567";
	public static final String VENDOR_PHONE = "+1 222-123-4567";
	public static final String VENDOR_NAME = "Vendor Jack";
	public static final double PREMIUM = 0.249;
	public static final String STATION_PHONE = "+1 555-123-7777";
	public static final double EPSILON = 0.0001;
	
	@Before
	public void setUp() {
		// Initialize the Vendor
		IdentityController ctl = new IdentityController();
		ctl.create(system, VENDOR_PHONE, VENDOR_NAME, IdentityType.Vendor);
	}
	
	@Test
	public void testQuote() throws ParseException {
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
		
		// but the customer should have been created
		Identity customer = CC.get().identity().find(CUSTOMER_PHONE);
		assertNotNull(customer);
		
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
	public void testPurchase() throws ParseException {
		PolicyController ctl = new PolicyController();
		SMSTest p = new SMSTest(ctl);
		
		
		// create the quote provider and setup their identity
		ExactQuoteProvider eqp = new ExactQuoteProvider(PREMIUM);
		CC.get().identity().create(system, ExactQuoteProvider.PROVIDER_IDENTITY, ExactQuoteProvider.PROVIDER_NAME, IdentityType.InsuranceProvider);
		
		// ensure the identity was setup correctly
		Identity eqpi = eqp.getIdentity();
		assertNotNull(eqpi);
		assertEquals(ExactQuoteProvider.PROVIDER_NAME, eqpi.getName());
		assertEquals(ExactQuoteProvider.PROVIDER_IDENTITY, eqpi.getPhoneNumber());
		assertEquals(IdentityType.InsuranceProvider, eqpi.getType());
		
		ctl.addProvider(eqp);
		
		// generate a message of the format:
		// quote, <Customer.Phone>, <Location>, <Qty> <Product>
		String quoteMessage = String.format("quote, %s, %s, %d %s",
					CUSTOMER_PHONE, Location.parse(CUSTOMER_LOCATION), 1, "Corn");
		
		// issue the quote request
		p.parseMessage(VENDOR_PHONE, quoteMessage);
		
		// check the quote response message
		assertEquals(VENDOR_PHONE, p.getLastRecipient());
		String response = String.format("premium: %.4f customer: %s", PREMIUM, CUSTOMER_PHONE);
		assertEquals(response, p.getLastMessage());
		
		// issue the purchase message
		String purchaseMessage = "purchase";
		p.parseMessage(VENDOR_PHONE, purchaseMessage);
		
		
		// check the purchase response
		assertEquals(CUSTOMER_PHONE, p.getLastRecipient());
		String confirmMessage = String.format("coverage #: 4017 exp: May 1, 2011 No Description insured: 1 Corn");
		assertEquals(confirmMessage, p.getLastMessage());
	}
	
	@Test
	public void testBogusPurchase() throws ParseException {
		PolicyController ctl = new PolicyController();
		SMSTest p = new SMSTest(ctl);
		
		// issue the purchase message (there hasn't been any quote)
		String purchaseMessage = "purchase";
		p.parseMessage(VENDOR_PHONE, purchaseMessage);
		
		// check the response
		assertEquals(VENDOR_PHONE, p.getLastRecipient());
		String confirmMessage = "Policy Not Purchased";
		assertEquals(confirmMessage, p.getLastMessage());
	}
	
	@Test(expected = ParseException.class)
	public void testBogusMessage() throws ParseException {
		PolicyController ctl = new PolicyController();
		SMSTest p = new SMSTest(ctl);
		
		String message = "bogus";
		p.parseMessage(STATION_PHONE, message);
	}
	
	@Test
	public void testMeasurement() throws ParseException {
		PolicyController ctl = new PolicyController();
		SMSTest p = new SMSTest(ctl);
		
		// create the measurement station
		CC.get().identity().create(system, STATION_PHONE, "Measurement Station", IdentityType.MeasurementStation);
		
		String message = "measurement, {12.45 45.67}, wind 22.5 m/s";
		p.parseMessage(STATION_PHONE, message);
		
		message = "measurement, {12.45 45.67}, rainfall 22.5 mm, wind 4.5 m/s";
		p.parseMessage(STATION_PHONE, message);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MeasurementStatisticsCB cbr = new MeasurementStatisticsCB();
		CC.get().measurement().iterate("rainfall", sdf.parse("2010-01-01"), sdf.parse("2011-01-01"), cbr);
		
		assertEquals(1, cbr.getCount());
		assertEquals(22.5, cbr.getMaximum(), EPSILON);
		
		MeasurementStatisticsCB cbw = new MeasurementStatisticsCB();
		CC.get().measurement().iterate("wind", sdf.parse("2010-01-01"), sdf.parse("2011-01-01"), cbw);
		assertEquals(2, cbw.getCount());
		assertEquals(22.5, cbw.getMaximum(), EPSILON);
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
