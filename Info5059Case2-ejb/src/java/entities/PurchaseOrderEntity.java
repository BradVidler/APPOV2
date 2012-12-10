
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
@NamedQuery(name = "PurchaseOrderEntity.findByPonumber",
            query = "SELECT p FROM PurchaseOrderEntity p WHERE p.pono = :pono"),
@NamedQuery(name = "PurchaseOrderEntity.findPONumbersByVendor",
            query = "SELECT p.pono FROM PurchaseOrderEntity p WHERE p.vendorno = :vendorno")
})
public class PurchaseOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PONumber", nullable = false)
    private Integer pono;
    @Column(name = "VendorNo", nullable = false)
    private int vendorno;
    @Column(name = "Amount", nullable = false)
    private double amount;
    @Column(name = "PODate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date podate;
   
    public PurchaseOrderEntity() {
    }

    public PurchaseOrderEntity(int vendorno, double amount, Date podate) {
        this.vendorno = vendorno;
        this.amount = amount;
        this.podate = podate;
    } 
    
    /**
     * Gets the date of this PurchaseOrderEntity.
     * @return the date
     */
    public Date getDate() {
        return this.podate;
    }

    /**
     * Sets the date of this PurchaseOrderEntity to the specified value.
     * @param date the new date
     */
    public void setDate(Date date) {
        this.podate = date;
    }
    
    /**
     * Gets the amount of this PurchaseOrderEntity.
     * @return the amount
     */
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of this PurchaseOrderEntity to the specified value.
     * @param amount the new amount
     */
    public void setPono(Double amount) {
        this.amount = amount;
    }
    
    /**
     * Gets the pono of this PurchaseOrderEntity.
     * @return the pono
     */
    public Integer getPono() {
        return this.pono;
    }

    /**
     * Sets the pono of this PurchaseOrderEntity to the specified value.
     * @param pono the new pono
     */
    public void setPono(Integer pono) {
        this.pono = pono;
    }

    /**
     * Gets the vendorno of this PurchaseOrderEntity.
     * @return the vendorno
     */
    public Integer getVendorno() {
        return this.vendorno;
    }

    /**
     * Sets the vendorno of this PurchaseOrderEntity to the specified value.
     * @param vendorno the new vendorno
     */
    public void setVendorno(Integer vendorno) {
        this.vendorno = vendorno;
    }

}