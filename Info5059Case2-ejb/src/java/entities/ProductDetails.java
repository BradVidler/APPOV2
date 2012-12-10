package entities;

import java.io.Serializable;

/**
 * ProductDetails - Container class that serializes product information traveling 
 * between War and Jar 
 */
public class ProductDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String prodcd;
	private int vendorno;
	private String vensku;
	private String prodname;
	private double costprice;
	private double msrp;
	private int rop;
	private int eoq;
	private int qoh;
	private int qoo;
        private byte[] qrcode;

        /** Creates a new instance of ProductDetails */
        public ProductDetails() {}

	public String getProdcd() { return prodcd; }
	public void setProdcd(String inValue) { prodcd = inValue;}
	public int getVendorno() { return vendorno;}
	public void setVendorno(int inValue) { vendorno = inValue;}
	public String getVensku() { return vensku; }
	public void setVensku(String inValue) { vensku = inValue;}
	public String getProdname() { return prodname; }
	public void setProdname(String inValue) { prodname = inValue; }
	public double getCostprice() { return costprice; }
	public void setCostprice(double inValue) { costprice = inValue;}
	public double getMsrp() { return msrp; }
	public void setMsrp(double inValue) {msrp = inValue;}
	public int getRop() { return rop; }
	public void setRop(int inValue) {rop = inValue;}
	public int getEoq() { return eoq; }
	public void setEoq(int inValue) {eoq = inValue;}
	public int getQoh() { return qoh; }
	public void setQoh(int inValue) {qoh = inValue;}
	public int getQoo() { return qoo; }
	public void setQoo(int inValue) {qoo = inValue;}
	public byte[] getQrcode() { return qrcode; }
	public void setQrcode(byte[] inValue) {qrcode = inValue;}
}