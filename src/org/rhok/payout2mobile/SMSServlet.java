/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rhok.payout2mobile.ProtocolProviders.SMSProtocolProvider;

@SuppressWarnings("serial")
public class SMSServlet extends HttpServlet {
	
	private SMSProtocolProvider provider;
	private Vector<SMSMessage> messages;
	
	public SMSServlet() {
		messages = new Vector<SMSMessage>();
		provider = new SMSProtocolProvider() {
			public void sendMessage(String to, String message) {
				messages.add(new SMSMessage(to, message));
			}
		};
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		// set the content type
		resp.setContentType("text/json");
		
		PrintWriter pw = resp.getWriter();
		
		pw.write("[");
		int count = 0;
		for(SMSMessage item : messages) {
			if (count > 0) {
				pw.write(", ");
			}
			pw.write(item.toJSON());
			count++;
		}
		pw.write("]");
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		// set the content type
		resp.setContentType("text/json");
		String content = null;
		try {
			provider.parseMessage(req.getParameter("from"), req.getParameter("content"));
			content = "{\"status\": \"ok\"}";
		} catch (ParseException ex) {
			content = "{\"status\": \"error\", \"message\": \"Parse error while processing message\"}";
		}
		resp.getWriter().print(content);
	}
}
