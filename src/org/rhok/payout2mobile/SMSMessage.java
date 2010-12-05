package org.rhok.payout2mobile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSMessage {
	private Date created;
	private String to;
	private String message;
	
	public Date getDate() { return created; }
	public String getTo() { return to; }
	public String getMessage() { return message; }
	
	public SMSMessage(String pTo, String pMessage) {
		to = pTo;
		message = pMessage;
		created = new Date();
	}
	
	public String toJSON() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return String.format("{\"created\": \"%s\", \"to\": \"%s\", \"message\": \"%s\"}",
					sdf.format(created), to, message);	
	}
}
