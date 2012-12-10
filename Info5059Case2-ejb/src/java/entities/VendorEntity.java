
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/** 
 *
 * @author evan
 * Starting Code for Entity Bean to persist Vendor information via JPA
 */

//Revision 1(nov. 16/2012): Added finder method
@Entity
@NamedQueries( { 
    @NamedQuery(name = "VendorEntity.findByVendorno",
                query = "SELECT v FROM VendorEntity v WHERE v.vendorno = :vendorno"),
    @NamedQuery(name = "VendorEntity.findAllVendorNos",
                query = "SELECT v.vendorno FROM VendorEntity v")
}
)
public class VendorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VendorNo", nullable = false)
    private Integer vendorno;
    @Column(name = "Address1", nullable = false)
    private String address1;
    @Column(name = "City", nullable = false)
    private String city;
    @Column(name = "Province", nullable = false)
    private String province;
    @Column(name = "Postalcode", nullable = false)
    private String postalcode;
    @Column(name = "Phone", nullable = false)
    private String phone;
    @Column(name = "Type", nullable = false)
    private String type;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Email", nullable = false)
    private String email;

   
    public VendorEntity() {
    }

    public VendorEntity(String address1, String city, String province, String postalcode,
            String phone, String type, String name, String email) {
        this.address1 = address1;
        this.city = city;
        this.province = province;
        this.postalcode = postalcode;
        this.phone = phone;
        this.type = type;
        this.name = name;
        this.email = email;
    } 

    /**
     * Gets the vendorno of this VendorEntity.
     * @return the vendorno
     */
    public Integer getVendorno() {
        return this.vendorno;
    }

    /**
     * Sets the vendorno of this VendorEntity to the specified value.
     * @param vendorno the new vendorno
     */
    public void setVendorno(Integer vendorno) {
        this.vendorno = vendorno;
    }

    /**
     * Gets the address1 of this VendorEntity.
     * @return the address1
     */
    public String getAddress1() {
        return this.address1;
    }

    /**
     * Sets the address1 of this VendorEntity to the specified value.
     * @param address1 the new address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Gets the city of this VendorEntity.
     * @return the city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Sets the city of this VendorEntity to the specified value.
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the province of this VendorEntity.
     * @return the province
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * Sets the province of this VendorEntity to the specified value.
     * @param province the new province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Gets the postalcode of this VendorEntity.
     * @return the postalcode
     */
    public String getPostalcode() {
        return this.postalcode;
    }

    /**
     * Sets the postalcode of this VendorEntity to the specified value.
     * @param postalcode the new postalcode
     */
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    /**
     * Gets the phone of this VendorEntity.
     * @return the phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets the phone of this VendorEntity to the specified value.
     * @param phone the new phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the type of this VendorEntity.
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type of this VendorEntity to the specified value.
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the name of this VendorEntity.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this VendorEntity to the specified value.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of this VendorEntity.
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of this VendorEntity to the specified value.
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}