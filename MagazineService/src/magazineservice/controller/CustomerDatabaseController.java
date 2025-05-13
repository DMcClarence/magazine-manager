/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import java.util.ArrayList;
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
    
    public CustomerDatabaseController(CustomerDatabase db) {
        setDatabaseRef(db);
        payingCustomerController = new PayingCustomerController();
        associateCustomerController = new AssociateCustomerController();
    }
    
    public void addCustomer(Customer customer) {
        dbRef.getDatabase().put(customer.getEmail(), customer);
    }
    
    public void removeCustomer(String email) {
        dbRef.getDatabase().remove(email);
    }
    
    public Customer getCustomer(String email) {
        return dbRef.getDatabase().get(email);
    }
    
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
    
    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<Customer>(dbRef.getDatabase().values());
    }
    
    public void setDatabaseRef(CustomerDatabase db) {
        this.dbRef = db;
    }
}
