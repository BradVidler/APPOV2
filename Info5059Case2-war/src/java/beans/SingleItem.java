/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.faces.model.SelectItem;
import javax.sql.*;
import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class SingleItem implements Serializable{
    
    public SingleItem()
    {
    }
    
    private int pono;
    private String prodcd;
    private String prodname;
    private int qty;
    private double price;
    private double ext;
    
    public int getPono()
    {
        return (pono);
    }
        
    public void setPono(int pono)
    {
        this.pono = pono;
    }
    
    public String getProdcd()
    {
        return (prodcd);
    }
        
    public void setProdcd(String uProdcd)
    {
        this.prodcd = uProdcd;
    }
    
    public String getProdname()
    {
        return (prodname);
    }
        
    public void setProdname(String uProdname)
    {
        this.prodname = uProdname;
    }
    
    public int getQty()
    {
        return (qty);
    }
        
    public void setQty(int uQty)
    {
        this.qty = uQty;
    }
    
    public double getPrice()
    {
        return (price);
    }
        
    public void setPrice(double uPrice)
    {
        this.price = uPrice;
    }
    
    public double getExt()
    {
        return (ext);
    }
        
    public void setExt(double uExt)
    {
        this.ext = uExt;
    }
}
