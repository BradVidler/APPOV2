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
public class SingleOrder implements Serializable{
    
    public SingleOrder()
    {
    }
    
    private String name;
    private String email;
    private String address;
    private String city;
    
    public String getName()
    {
        return (name);
    }
        
    public void setName(String uName)
    {
        this.name = uName;
    }
    
    public String getEmail()
    {
        return (email);
    }
        
    public void setEmail(String uEmail)
    {
        this.email = uEmail;
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
}
