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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    /**
     *
     */
    public MagazineServiceDatabaseController() {
        dbRef = null;
        customerDBController = null;
        magazineDBController = null;
    }
    
    /**
     *
     * @param db
     */
    public MagazineServiceDatabaseController(MagazineServiceDatabase db) {
        setDatabaseRef(db);
    }
    
    /**
     *
     * @param customer
     */
    public void addCustomer(Customer customer) {
        customerDBController.addCustomer(customer);
    }
    
    /**
     *
     * @param supplementMagazine
     */
    public void addSupplementMagazine(SupplementMagazine supplementMagazine) {
        magazineDBController.addSupplementMagazine(supplementMagazine);
        dbRef.getSupplementSubCounts().put(supplementMagazine.getTitle(), 0);
    }
    
    /**
     *
     * @param title
     */
    public void removeSupplementMagazine(String title) {
        magazineDBController.removeSupplementMagazine(title);
        if(dbRef.getSupplementSubCounts().get(title) <= 0) {
            dbRef.getSupplementSubCounts().remove(title);
        }
    }
    
    /**
     *
     * @param email
     */
    public void removeCustomer(String email) {
        ArrayList<SupplementMagazine> temp = new ArrayList<SupplementMagazine>(customerDBController.getCustomer(email).getSuppMags());
        for(SupplementMagazine sm : temp) {
            removeFromSubscription(sm.getTitle(), email);
        }
        dbRef.getEmailDatabase().remove(email);
        customerDBController.removeCustomer(email);
    }
    
    /**
     *
     * @param email
     * @return
     */
    public Customer getCustomer(String email) {
        return customerDBController.getCustomer(email);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<PayingCustomer> getAllPayingCustomers() {
        return customerDBController.getAllPayingCustomers();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Customer> getAllCustomers() {
        return customerDBController.getAllCustomers();
    }
    
    /**
     *
     * @return
     */
    public MainMagazine getMainMagazine() {
        return magazineDBController.getMainMagazine();
    }
    
    /**
     *
     * @param title
     * @return
     */
    public SupplementMagazine getSupplementMagazine(String title) {
        return magazineDBController.getSupplementMagazine(title);
    }
    
    /**
     *
     * @param title
     * @return
     */
    public Magazine getMagazine(String title) {
        return magazineDBController.getMagazine(title);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<SupplementMagazine> getAllSupplementMagazines() {
        return magazineDBController.getAllSupplementMagazines();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Magazine> getAllMagazines() {
        return magazineDBController.getAllMagazines();
    }
    
    /**
     *
     * @param db
     */
    public void setDatabaseRef(MagazineServiceDatabase db) {
        this.dbRef = db;
        customerDBController = new CustomerDatabaseController(db.getCustomerDatabase());
        magazineDBController = new MagazineDatabaseController(db.getMagazineDatabase());
    }
    
    /**
     *
     * @param dbFile
     */
    public void serializeDB(File dbFile) {
        try {
           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dbFile));
           out.writeObject(dbRef);
           out.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     *
     * @param dbFile
     * @return
     */
    public MagazineServiceDatabase deserializeDB(File dbFile) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(dbFile));
            setDatabaseRef((MagazineServiceDatabase)in.readObject());
            in.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        
        return dbRef;
    }
    
    /**
     *
     * @param title
     * @param email
     */
    public void addToSubscription(String title, String email) {
        if(!customerDBController.getCustomer(email).getSuppMags().contains(magazineDBController.getSupplementMagazine(title))) {
            customerDBController.getCustomer(email).getSuppMags().add(magazineDBController.getSupplementMagazine(title));
            dbRef.getSupplementSubCounts().replace(title, dbRef.getSupplementSubCounts().get(title) + 1);
        }

    }
    
    /**
     *
     * @param title
     * @param email
     */
    public void removeFromSubscription(String title, String email) {
        if(customerDBController.getCustomer(email).getSuppMags().contains(magazineDBController.getSupplementMagazine(title))) {
            customerDBController.getCustomer(email).getSuppMags().remove(magazineDBController.getSupplementMagazine(title));
            dbRef.getSupplementSubCounts().replace(title, dbRef.getSupplementSubCounts().get(title) - 1);
        }
    }
    
    /**
     *
     * @param email
     * @param title
     * @return
     */
    public boolean isSubscribedToSupplement(String email, String title) {
        return customerDBController.getCustomer(email).getSuppMags().contains(magazineDBController.getSupplementMagazine(title));
    }
    
    /**
     *
     * @param title
     * @return
     */
    public int getNumOfSubbedCustomers(String title) {
        return dbRef.getSupplementSubCounts().get(title);
    }
    
    /**
     *
     * @param email
     * @param nextMonth
     * @param emailBody
     */
    public void saveEmail(String email, YearMonth nextMonth, String emailBody) {
        if(!dbRef.getEmailDatabase().containsKey(email)) {
            dbRef.getEmailDatabase().put(email, new HashMap<YearMonth, String>());
        }
        
        HashMap<YearMonth, String> temp = dbRef.getEmailDatabase().get(email);
        temp.put(nextMonth, emailBody);
    }
    
    /**
     *
     * @param email
     * @return
     */
    public HashMap<YearMonth, String> getBillingHistoryForCustomer(String email) {
        return dbRef.getEmailDatabase().get(email);
    }
    
    /**
     *
     * @param associateEmail
     * @param oldBenefactorEmail
     * @param newBenefactorEmail
     */
    public void changeBenefactor(String associateEmail, String oldBenefactorEmail, String newBenefactorEmail) {
        customerDBController.changeBenefactor(associateEmail, oldBenefactorEmail, newBenefactorEmail);
    }
}
