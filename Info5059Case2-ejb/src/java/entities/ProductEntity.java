package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "ProductEntity.findByVendorno", query = "SELECT p FROM ProductEntity p WHERE p.vendorno = :vendorno"),
    @NamedQuery(name = "ProductEntity.findByVensku", query = "SELECT p FROM ProductEntity p WHERE p.vensku = :vensku"),
    @NamedQuery(name = "ProductEntity.findByProdcd", query = "SELECT p FROM ProductEntity p WHERE p.prodcd = :prodcd")
})
public class ProductEntity implements Serializable {
    @Id
    @Column(name = "prodcd", nullable = false)
    private String prodcd;
    @Column(name = "vendorno", nullable = false)
    private int vendorno;
    @Column(name = "vensku", nullable = false)
    private String vensku;
    @Column(name = "prodnam", nullable = false)
    private String prodnam;
    @Column(name = "costprice", nullable = false)
    private double costprice;
    @Column(name = "msrp", nullable = false)
    private double msrp;
    @Column(name = "rop", nullable = false)
    private int rop;
    @Column(name = "eoq", nullable = false)
    private int eoq;
    @Column(name = "qoh", nullable = false)
    private int qoh;
    @Column(name = "qoo", nullable = false)
    private int qoo;
    @Column(name = "qrcode", nullable = false)
    private byte[] qrcode;
    
    public ProductEntity()
    {  
    }
    
    public ProductEntity(String prodcd, int vendorno, String vensku, String prodnam,
            double costprice, double msrp, int rop, int eoq, int qoh, int qoo, byte[] qrcode) {
        this.prodcd = prodcd;
        this.vendorno = vendorno;
        this.vensku = vensku;
        this.prodnam = prodnam;
        this.costprice = costprice;
        this.msrp = msrp;
        this.rop = rop;
        this.eoq = eoq;
        this.qoh = qoh;
        this.qoo = qoo;
        this.qrcode = qrcode;
    }
    
    /**
     * Gets the prodcd of this ProductEntity.
     * @return the prodcd
     */
    public String getProdcd() {
        return this.prodcd;
    }

    /**
     * Sets the prodcd of this VendorEntity to the specified value.
     * @param prodcd the new prodcd
     */
    public void setProdcd(String prodcd) {
        this.prodcd = prodcd;
    }
    
    /**
     * Gets the vendorno of this ProductEntity.
     * @return the vendorno
     */
    public int getVendorno() {
        return this.vendorno;
    }

    /**
     * Sets the vendorno of this VendorEntity to the specified value.
     * @param vendorno the new vendorno
     */
    public void setVendorno(int vendorno) {
        this.vendorno = vendorno;
    }
    
    /**
     * Gets the vensku of this ProductEntity.
     * @return the vensku
     */
    public String getVensku() {
        return this.vensku;
    }

    /**
     * Sets the vensku of this VendorEntity to the specified value.
     * @param vensku the new vensku
     */
    public void setVensku(String vensku) {
        this.vensku = vensku;
    }
    
    /**
     * Gets the prodnam of this ProductEntity.
     * @return the prodnam
     */
    public String getProdnam() {
        return this.prodnam;
    }

    /**
     * Sets the prodnam of this VendorEntity to the specified value.
     * @param prodnam the new prodnam
     */
    public void setProdnam(String prodnam) {
        this.prodnam = prodnam;
    }
    
    /**
     * Gets the costprice of this ProductEntity.
     * @return the costprice
     */
    public double getCostprice() {
        return this.costprice;
    }

    /**
     * Sets the costprice of this VendorEntity to the specified value.
     * @param costprice the new costprice
     */
    public void setCostprice(double costprice) {
        this.costprice = costprice;
    }
    
    /**
     * Gets the msrp of this ProductEntity.
     * @return the msrp
     */
    public double getMsrp() {
        return this.msrp;
    }

    /**
     * Sets the msrp of this VendorEntity to the specified value.
     * @param msrp the new msrp
     */
    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }
    
    /**
     * Gets the rop of this ProductEntity.
     * @return the rop
     */
    public int getRop() {
        return this.rop;
    }

    /**
     * Sets the rop of this VendorEntity to the specified value.
     * @param rop the new rop
     */
    public void setRop(int rop) {
        this.rop = rop;
    }
    
    /**
     * Gets the eoq of this ProductEntity.
     * @return the eoq
     */
    public int getEoq() {
        return this.eoq;
    }

    /**
     * Sets the eoq of this VendorEntity to the specified value.
     * @param eoq the new eoq
     */
    public void setEoq(int eoq) {
        this.eoq = eoq;
    }
    
    /**
     * Gets the qoh of this ProductEntity.
     * @return the qoh
     */
    public int getQoh() {
        return this.qoh;
    }

    /**
     * Sets the qoh of this VendorEntity to the specified value.
     * @param qoh the new qoh
     */
    public void setQoh(int qoh) {
        this.qoh = qoh;
    }
    
    /**
     * Gets the qoo of this ProductEntity.
     * @return the qoo
     */
    public int getQoo() {
        return this.qoo;
    }

    /**
     * Sets the qoo of this VendorEntity to the specified value.
     * @param qoo the new qoo
     */
    public void setQoo(int qoo) {
        this.qoo = qoo;
    }
    
    /**
     * Gets the qoh of this ProductEntity.
     * @return the qoh
     */
    public byte[] getQrcode() {
        return this.qrcode;
    }

    /**
     * Sets the qrcode of this VendorEntity to the specified value.
     * @param qrcode the new qrcode
     */
    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
    }
}
