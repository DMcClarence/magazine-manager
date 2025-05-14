/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import java.util.ArrayList;
import magazineservice.model.Customer;
import magazineservice.model.Magazine;
import magazineservice.model.MagazineServiceDatabase;
import magazineservice.model.MainMagazine;
import magazineservice.model.PayingCustomer;
import magazineservice.model.PaymentMethod;
import magazineservice.model.SupplementMagazine;

/**
 *
 * @author 34085068
 */
public class MagazineServiceDatabaseController {
    private MagazineServiceDatabase dbRef;
    private CustomerDatabaseController customerDBController;
    private MagazineDatabaseController magazineDBController;
    
    public MagazineServiceDatabaseController(MagazineServiceDatabase db) {
        setDatabaseRef(db);
        customerDBController = new CustomerDatabaseController(db.getCustomerDatabase());
        magazineDBController = new MagazineDatabaseController(db.getMagazineDatabase());
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
    
    public MainMagazine getMainMagazine() {
        return magazineDBController.getMainMagazine();
    }
    
    public SupplementMagazine getSupplementMagazine(String title) {
        return magazineDBController.getSupplementMagazine(title);
    }
    
    public Magazine getMagazine(String title) {
        return magazineDBController.getMagazine(title);
    }
    
    public ArrayList<SupplementMagazine> getAllSupplementMagazines() {
        return magazineDBController.getAllSupplementMagazines();
    }
    
    public ArrayList<Magazine> getAllMagazines() {
        return magazineDBController.getAllMagazines();
    }
    
//    public void createMainMagazine(String title, double weeklyCost) {
//        magazineDBController.createMainMagazine(title, weeklyCost);
//    }
    
    public void setDatabaseRef(MagazineServiceDatabase db) {
        this.dbRef = db;
    }
}
