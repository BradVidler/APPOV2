
package beans;

import ejbs.APFacade;
import entities.PurchaseOrderDetails;
import javax.inject.Named;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.sql.*;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.ByteArrayInputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import entities.PurchaseOrderLineItemDetails;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

@Named
@SessionScoped
public class poViewerBean implements Serializable {
    @EJB(name = "apf")
    private APFacade apf;
    public poViewerBean()
    {
    }
    
    @Inject
    VendorBean vendor;
    
    private int pono;
    private String potext;
    private double poprice;
    
    private SingleOrder order;
    private ArrayList<SingleItem> orderitems;
    
    private String message;
    
    private ArrayList<PurchaseOrderLineItemDetails> polineitems;
    
    public VendorBean getVendor() {
        return vendor;
    }
    
    public void changeVendor() {
        message = "";
        if (vendor == null) {
            vendor = new VendorBean();
        }
        vendor.getVendorEJB();
        //vendor.getProductcodes();
    }
    
    public int getPono()
    {
        return (pono);
    }

    public void setPono(int uPono)
    {
        this.pono = uPono;
    }
    
    public String getPotext()
    {
        return (potext);
    }
    
    public String getMessage()
    {
        return (message);
    }
    
    public void setMessage(String msg)
    {
        this.message = msg;
    }
    
    @Resource(name = "Info5059db")
    DataSource ds;
    
    public void changePO()
    {
        String sql = "SELECT * FROM purchaseorders WHERE ponumber = " + pono;
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection con = null;

        try
        {
            con = ds.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            //order = new ArrayList<SingleOrder>();
            while(rs.next())
            {         
                poprice = (rs.getDouble("amount"));
            }
        }
        catch (Exception e)
        {
            System.out.println("Can't get order - " + e);
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
                message = "error getting order - " + e.getMessage();
            }
        }

        //return orders;
        
        //set potext
        getOrderitems();
        setPotext();
    }
    
    public void getOrderitems()
    {
        String sql = "SELECT * FROM purchaseorderlineitems WHERE ponumber = " + pono;
        PreparedStatement stmt = null;
        ResultSet rs;
        Connection con = null;

        try
        {
            con = ds.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            orderitems = new ArrayList<SingleItem>();
            while(rs.next())
            {         
                SingleItem item = new SingleItem();
                item.setProdcd(rs.getString("prodcd"));
                item.setPrice(rs.getDouble("price"));
                item.setQty(rs.getInt("qty"));
                item.setExt(item.getPrice() * 1.13);
                
                orderitems.add(item);
            }
        }
        catch (Exception e)
        {
            System.out.println("Can't get order - " + e);
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
                message = "error getting order - " + e.getMessage();
            }
        }

        //return orders;
    }
    
    public byte[] buildXML() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = null;
        
        try {
            //System.out.println(pono + " " + poprice + " " + vendor.getName());
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            writer = xof.createXMLStreamWriter(out);
            writer.writeStartDocument("1.0");
            writer.writeStartElement("orders"); //write root
            
            writer.writeEmptyElement("vendor");
            writer.writeAttribute("name", vendor.getName());
            writer.writeAttribute("email", vendor.getEmail());
            writer.writeAttribute("address", vendor.getAddress());
            writer.writeAttribute("city", vendor.getCity());
            
            for(PurchaseOrderLineItemDetails item : polineitems)
            {
                poprice += item.getPrice() * item.getQty();
                writer.writeEmptyElement("polineitem");
                writer.writeAttribute("prodcode", item.getProdcd());
                writer.writeAttribute("price", (priceToString(item.getPrice())));
                writer.writeAttribute("qty", Integer.toString(item.getQty()));
                writer.writeAttribute("ext", priceToString(item.getQty() * item.getPrice() * 1.13));
            }
            
            PurchaseOrderDetails po = new PurchaseOrderDetails();
            po = apf.getPO(pono);
            writer.writeEmptyElement("PO");
            writer.writeAttribute("podate", po.getPodate().toString());
            writer.writeAttribute("num", Integer.toString(pono));
            writer.writeAttribute("total", priceToString(poprice));
            writer.writeAttribute("tax", priceToString(poprice * 0.13));
            writer.writeAttribute("pototal", priceToString(poprice * 1.13));
            
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } catch (Exception ex) {
            System.out.print("ERROR building XML - " + ex.getCause());
        }
        
        return out.toByteArray();
    }
    
    public static String priceWithDecimal (Double price) {
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
    return formatter.format(price);
}

public static String priceWithoutDecimal (Double price) {
    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
    return formatter.format(price);
}

public static String priceToString(Double price) {
    String toShow = priceWithoutDecimal(price);
    if (toShow.indexOf(".") > 0) {
        return priceWithDecimal(price);
    } else {
        return priceWithoutDecimal(price);
    }
}
    
    public void setPotext()
    {
        try
        {
            //locate xsl, build xml, and transform to html
            ExternalContext application = FacesContext.getCurrentInstance().getExternalContext();
            Source xsl = new StreamSource(application.getResourceAsStream("/POViewer.xsl"));
            Source xml = new StreamSource(new ByteArrayInputStream(buildXML()));
            
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(xsl);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            transformer.transform(xml, new StreamResult(out));
            
            potext = out.toString(); //potext is displayed in jsf
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }

    public void cmdPickPO_action() {
        try {
            poprice = 0.00;
            vendor.getVendorEJB();
            polineitems = vendor.getPOLinesEJB(pono);
            //poprice = 1.00;
            
            //locate xsl, build xml, and trandform to html
            ExternalContext application = FacesContext.getCurrentInstance().getExternalContext();
            Source xsl = new StreamSource(application.getResourceAsStream("/POViewer.xsl"));
            Source xml = new StreamSource(new ByteArrayInputStream(buildXML()));
            
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(xsl);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            transformer.transform(xml, new StreamResult(out));
            
            potext = out.toString(); //potext is displayed in JSP
        } catch (Exception ex) {
            System.out.println("Error generating XML - " + ex.getMessage());
        }
    }
 
}
