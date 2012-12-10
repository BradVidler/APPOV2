package entities;

import java.io.Serializable;

/**
 * PurchaseOrderLineItemDetails - Container class that serializes purchase order information traveling 
 * between War and Jar 
 */
public class PurchaseOrderLineItemDetails implements Serializable {

	private static final long serialVersionUID = 1L;
        private int lineid;
        private int pono;
        private String prodcd;
        private double price;
        private int qty;

        /** Creates a new instance of PurchaseOrderLineItemDetails */
        public PurchaseOrderLineItemDetails() {}

	public int getLineid() { return lineid; }
	public void setLineid(int inValue) { lineid = inValue;}
	public int getPono() { return pono;}
	public void setPono(int inValue) { pono = inValue;}
	public String getProdcd() { return prodcd; }
	public void setProdcd(String inValue) { prodcd = inValue;}
        public double getPrice() { return price; }
	public void setPrice(double inValue) { price = inValue;}
        public int getQty() { return qty; }
	public void setQty(int inValue) { qty = inValue;}
}