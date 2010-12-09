/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Payout2MobileServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		// set the content type
		resp.setContentType("text/html");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		// set the content type
		resp.setContentType("text/html");
	}
}
