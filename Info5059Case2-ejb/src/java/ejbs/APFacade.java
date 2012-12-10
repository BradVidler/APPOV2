package ejbs;

import entities.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class APFacade  {

    @PersistenceContext
    private EntityManager em;
    
    public boolean login (int no, String pw) {
        boolean retVal = false;
        try {
            System.out.println("In facade for Agent");
            Query qry = em.createNamedQuery("AgentEntity.findByAgentno");
            qry.setParameter("agentno", no);
            AgentEntity agent = (AgentEntity) qry.getSingleResult();
            if (agent.getPassword().equals(pw)) {
                retVal = true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return retVal;
    }

    public int addVendor(VendorDetails ven) {

        VendorEntity ve = null;
        int retVal = -1;

        try {
           ve = new VendorEntity(ven.getAddress1(), ven.getCity(),
                    ven.getProvince(), ven.getPostalCode(),
                    ven.getPhone(), ven.getType(), ven.getName(),
                    ven.getEmail());
            em.persist(ve);
            em.flush();
            retVal = ve.getVendorno().intValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retVal;
    }
    
    public int[] getVendorNos() {
        
        int[] retVal = null;
        try {
            Query qry = em.createNamedQuery("VendorEntity.findAllVendorNos");
            List vens = qry.getResultList();
            retVal = new int[vens.size()];
            for(int i = 0; i < vens.size(); i++) {
                retVal[i] = ((Integer) vens.get(i)).intValue();
                //System.out.println(retVal[i]);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.out.println(retVal[4]);
        return retVal;
    }
    
    public ArrayList<PurchaseOrderLineItemDetails> getLineItem(int pono) {
        ArrayList<PurchaseOrderLineItemDetails> lineitems = new ArrayList<PurchaseOrderLineItemDetails>();
        
        try
        {
            Query qry = em.createNamedQuery("PurchaseOrderLineItemEntity.findByPonumber");
            qry.setParameter("pono", pono);
            List<PurchaseOrderLineItemEntity> itemdetails = qry.getResultList();
            
            for (int i = 0; i < itemdetails.size(); i++)
            {
                PurchaseOrderLineItemDetails oneLine = new PurchaseOrderLineItemDetails();
                oneLine.setPrice(itemdetails.get(i).getPrice());
                oneLine.setProdcd(itemdetails.get(i).getProdcd());
                oneLine.setQty(itemdetails.get(i).getQty());
                oneLine.setLineid(itemdetails.get(i).getLineid());
                lineitems.add(oneLine);
                //System.out.println(oneLine.getProdcd());
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error getting line items - " + ex.getMessage());
        }
        return lineitems;
    }
    
    public int[] getPONos(int ven) {
        
        int[] retVal = null;
        try {
            Query qry = em.createNamedQuery("PurchaseOrderEntity.findPONumbersByVendor");
            qry.setParameter("vendorno", ven);
            List ponos = qry.getResultList();
            retVal = new int[ponos.size()];
            for(int i = 0; i < ponos.size(); i++) {
                retVal[i] = ((Integer) ponos.get(i)).intValue();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return retVal;
    }
    
    public boolean addProduct(ProductDetails prod)
    {
        ProductEntity pe = null;
        boolean retVal = false;

        try {
           pe = new ProductEntity(prod.getProdcd(), prod.getVendorno(),
                    prod.getVensku(), prod.getProdname(),
                    prod.getCostprice(), prod.getMsrp(), prod.getRop(),
                    prod.getEoq(), prod.getQoh(), prod.getQoo(), prod.getQrcode());
            em.persist(pe);
            em.flush();
            retVal = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retVal;
    }
    
    public VendorDetails getVendor(int vendor)
    {
        VendorDetails details = new VendorDetails();
        try
        {
            System.out.println("In Vendor Details");
            Query qry = em.createNamedQuery("VendorEntity.findByVendorno");
            qry.setParameter("vendorno", vendor);
            VendorEntity ven = (VendorEntity) qry.getSingleResult();
            details.setVendorno(vendor);
            details.setAddress1(ven.getAddress1());
            details.setName(ven.getName());
            details.setCity(ven.getCity());
            details.setProvince(ven.getProvince());
            details.setPhone(ven.getPhone());
            details.setType(ven.getType());
            details.setEmail(ven.getEmail());
            details.setPostalCode(ven.getPostalcode());
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return details;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int addPO(ArrayList<PurchaseOrderLineItemDetails> lines, int ven, double amt) throws Exception {
        PurchaseOrderEntity poe = null;
        int retVal = -1;
        int retValDetails = -1;
        Date poDate = new java.util.Date();
        
        try {
            poe = new PurchaseOrderEntity(ven, amt, poDate);
            em.persist(poe);
            em.flush();
            retVal = poe.getPono().intValue();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        if (retVal > 0) {
            for (PurchaseOrderLineItemDetails detail : lines) {
                detail.setPono(retVal);
                retValDetails = addPOLine(detail);
                if (!(retValDetails > 0)) {
                    throw new Exception("Problem Adding LineItem");
                }
            }
        } else {
            throw new Exception("PO Not Generated");
        }
        
        retValDetails = -1;
        return retVal;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private int addPOLine(PurchaseOrderLineItemDetails line) {
        PurchaseOrderLineItemEntity pole = null;
        int retVal = -1;
        try {
            pole = new PurchaseOrderLineItemEntity(line.getPono(), line.getProdcd(), line.getQty(), line.getPrice());
            em.persist(pole);
            retVal = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return retVal;
    }
    
    public ArrayList<ProductDetails> getAllProductsForVendor(int vendorno)
    {
        ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
        
        try
        {
            Query qry = em.createNamedQuery("ProductEntity.findByVendorno");
            qry.setParameter("vendorno", vendorno);
            List<ProductEntity> prods = qry.getResultList();
            
            for (ProductEntity prod : prods)
            {
                ProductDetails oneProd = new ProductDetails();
                oneProd.setProdcd(prod.getProdcd());
                oneProd.setVendorno(prod.getVendorno());
                oneProd.setVensku(prod.getVensku());
                oneProd.setProdname(prod.getProdnam());
                oneProd.setCostprice(prod.getCostprice());
                oneProd.setMsrp(prod.getMsrp());
                oneProd.setRop(prod.getRop());
                oneProd.setEoq(prod.getEoq());
                oneProd.setQoh(prod.getQoh());
                oneProd.setQoo(prod.getQoo());
                products.add(oneProd);
                System.out.println(prod.getProdcd());
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return products;
    }
    
    public ProductDetails getProduct(String prodcd)
    {
        ProductDetails details = new ProductDetails();
        try
        {
            System.out.println("In Product Details");
            Query qry = em.createNamedQuery("ProductEntity.findByProdcd");
            qry.setParameter("prodcd", prodcd);
            ProductEntity prod = (ProductEntity) qry.getSingleResult();
            details.setProdcd(prodcd);
            details.setCostprice(prod.getCostprice());
            details.setEoq(prod.getEoq());
            details.setMsrp(prod.getMsrp());
            details.setProdname(prod.getProdnam());
            details.setQoh(prod.getQoh());
            details.setQoo(prod.getQoo());
            details.setQrcode(prod.getQrcode());
            details.setRop(prod.getRop());
            details.setVendorno(prod.getVendorno());
            details.setVensku(prod.getVensku());
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return details;
    }
    
    public PurchaseOrderDetails getPO(int pono)
    {
        PurchaseOrderDetails details = new PurchaseOrderDetails();
        try
        {
            System.out.println("In PurchaseOrderDetails");
            Query qry = em.createNamedQuery("PurchaseOrderEntity.findByPonumber");
            qry.setParameter("pono", pono);
            PurchaseOrderEntity po = (PurchaseOrderEntity) qry.getSingleResult();
            details.setDate(po.getDate());
            details.setAmount(po.getAmount());
            details.setPono(pono);
            details.setVendorno(po.getVendorno());
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return details;
    }
    
}
