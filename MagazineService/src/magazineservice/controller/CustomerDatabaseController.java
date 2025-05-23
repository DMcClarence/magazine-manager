/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import java.util.ArrayList;
import magazineservice.model.AssociateCustomer;
import magazineservice.model.Customer;
import magazineservice.model.CustomerDatabase;
import magazineservice.model.PayingCustomer;

/**
 *
 * @author 34085068
 */
public class CustomerDatabaseController {
    private CustomerDatabase dbRef;
    private PayingCustomerController payingCustomerController;
    private AssociateCustomerController associateCustomerController;
    
    /**
     *
     * @param db
     */
    public CustomerDatabaseController(CustomerDatabase db) {
        setDatabaseRef(db);
        payingCustomerController = new PayingCustomerController();
        associateCustomerController = new AssociateCustomerController();
    }
    
    /**
     *
     * @param customer
     */
    public void addCustomer(Customer customer) {
        dbRef.getDatabase().put(customer.getEmail(), customer);
        
        if(customer instanceof AssociateCustomer) {
            try {
                payingCustomerController.addAssociateCustomer(((AssociateCustomer)customer).getBenefactor(), (AssociateCustomer)customer);
            }
            catch(ClassCastException cce) {
                cce.printStackTrace();
            }
        }
    }
    
    /**
     *
     * @param email
     */
    public void removeCustomer(String email) {
        try {
            if(dbRef.getDatabase().get(email) instanceof PayingCustomer) {
                ArrayList<AssociateCustomer> temp = new ArrayList<AssociateCustomer>(((PayingCustomer)dbRef.getDatabase().get(email)).getAssociates());
                for(AssociateCustomer ac : temp) {
                    removeCustomer(ac.getEmail());
                }
            }
            else if(dbRef.getDatabase().get(email) instanceof AssociateCustomer) {
                payingCustomerController.removeAssociateCustomer(((AssociateCustomer)dbRef.getDatabase().get(email)).getBenefactor(), (AssociateCustomer)dbRef.getDatabase().get(email));
            }
        }
        catch(ClassCastException cce) {
            cce.printStackTrace();
        }

        dbRef.getDatabase().remove(email);
    }
    
    /**
     *
     * @param email
     * @return
     */
    public Customer getCustomer(String email) {
        return dbRef.getDatabase().get(email);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<PayingCustomer> getAllPayingCustomers() {
        ArrayList<PayingCustomer> payingCustomers = new ArrayList<PayingCustomer>();
        
        for(Customer c : dbRef.getDatabase().values()) {
            try {
                payingCustomers.add((PayingCustomer)c);
            }
            catch(ClassCastException cce) {
                // Not a PayingCustomer, so move on.
            }
        }
        
        return payingCustomers;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<Customer>(dbRef.getDatabase().values());
    }
    
    /**
     *
     * @param associateEmail
     * @param oldBenefactorEmail
     * @param newBenefactorEmail
     */
    public void changeBenefactor(String associateEmail, String oldBenefactorEmail, String newBenefactorEmail) {
        try {
            payingCustomerController.removeAssociateCustomer((PayingCustomer)dbRef.getDatabase().get(oldBenefactorEmail), (AssociateCustomer)dbRef.getDatabase().get(associateEmail));
            payingCustomerController.addAssociateCustomer((PayingCustomer)dbRef.getDatabase().get(newBenefactorEmail), (AssociateCustomer)dbRef.getDatabase().get(associateEmail));
            ((AssociateCustomer)dbRef.getDatabase().get(associateEmail)).setBenefactor((PayingCustomer)dbRef.getDatabase().get(newBenefactorEmail));
        }
        catch(IllegalArgumentException iae) {
            iae.printStackTrace();
        } 
    }
    
    /**
     *
     * @param db
     */
    public void setDatabaseRef(CustomerDatabase db) {
        this.dbRef = db;
    }
}
