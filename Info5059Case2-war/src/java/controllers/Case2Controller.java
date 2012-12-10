/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//StAX
import javax.xml.stream.XMLInputFactory;
import javax.xml.namespace.QName;
import javax.xml.stream.EventFilter;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;

//Revision 1: Added STaX support
//Revision 2: Added mobile support to make sure non-logged in users do not have access
@WebServlet(name = "Case2Controller", 
            urlPatterns = {"/C2Controller"},
            initParams =  {@WebInitParam(name = "Webpages",
                                         value = "/Webpages.xml")})
public class Case2Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String jsf = request.getParameter("jsf");
        
        // QName stands for Qualified Name
        QName qNode = new QName("webpage");
        QName qAttributeURL = new QName("url");
        QName qAttributeId = new QName("id");
        
        try {
            HttpSession session = request.getSession(); 
            
            String mobile = request.getParameter("mobile");
            
            //mobile pages use session, regular pages do not
            if (mobile != null) {
                if (session.getAttribute("loginOk") == null) {
                    jsf = "L";
                }
            }
            
            String fileName = getServletConfig().getInitParameter("Webpages");
            ServletContext application = getServletConfig().getServletContext();
            InputStream in = application.getResourceAsStream(fileName);
            
            try {
                XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                //setup a new reader
                XMLStreamReader parser = inputFactory.createXMLStreamReader(in);
                
                // Read the XML document
                while (true) {
                    int event = parser.next();
                    if (event == XMLStreamConstants.END_DOCUMENT) {
                        parser.close();
                        break;
                    }

                    if (event == XMLStreamConstants.START_ELEMENT) {
                        if (parser.getName().getLocalPart().equals(qNode.getLocalPart())) { //webpage node
                            if (parser.getAttributeValue(null, qAttributeId.getLocalPart()).equals(jsf)) //found the correct id
                            {
                                out.println("Redirecting...");
                                //redirect to url
                                response.sendRedirect(response.encodeRedirectURL(parser.getAttributeValue(null, qAttributeURL.getLocalPart())));
                                break;
                            }
                        }
                    }
                }

                parser.close();
                
            } catch (XMLStreamException ex) {
                Logger.getLogger(Case2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            session.invalidate();
            

        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
