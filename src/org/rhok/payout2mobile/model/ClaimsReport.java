package org.rhok.payout2mobile.model;

import java.util.Date;

public class ClaimsReport {

	private Date start;
	private Date end;
	
	public Date getStart() { return start; }
	public Date getEnd() { return end; }
	
	public ClaimsReport(Date pStart, Date pEnd) {
		start = pStart;
		end = pEnd;
	}
}
