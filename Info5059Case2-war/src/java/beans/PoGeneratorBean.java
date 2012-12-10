/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import ejbs.APFacade;
import entities.ProductDetails;
import entities.PurchaseOrderLineItemDetails;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import java.sql.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;

@Named
@SessionScoped
public class PoGeneratorBean implements Serializable {
    @EJB(name = "apf")
    private APFacade apf;
    
    public PoGeneratorBean()
    {
    }
    
    @Inject
    VendorBean vendor;
    
    private SingleItem item;
    
    private String prodcd;
    private int vendorno;
    
    private int orderno; //CASE 2
    
    private String vendorsku; //sku = stock keeping unit
    private String prodname;
    private double costprice;
    private double msrp; //manufacturer’s suggested retail price
    private int rop; //reorder point
    private int qoh; //quantity on hand
    private int eoq; //economic order quantity
    private int qoo; //quantity on order
    private String message = "";
    private ArrayList<SelectItem> productcodes = null;
    private ArrayList<SingleItem> items = null;
    private ArrayList<SelectItem> qtys = null;
    private double total;
    private int qty;
    private double sub;
    private double tax;
    private int poNum;
    private int loNum;
    
    
    public int getVendorno()
    {
        return (vendorno);
    }
        
    public void setVendorno(int uVendorno)
    {
        this.vendorno = uVendorno;
    }
    
    public int getOrderno()
    {
        return (orderno);
    }
        
    public void setOrderno(int uOrderno)
    {
        this.orderno = uOrderno;
    }
    
    public int getQty()
    {
        return (qty);
    }
        
    public void setQty(int uQty)
    {
        this.qty = uQty;
    }
    
    public String getProdcd()
    {
        return (prodcd);
    }
        
    public void setProdcd(String uProdcd)
    {
        this.prodcd = uProdcd;
    }
    
    public String getMessage()
    {
        return (message);
    }
        
    public void setMessage(String uMessage)
    {
        this.message = uMessage;
    }
    
    public double getSub()
    {
        return (sub);
    }
        
    public void setSub(double uSub)
    {
        this.sub = uSub;
    }
    
    public double getTotal()
    {
        return (total);
    }
        
    public void setTotal(double uTotal)
    {
        this.total = uTotal;
    }
    
    public double getTax()
    {
        return (tax);
    }
        
    public void setTax(double uTax)
    {
        this.tax = uTax;
    }
    
    @Resource(name = "Info5059db")
    DataSource ds;
    
   
    public VendorBean getVendor() {
        return vendor;
    }
    
    public void changeVendor() {
        //Thread.dumpStack();
        message = "";
        if (vendor == null) {
            vendor = new VendorBean();
        }
        vendor.setVendorNo(vendorno); //string coming from jsf
        vendor.getVendorEJB();
        //vendor.getProductcodes();
        productcodes = new ArrayList<SelectItem>();
        qtys = new ArrayList<SelectItem>();
        items = new ArrayList<SingleItem>();
        //productcodes = vendor.getAllProductsForVendor();
    }
    
    public ArrayList<SelectItem> getQtys(){
        for(int i = 0; i<10; ++i)
        {
            SelectItem item = new SelectItem(Integer.toString(i));
            qtys.add(item);
        }
        return qtys;
    }
    
    public ArrayList<SingleItem> getItems()
    {
        return items;
    }
    
    public void fetchItem() 
    {
        String sql = "SELECT * FROM Products WHERE prodcd = '" + prodcd + "'";
            PreparedStatement stmt = null;
            ResultSet rs;
            Connection con = null;
           
            try
            {
                con = ds.getConnection();
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                rs.next();
                prodname = (String) rs.getString("prodnam");
                costprice = rs.getDouble("costprice");  
                //ext = price * qty;
            }
            catch (Exception e)
            {
                System.out.println("Can't get product " + e);
                //Thread.dumpStack();
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
                    //message = "error getting vender";
                }
            }
    }
    
    public void addItemEJB() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProdcd().equalsIgnoreCase(prodcd)) {
                total -= items.get(i).getExt();
                sub -= items.get(i).getExt() / 1.13;
                tax -= items.get(i).getExt() * 0.13;
                items.remove(i);
            }
        }
        
        for (ProductDetails prod : vendor.getAllProductsForVendorEJB()) {
            if (qty > 0) {
                if (prodcd.equalsIgnoreCase(prod.getProdcd())) {
                    item = new SingleItem();
                    item.setProdcd(prodcd);
                    item.setProdname(prod.getProdname());
                    item.setPrice(prod.getCostprice());

                    if (qty == eoq) {
                        item.setQty(prod.getEoq());
                    } else {
                        item.setQty(qty);
                    }
                    
                    item.setExt(item.getQty() * item.getPrice() * 1.13);
                    sub += item.getPrice() * item.getQty();
                    tax += item.getPrice() * item.getQty() * 0.13;
                    total += item.getExt();
                    items.add(item);
                }
            }
        }
    }
    
    public void addItem(){
        item = new SingleItem();
        //check if the item is already in our items list
        for (int i = 0; i < items.size(); ++i) {
            if ((items.get(i) != null) && (items.get(i).getProdcd().equalsIgnoreCase(prodcd))){
                total-=items.get(i).getExt()*1.13;
                sub-=items.get(i).getPrice() * items.get(i).getQty();
                tax=sub*0.13;
                items.remove(i);
            }
        }
        
        //add the item if quantity is greater than 0, otherwise keep it off (already deleted it)
        if (qty > 0)
        {
            fetchItem(); //sets prodname and costprice
            item.setProdcd(prodcd);
            item.setProdname(prodname);
            item.setPrice(costprice);
            item.setQty(qty);
            item.setExt(costprice * item.getQty());
            items.add(item);
            sub+=costprice*qty;
            tax=sub*0.13;
            total=sub+tax;
        }
    }
    
    public void poAddEJB() {
        poNum = -1;
        
        try {
            ArrayList<PurchaseOrderLineItemDetails> details = new ArrayList<PurchaseOrderLineItemDetails>();
            
            for (SingleItem item : items) {
                PurchaseOrderLineItemDetails detail = new PurchaseOrderLineItemDetails();
                detail.setPono(0);
                detail.setQty(item.getQty());
                detail.setProdcd(item.getProdcd());
                detail.setPrice(item.getPrice());
                details.add(detail);
            }
            
            poNum = apf.addPO(details, vendorno, total);
        } catch (Exception ex) {
            
        }
    }
    
    public String addProductOrder() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        PreparedStatement pstmt;
        Connection con = null;
        String sql = "INSERT INTO PurchaseOrders (Vendorno,Amount,PODate) VALUES (?,?,?)";
        try{
            con = ds.getConnection();
            con.setAutoCommit(false); //needed for trans rollback
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, vendorno);
            pstmt.setDouble(2, total);
            pstmt.setDate(3, sqlDate);
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            poNum = rs.getInt(1);
            rs.close();
            pstmt.close();
            
            for(SingleItem item : items){
                if (item.getQty() > 0) {
                    sql = "INSERT INTO PurchaseOrderLineItems (PONumber,Prodcd,Qty,Price) VALUES (?,?,?,?)";
                    pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, poNum);
                    pstmt.setString(2, item.getProdcd());
                    pstmt.setInt(3, item.getQty());
                    pstmt.setDouble(4, item.getPrice());
                    pstmt.execute();
                    rs = pstmt.getGeneratedKeys();
                    rs.next();
                    loNum = rs.getInt(1);
                    
                }
                rs.close();
                pstmt.close();
            }//end for
            con.commit();
            message = "PO " + poNum + " Added!";
            
            //clear the array so the items don't show after we click add PO
            items.clear();
            
            //set totals back to 0
            sub = 0;
            tax = 0;
            total = 0;
        }
        catch (Exception e){
            poNum = 0;
            System.out.println("Transaction failed - " + e.getMessage());
            message = "PO Creation Failed!";
            try{
                con.rollback();
            } catch(Exception sqx) {
                System.out.println("Rollback failed " + sqx.getMessage());
            }
                
        }
        finally {
            try {
                con.close();
            }
            catch (Exception sqx) {
                System.out.println("error closing db object(s) - " + sqx.getMessage());
            }
            return message;
        }
    }
}
