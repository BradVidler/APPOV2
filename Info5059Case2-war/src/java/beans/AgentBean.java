package beans;

import ejbs.APFacade;
import entities.ProductDetails;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.sql.*;

@Named(value = "agentBean")
@SessionScoped
public class AgentBean implements Serializable {
    @EJB(name = "apf")
    private APFacade apf;
    public AgentBean()
    {
    }
    
    private int agentno;
    private String password;
    private String msg;
    private boolean loginOk;

    public int getAgentno() {
        return agentno;
    }

    public void setAgentno(int agentno) {
        this.agentno = agentno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isLoginOk() {
        return loginOk;
    }

    public void setLoginOk(boolean loginOk) {
        this.loginOk = loginOk;
    }
    
    public void loginEJB() {
        try {
            //locate and call APFacade
            //System.err.println("Attempting to login...");
            loginOk = apf.login(agentno, password);
            //System.out.println("Back from apf");
            if (loginOk) {
                msg = "Login Successful";
                FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                session.setAttribute("loginOk", loginOk);
            } else {
                msg = "Login Unsucessful, try again";
                System.err.println("LOGIN FAILED");
            }
        } catch (Exception ex) {
            System.err.println("Login failed - " + ex.getMessage());
            msg = "Problem loggin in - " + ex.getMessage();
        }
        //msg = "Something happened...";
    }
}
