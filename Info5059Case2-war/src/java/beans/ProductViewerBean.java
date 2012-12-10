/*
 * Sample Backing Bean used in conjunction with the sample JSF pages
 */
package beans;

import ejbs.APFacade;
import entities.ProductDetails;
import entities.PurchaseOrderLineItemDetails;
import entities.VendorDetails;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.sql.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

//Revision 1(Nov 16/2012): Added methods getVendorEJB, getAllProductsForVendorEJB and getProdCodesEJB.

@Named
@SessionScoped
public class ProductViewerBean implements Serializable
{
    @EJB(name = "apf")
    private APFacade apf;
    public ProductViewerBean()
    {
    }
    
    @Inject
    VendorBean vendor;
    
    @Inject
    ProductBean product;
    
    private String message;
    private int vendorno;
    private String prodcd;
    
    public VendorBean getVendor() {
        return vendor;
    }
    
    public ProductBean getProduct() {
        return product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getVendorno() {
        return vendorno;
    }

    public void setVendorno(int vendorno) {
        this.vendorno = vendorno;
    }

    public String getProdcd() {
        return prodcd;
    }

    public void setProdcd(String prodcd) {
        this.prodcd = prodcd;
    }
    
     public void changeVendor() {
        message = "";
        try
        {
        if (vendor == null) {
            vendor = new VendorBean();
        }
        vendor.getVendorEJB();
        //vendor.setVendorNo(51);
        //vendor.getProdCodesEJB();
        //System.out.println(vendor.productcodes);
        }
        catch(Exception ex)
        {
            System.out.println("Error changing vendor - " + ex.getMessage());
        }
    }
    
    public void getProductEJB()
    {
        try
        {
            ProductDetails details = apf.getProduct(prodcd);
            product.setCostprice(details.getCostprice());
            product.setEoq(details.getEoq());
            product.setMsrp(details.getMsrp());
            product.setProdcd(details.getProdcd());
            product.setProdname(details.getProdname());
            product.setQoh(details.getQoh());
            product.setQoo(details.getQoo());
            product.setQrcode(details.getQrcode());
            product.setRop(details.getRop());
            product.setVendorno(details.getVendorno());
            product.setVendorsku(details.getVensku());
        }
        catch (Exception ex)
        {
            System.out.println("Can't get product: " + ex.getMessage());
            message = "Can't get product: " + ex.getMessage();
        }
    }
}

