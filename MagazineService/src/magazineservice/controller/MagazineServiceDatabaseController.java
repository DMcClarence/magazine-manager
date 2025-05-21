/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import magazineservice.model.Customer;
import magazineservice.model.Magazine;
import magazineservice.model.MagazineServiceDatabase;
import magazineservice.model.MainMagazine;
import magazineservice.model.PayingCustomer;
import magazineservice.model.SupplementMagazine;

/**
 *
 * @author 34085068
 */
public class MagazineServiceDatabaseController {
    private MagazineServiceDatabase dbRef;
    private CustomerDatabaseController customerDBController;
    private MagazineDatabaseController magazineDBController;
    
    public MagazineServiceDatabaseController() {
        dbRef = null;
        customerDBController = null;
        magazineDBController = null;
    }
    
    public MagazineServiceDatabaseController(MagazineServiceDatabase db) {
        setDatabaseRef(db);
    }
    
    public void addCustomer(Customer customer) {
        customerDBController.addCustomer(customer);
    }
    
    public void addSupplementMagazine(SupplementMagazine supplementMagazine) {
        magazineDBController.addSupplementMagazine(supplementMagazine);
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
    
    public void setDatabaseRef(MagazineServiceDatabase db) {
        this.dbRef = db;
        customerDBController = new CustomerDatabaseController(db.getCustomerDatabase());
        magazineDBController = new MagazineDatabaseController(db.getMagazineDatabase());
    }
    
    public void serializeDB(File dbFile) {
        try {
           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dbFile));
           out.writeObject(dbRef);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public MagazineServiceDatabase deserializeDB(File dbFile) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(dbFile));
            setDatabaseRef((MagazineServiceDatabase)in.readObject());
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        
        return dbRef;
    }
    
    public void addToSubscription(String title, String email) {
        if(!customerDBController.getCustomer(email).getSuppMags().contains(magazineDBController.getSupplementMagazine(title))) {
            customerDBController.getCustomer(email).getSuppMags().add(magazineDBController.getSupplementMagazine(title));
        }
        
    }
    
    public void removeFromSubscription(String title, String email) {
        customerDBController.getCustomer(email).getSuppMags().remove(magazineDBController.getSupplementMagazine(title));
    }
    
    public boolean isSubscribedToSupplement(String email, String title) {
        return customerDBController.getCustomer(email).getSuppMags().contains(magazineDBController.getSupplementMagazine(title));
    }
}
