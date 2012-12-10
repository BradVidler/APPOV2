package beans;

import ejbs.APFacade;
import entities.ProductDetails;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.sql.*;

//qrcode stuff
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

//Revision 1(nov. 16/2012): Added method addProductEJB. Getter/setter for qrcode.

@Named(value = "productBean")
@RequestScoped
public class ProductBean {
    @EJB(name = "apf")
    private APFacade apf;
    
    public ProductBean()
    {
    }
    
    private String prodcd;
    private int vendorno;
    private String vendorsku; //sku = stock keeping unit
    private String prodname;
    private double costprice;
    private double msrp; //manufacturer’s suggested retail price
    private int rop; //reorder point
    private int qoh; //quantity on hand
    private int eoq; //economic order quantity
    private int qoo; //quantity on order
    
    private String qrcodestring;
    private byte[] qrcode;
    
    private String message = "";
    
    public String getProdcd()
    {
        return (prodcd);
    }
        
    public void setProdcd(String uProdcd)
    {
        this.prodcd = uProdcd;
    }
    
    public int getVendorno()
    {
        return (vendorno);
    }
        
    public void setVendorno(int uVendorno)
    {
        this.vendorno = uVendorno;
    }
    
    public String getVendorsku()
    {
        return (vendorsku);
    }
        
    public void setVendorsku(String uVendorsku)
    {
        this.vendorsku = uVendorsku;
    }
    
    public String getProdname()
    {
        return (prodname);
    }
        
    public void setProdname(String uProdname)
    {
        this.prodname = uProdname;
    }
    
    public double getCostprice()
    {
        return (costprice);
    }
        
    public void setCostprice(double uCostprice)
    {
        this.costprice = uCostprice;
    }
    
    public double getMsrp()
    {
        return (msrp);
    }
        
    public void setMsrp(double uMsrp)
    {
        this.msrp = uMsrp;
    }
    
    public int getRop()
    {
        return (rop);
    }
        
    public void setRop(int uRop)
    {
        this.rop = uRop;
    }
    
    public int getQoh()
    {
        return (qoh);
    }
        
    public void setQoh(int uQoh)
    {
        this.qoh = uQoh;
    }
    
    public int getEoq()
    {
        return (eoq);
    }
        
    public void setEoq(int uEoq)
    {
        this.eoq = uEoq;
    }
    
    public int getQoo()
    {
        return (qoo);
    }
        
    public void setQoo(int uQoo)
    {
        this.qoo = uQoo;
    }
    
    public String getMessage()
    {
        return (message);
    }
        
    public void setMessage(String uMessage)
    {
        this.message = uMessage;
    }
    
    public String getQrcodestring()
    {
        return (qrcodestring);
    }
        
    public void setQrcodestring(String uQrcodestring)
    {
        this.qrcodestring = uQrcodestring;
        createQRCode();
    }
    
    public byte[] getQrcode()
    {
        return (qrcode);
    }
        
    public void setQrcode(byte[] uQrcode)
    {
        this.qrcode = uQrcode;
    }
    
    @Resource(name = "Info5059db")
    DataSource ds;
    
    public void createQRCode()
    {
       try {
               byte[] qrCodeBinary = QRCode.from(qrcodestring).to(ImageType.PNG).stream().toByteArray();
               setQrcode(qrCodeBinary);
        } catch (Exception ex) {            
            message = "Could not create qrcode!";
        }
    }
    
    public boolean addProductEJB()
    {
        boolean success = false;
            
        try   
        {
            //serialize the data
            ProductDetails details = new ProductDetails();
            details.setProdcd(prodcd);
            details.setVendorno(vendorno);
            details.setCostprice(costprice);
            details.setMsrp(msrp);
            details.setProdname(prodname);
            details.setVensku(vendorsku);
            details.setEoq(eoq);
            details.setQoh(qoh);
            details.setQoo(qoo);
            details.setRop(rop);
            details.setQrcode(qrcode);

            //call to APFacade
            success = apf.addProduct(details);

            if (success == true)
            {
                message = "Product Added!";
            }
            else
            {
                message = "Product Not Added!";
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return success;
    }
    
    public String addProduct() {
            PreparedStatement pstmt;
            Connection con = null;
            String sql = "INSERT INTO Products (prodcd,vendorno,vensku,prodnam,costprice,msrp,rop,qoh,eoq,qoo) VALUES (?,?,?,?,?,?,?,?,?,?)";
            try{
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, prodcd);
                pstmt.setInt(2, vendorno);
                pstmt.setString(3, vendorsku);
                pstmt.setString(4, prodname);
                pstmt.setDouble(5, costprice);
                pstmt.setDouble(6, msrp);
                pstmt.setInt(7, rop);
                pstmt.setInt(8, qoh);
                pstmt.setInt(9, eoq);
                pstmt.setInt(10, qoo);
                pstmt.execute();
                //rs.next();
                //vendorno = rs.getInt(1);
                //rs.close();
                pstmt.close();
                //if (vendorno > 0) {
                message = "Product " + prodcd + " Added!";
                //} 
                //else {
                //    message = "Product Not Added!";
                //}
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                message = "Problem adding Product - " + e.getMessage();
            }
            finally {
                try {
                    con.close();
                }
                catch (Exception e) {
                    System.out.println("error closing db object(s) - " + e.getMessage());
                }
                return message;
            }
        }
            
}
