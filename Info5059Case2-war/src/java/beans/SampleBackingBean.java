/*
 * Sample Backing Bean used in conjunction with the sample JSF pages
 */
package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.*;
import javax.annotation.Resource;
import javax.sql.*;

@Named(value = "sampleBackingBean")
@RequestScoped
public class SampleBackingBean 
{
    public SampleBackingBean()
    {
        super();
    }
        private String name;
        private String message = "";
        
        private int vendorno;
        
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

