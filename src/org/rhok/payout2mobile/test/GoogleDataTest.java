package org.rhok.payout2mobile.test;

import org.junit.After;
import org.junit.Before;
import org.rhok.payout2mobile.model.Identity;
import org.rhok.payout2mobile.model.IdentityType;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GoogleDataTest {

	protected final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	protected static final String SYSTEM_PHONE = "SYSTEM";
	protected Identity system;
	
	@Before
	public void setUpDB() {
		helper.setUp();
		system = new Identity(null, SYSTEM_PHONE, "System", IdentityType.System);
	}
	
	@After
	public void tearDownDB() {
		helper.tearDown();
	}
}
