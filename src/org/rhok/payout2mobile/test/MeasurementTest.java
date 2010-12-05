package org.rhok.payout2mobile.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.rhok.payout2mobile.controllers.CC;
import org.rhok.payout2mobile.model.*;

import static org.junit.Assert.*;

public class MeasurementTest extends GoogleDataTest {

	
	@Test
	public void testTrackMeasurements() throws ParseException {
		double epsilon = 0.0001;
		Identity ms1, ms2;
		Location l1, l2;
		
		// setup the first measurement station
		l1 = Location.parse("{-1.16 36.48}"); // Nairobi 1°16'S 36°48'E
		
		// track a couple of measurements
		ms1 = CC.get().identity().create(system, "MS1", "MS1", IdentityType.MeasurementStation);
		CC.get().measurement().create(ms1, l1, "rainfall", 0.05, "mm");
		CC.get().measurement().create(ms1, l1, "wind", 0.05, "m/s");
		
		// setup the second measurement station
		l2 = Location.parse("{0.28 36.06}"); // Nakuru 0°28'S 36°06'E
		
		// track a couple of measurements
		ms2 = CC.get().identity().create(system, "MS2", "MS2", IdentityType.MeasurementStation);
		CC.get().measurement().create(ms2, l2, "rainfall", 0.08, "mm");
		CC.get().measurement().create(ms2, l2, "wind", 0.08, "m/s");
		
		
		// Mombasa 4°05'S 39°65'E
		
		
		// Get the total rainfall
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		double totalRainfall = 0;
		MeasurementStatisticsCB cb = new MeasurementStatisticsCB();
		CC.get().measurement().iterate("rainfall", sdf.parse("2010-01-01"), sdf.parse("2011-12-31"), cb);
		assertEquals(2, cb.getCount());
		assertEquals(0.13, cb.getSum(), 0.0001);
		
		
	}
}
