package entities;

import java.io.Serializable;
import java.util.Date;

/**
 * PurchaseOrderDetails - Container class that serializes purchase order information traveling 
 * between War and Jar 
 */
public class PurchaseOrderDetails implements Serializable {

	private static final long serialVersionUID = 1L;
        private int pono;
        private int vendorno;
        private double amount;
        private Date podate;

        /** Creates a new instance of PurchaseOrderDetails */
        public PurchaseOrderDetails() {}

	public int getPono() { return pono; }
	public void setPono(int inValue) { pono = inValue;}
	public int getVendorno() { return vendorno;}
	public void setVendorno(int inValue) { vendorno = inValue;}
	public double getAmount() { return amount; }
	public void setAmount(double inValue) { amount = inValue;}
        public Date getPodate() { return podate; }
	public void setDate(Date inValue) { podate = inValue;}
}