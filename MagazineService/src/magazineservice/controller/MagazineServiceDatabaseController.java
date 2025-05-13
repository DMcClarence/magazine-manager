/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import java.util.ArrayList;
import magazineservice.model.Customer;
import magazineservice.model.MagazineServiceDatabase;
import magazineservice.model.MainMagazine;
import magazineservice.model.PayingCustomer;
import magazineservice.model.PaymentMethod;

/**
 *
 * @author 34085068
 */
public class MagazineServiceDatabaseController {
    private MagazineServiceDatabase dbRef;
    private CustomerDatabaseController customerDBController;
    
    public MagazineServiceDatabaseController(MagazineServiceDatabase db) {
        setDatabaseRef(db);
        customerDBController = new CustomerDatabaseController(db.getCustomerDatabase());
    }
    
    public void addCustomer(String name, String email, MainMagazine mainMag, PaymentMethod paymentMethod) {
        PayingCustomer customer = new PayingCustomer(name, email, mainMag, paymentMethod);
        customerDBController.addCustomer(customer);
    }
    
    public void removeCustomer(String email) {
        customerDBController.removeCustomer(email);
    }
    
    public Customer getCustomer(String email) {
        return customerDBController.getCustomer(email);
    }
    
    public ArrayList<PayingCustomer> getAllPayingCustomers() {
        return customerDBController.getAllPayingCustomers();
    }
    
    public ArrayList<Customer> getAllCustomers() {
        return customerDBController.getAllCustomers();
    }
    
    public void setDatabaseRef(MagazineServiceDatabase db) {
        this.dbRef = db;
    }
}
