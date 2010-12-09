/***************************************
 * DO NOT REMOVE
 * License: BSD License (see LICENSE in the root of this project)
 * Original Author: Ian Hopkins
 */
package org.rhok.payout2mobile.model;

public class ProductQuantity {
	private int quantity;
	private String product;
	
	public int getQuantity() { return quantity; }
	public String getProduct() { return product; }
	
	public ProductQuantity(int pQuantity, String pProduct) {
		quantity = pQuantity;
		product = pProduct;
	}
}
