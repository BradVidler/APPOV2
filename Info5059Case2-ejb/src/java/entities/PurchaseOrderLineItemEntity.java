
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

@Entity
@NamedQueries({
    @NamedQuery(name = "PurchaseOrderLineItemEntity.findByPonumber",
                query = "SELECT p FROM PurchaseOrderLineItemEntity p WHERE p.pono = :pono")
})
public class PurchaseOrderLineItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LineID", nullable = false)
    private Integer lineid;
    @Column(name = "PONumber", nullable = false)
    private Integer pono;
    @Column(name = "Prodcd", nullable = false)
    private String prodcd;
    @Column(name = "Qty", nullable = false)
    private Integer qty;
    @Column(name = "Price", nullable = false)
    private Double price;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
   
    public PurchaseOrderLineItemEntity() {
    }

    public PurchaseOrderLineItemEntity(int pono, String prodcd, int qty, double price) {
        this.pono = pono;
        this.prodcd = prodcd;
        this.price = price;
        this.qty = qty;
    } 
    
    /**
     * Gets the price of this PurchaseOrderLineItemEntity.
     * @return the price
     */
    public Double getPrice() {
        return this.price;
    }

    /**
     * Sets the price of this PurchaseOrderLineItemEntity to the specified value.
     * @param price the new price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    
    /**
     * Gets the prodcd of this PurchaseOrderLineItemEntity.
     * @return the prodcd
     */
    public String getProdcd() {
        return this.prodcd;
    }

    /**
     * Sets the prodcd of this PurchaseOrderLineItemEntity to the specified value.
     * @param prodcd the new prodcd
     */
    public void setProdcd(String prodcd) {
        this.prodcd = prodcd;
    }
    
    /**
     * Gets the lineid of this PurchaseOrderLineItemEntity.
     * @return the lineid
     */
    public Integer getLineid() {
        return this.lineid;
    }

    /**
     * Sets the lineid of this PurchaseOrderLineItemEntity to the specified value.
     * @param lineid the new lineid
     */
    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }
    
    /**
     * Gets the pono of this PurchaseOrderLineItemEntity.
     * @return the pono
     */
    public Integer getPono() {
        return this.pono;
    }

    /**
     * Sets the pono of this PurchaseOrderLineItemEntity to the specified value.
     * @param pono the new pono
     */
    public void setPono(Integer pono) {
        this.pono = pono;
    }

}