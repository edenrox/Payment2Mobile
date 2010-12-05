package org.rhok.payout2mobile.test;

import javax.jdo.PersistenceManager;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rhok.payout2mobile.controllers.IdentityController;
import org.rhok.payout2mobile.model.Identity;
import org.rhok.payout2mobile.model.IdentityType;
import org.rhok.payout2mobile.model.PMF;

import static org.junit.Assert.*;

public class IdentityTest extends GoogleDataTest {


	
	@Test
	public void testCreateAndProperties() {
		Identity item = new Identity(null, "+1 555-234-2456", "Jacko Flante", IdentityType.Customer);
		
		//assertEquals(null, item.getParent());
		assertEquals("+1 555-234-2456", item.getPhoneNumber());
		assertEquals("Jacko Flante", item.getName());
		assertEquals(IdentityType.Customer, item.getType());
		
		Identity item2 = new Identity(item, "+1 555-098-2456", "Mark Smith", IdentityType.InsuranceProvider);
		
		assertEquals("+1 555-098-2456", item2.getPhoneNumber());
		assertEquals("Mark Smith", item2.getName());
		assertEquals(IdentityType.InsuranceProvider, item2.getType());
	}
	
	@Test
	public void testController() {
		Identity item = null;
		String phoneNumber = "+1 555-234-2456";
		int count = 0;
		
		// setup a controller
		IdentityController ctl = new IdentityController();
		
		count = ctl.list(IdentityType.Customer).size();
		
		// create the item
		Identity parent = new Identity(null, "-1", "Parent", IdentityType.System);
		item = ctl.create(parent, phoneNumber, "Jacko Flante", IdentityType.Customer);
		
		// ensure the item was created correctly
		assertNotNull(item);
		assertEquals(phoneNumber, item.getPhoneNumber());
		assertEquals("Jacko Flante", item.getName());
		assertEquals(IdentityType.Customer, item.getType());
		
		// ensure we can find the item also
		Identity item2 = ctl.find(phoneNumber);
		
		// ensure the item was loaded correctly
		assertNotNull(item2);
		assertEquals(item.getPhoneNumber(), item2.getPhoneNumber());
		assertEquals(item.getCreated(), item2.getCreated());
		
		// ensure the count went up
		int count2 = ctl.list(IdentityType.Customer).size();
		assertEquals(count + 1, count2);
	}
}
