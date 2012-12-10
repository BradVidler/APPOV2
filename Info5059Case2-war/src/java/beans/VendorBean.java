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

//Revision 1(Nov 16/2012): Added methods getVendorEJB, getAllProductsForVendorEJB and getProdCodesEJB.
//Revision 2: Added getPOLinesEJB

@Named
@SessionScoped
public class VendorBean implements Serializable
{
    @EJB(name = "apf")
    private APFacade apf;
    public VendorBean()
    {
    }
        private int vendorno;
        private String name;
        private String address;
        private String city;
        private String province;
        private String postalcode;
        private String phone;
        private String type;
        private String email;
        private String message = "";
        
        private ArrayList<SelectItem> vendornos = null;
        private ArrayList<SelectItem> productcodes = null;
        
        private ArrayList<SelectItem> ordernos = null; //CASE 2
        
        private ArrayList<ProductDetails> productdetails = null;
        
        private ArrayList<PurchaseOrderLineItemDetails> polineitems = null;
        
        public String getName()
        {
            return (name);
        }
        
        public void setName(String uName)
        {
            this.name = uName;
        }
        
        public String getMessage()
        {
            return (message);
        }
        
        public String getAddress()
        {
            return (address);
        }
        
        public void setAddress(String uAddress)
        {
            this.address = uAddress;
        }
        
        public String getCity()
        {
            return (city);
        }
        
        public void setCity(String uCity)
        {
            this.city = uCity;
        }
        
        public String getProvince()
        {
            return (province);
        }
        
        public void setProvince(String uProvince)
        {
            this.province = uProvince;
        }
        
        public String getPostalCode()
        {
            return (postalcode);
        }
        
        public void setPostalCode(String uPostalCode)
        {
            this.postalcode = uPostalCode;
        }
        
        public String getPhone()
        {
            return (phone);
        }
        
        public void setPhone(String uPhone)
        {
            this.phone = uPhone;
        }
        
        public String getType()
        {
            return (type);
        }
        
        public void setType(String uType)
        {
            this.type = uType;
        }
        
        public String getEmail()
        {
            return (email);
        }
        
        public void setEmail(String uEmail)
        {
            this.email = uEmail;
        }
        
        public void setMessage(String msg)
        {
            this.message = msg;
        }
        
        public int getVendorNo()
        {
            return (vendorno);
        }
        
        public void setVendorNo(int uNo)
        {
            this.vendorno = uNo;
        }
        
        public void doSomething()
        {
            try
            {
                message = "Hey " + name + " the time is now "
                        + new java.util.Date().toString();
            }
            catch (Exception e)
            {
                message = "Error: " + e.getMessage();
            }
        }
        
        @Resource(name = "Info5059db")
        DataSource ds;
        
        public ArrayList<PurchaseOrderLineItemDetails> getPOLinesEJB(int pono) {
            try {
                polineitems = apf.getLineItem(pono);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return polineitems;
        }
        
        public ArrayList<SelectItem> getOrdernos()
        {
            int[] nos = null;
            try
            {
                ordernos = new ArrayList<SelectItem>();
                nos = apf.getPONos(vendorno);
                for (int i = 0; i < nos.length; i++) {
                    SelectItem item = new SelectItem(Integer.toString(nos[i]));
                    ordernos.add(item);
                }
            }
            catch (Exception ex)
            {
                message = "error getting ordernos - " + ex.getMessage();
            }
            return ordernos;
        }
        
        public ArrayList<SelectItem> getOrdernosOLD()
        {
            String sql = "SELECT ponumber FROM purchaseorders WHERE vendorno =  " + vendorno;
            PreparedStatement stmt = null;
            ResultSet rs;
            Connection con = null;
            
            try
            {
                con = ds.getConnection();
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                ordernos = new ArrayList<SelectItem>();
                while(rs.next())
                {         
                    //System.out.println("hello");
                    SelectItem item = new SelectItem(Integer.toString(rs.getInt(1)));
                    ordernos.add(item);
                }
                
            }
            catch (Exception e)
            {
                System.out.println("Can't get ordernos - " + e);
            }
            finally
            {
                try
                {
                    stmt.close();
                    con.close();
                }
                catch(Exception e)
                {
                    System.out.println("error closing db object(s) " + e.getMessage());
                    message = "error getting ordernos - " + e.getMessage();
                }
            }
                
            return ordernos;
        }
        
        public ArrayList<SelectItem> getVendornos()
        {
            int[] nos = null;
            try
            {
                vendornos = new ArrayList<SelectItem>();
                nos = apf.getVendorNos();
                //System.out.println(nos.length);
                for (int i = 0; i < nos.length; i++) {
                    SelectItem item = new SelectItem(Integer.toString(nos[i]));
                    vendornos.add(item);
                    //System.out.println(nos[i]);
                }
            }
            catch (Exception ex)
            {
                message = "error getting vendornos - " + ex.getMessage();
            }
            return vendornos;
        }
        
        public ArrayList<SelectItem> getVendornosOLD()
        {
            String sql = "SELECT vendorno FROM vendors";
            PreparedStatement stmt = null;
            ResultSet rs;
            Connection con = null;
            
            try
            {
                con = ds.getConnection();
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                vendornos = new ArrayList<SelectItem>();
                while(rs.next())
                {         
                    //System.out.println("hello");
                    SelectItem item = new SelectItem(Integer.toString(rs.getInt(1)));
                    vendornos.add(item);
                }
                
            }
            catch (Exception e)
            {
                System.out.println("Can't get vendornos - " + e);
            }
            finally
            {
                try
                {
                    stmt.close();
                    con.close();
                }
                catch(Exception e)
                {
                    System.out.println("error closing db object(s) " + e.getMessage());
                    message = "error getting vendornos - " + e.getMessage();
                }
            }
                
            return vendornos;
        }
        
        public String addVendor() {
            PreparedStatement pstmt;
            Connection con = null;
            String sql = "INSERT INTO Vendors (Address1,City,Province,PostalCode,Phone,VendorType,Name,Email) VALUES (?,?,?,?,?,?,?,?)";
            try{
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, address);
                pstmt.setString(2, city);
                pstmt.setString(3, province);
                pstmt.setString(4, postalcode);
                pstmt.setString(5, phone);
                pstmt.setString(6, type);
                pstmt.setString(7, name);
                pstmt.setString(8, email);
                pstmt.execute();
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                vendorno = rs.getInt(1);
                rs.close();
                pstmt.close();
                if (vendorno > 0) {
                    message = "Vendor " + vendorno + " Added!";
                } 
                else {
                    message = "Vendor Not Added!";
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                message = "Problem adding Vendor - " + e.getMessage();
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
        
        public void addVendorEJB()
        {
            vendorno = -1;
            
            try   
            {
                //serialize the data
                VendorDetails details = new VendorDetails();
                details.setAddress1(address);
                details.setName(name);
                details.setCity(city);
                details.setProvince(province);
                details.setPhone(phone);
                details.setEmail(email);
                details.setPostalCode(postalcode);
                details.setType(type);
                
                //call to APFacade
                vendorno = apf.addVendor(details);
                
                if (vendorno > 0)
                {
                    message = "Vendor " + vendorno + " Added!";
                }
                else
                {
                    message = "Vendor Not Added!";
                }
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage() + vendorno);
            }
        }
        
        public ArrayList<SelectItem> getProductcodes()
        {
            productcodes = new ArrayList<SelectItem>();
            try
            {
                if (vendorno > 0)
                {
                    productdetails = apf.getAllProductsForVendor(vendorno);
                    for(ProductDetails p : productdetails)
                    {
                        SelectItem item = new SelectItem(p.getProdcd());
                        productcodes.add(item);
                        System.out.println("Added item - " + p.getProdcd());
                    }
                }
            }
            catch (Exception ex)
            {
                System.out.println("Error getting prodcds - " + ex.getMessage());
            }
            return productcodes;
        }
        
        public ArrayList<ProductDetails> getAllProductsForVendorEJB()
        {
            try
            {
                productdetails = apf.getAllProductsForVendor(vendorno);
                System.out.println("Products from facade - " + productdetails.size());
            }
            catch (Exception ex)
            {
                System.out.println("Can't get product entities for vendor:" + ex.getMessage());
            }
            return productdetails;
        }
        
        public ArrayList<SelectItem> getProductcodesOLD() {
            String sql = "SELECT prodcd FROM Products WHERE vendorno = " + vendorno;
            PreparedStatement stmt = null;
            ResultSet rs;
            Connection con = null;
            
            try
            {
                productcodes = new ArrayList<SelectItem>();
                con = ds.getConnection();
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                while(rs.next())
                {         
                    //System.out.println("hello");
                    SelectItem item = new SelectItem(rs.getString("prodcd"));
                    productcodes.add(item);
                }
                
            }
            catch (Exception e)
            {
                System.out.println("Can't get vendor " + e);
            }
            finally
            {
                try
                {
                    stmt.close();
                    con.close();
                }
                catch(Exception e)
                {
                    System.out.println("error closing db object(s) " + e.getMessage());
                    message = "error getting vender";
                }
            }
            return productcodes;
        }
              
        public void getVendorEJB()
        {
            try
            {
                VendorDetails details = apf.getVendor(vendorno);
                name = details.getName();
                address = details.getAddress1();
                city = details.getCity();
                province = details.getProvince();
                postalcode = details.getPostalCode();
                phone = details.getPhone();
                type = details.getType();
                email = details.getEmail();
            }
            catch (Exception ex)
            {
                System.out.println("Can't get vendor: " + ex);
                message = "Can't get vendor: " + ex;
            }
        }
        
        public void getVendor() {
            String sql = "SELECT * FROM Vendors WHERE vendorno = " + vendorno;
            PreparedStatement stmt = null;
            ResultSet rs;
            Connection con = null;
            
            try
            {
                con = ds.getConnection();
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                rs.next();
                name = (String) rs.getString("name");
                province = (String) rs.getString("province");
                email = (String) rs.getString("email");
                address = (String) rs.getString("address1");
                postalcode = (String) rs.getString("postalcode");
                type = (String) rs.getString("vendortype");
                city = (String) rs.getString("city");
                phone = (String) rs.getString("phone");
                //message = "Vendor 1's name = " + name;
                
            }
            catch (Exception e)
            {
                System.out.println("Can't get vendor " + e);
            }
            finally
            {
                try
                {
                    stmt.close();
                    con.close();
                }
                catch(Exception e)
                {
                    System.out.println("error closing db object(s) " + e.getMessage());
                    message = "error getting vender";
                }
            }
        }
  
        
        public void findSomeDBData()
        {
            String sql = "SELECT name FROM Vendors WHERE vendorno = " + vendorno;
            PreparedStatement stmt = null;
            ResultSet rs;
            Connection con = null;
            
            try
            {
                con = ds.getConnection();
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                rs.next();
                name = (String) rs.getString("name");
                message = "Vendor 1's name = " + name;
            }
            catch (Exception e)
            {
                System.out.println("Can't get vendor " + e);
            }
            finally
            {
                try
                {
                    stmt.close();
                    con.close();
                }
                catch(Exception e)
                {
                    System.out.println("error closing db object(s) " + e.getMessage());
                    message = "error getting vender";
                }
            }
        }
           
}

