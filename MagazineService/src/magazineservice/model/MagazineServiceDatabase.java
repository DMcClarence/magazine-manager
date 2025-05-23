/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.HashMap;

/**
 *
 * @author 34085068
 */
public class MagazineServiceDatabase implements Serializable {
    private CustomerDatabase customerDB;
    private MagazineDatabase magazineDB;
    private HashMap<String, Integer> supplementSubCounts;
    private HashMap<String, HashMap<YearMonth, String>> emailDatabase;
    
    /**
     *
     * @param title
     * @param weeklyCost
     */
    public MagazineServiceDatabase(String title, double weeklyCost) {
        customerDB = new CustomerDatabase();
        magazineDB = new MagazineDatabase(title, weeklyCost);
        supplementSubCounts = new HashMap<String, Integer>();
        emailDatabase = new HashMap<String, HashMap<YearMonth, String>>();
    }
    
    /**
     *
     * @return
     */
    public CustomerDatabase getCustomerDatabase() {
        return this.customerDB;
    }
    
    /**
     *
     * @return
     */
    public MagazineDatabase getMagazineDatabase() {
        return this.magazineDB;
    }
    
    /**
     *
     * @return
     */
    public HashMap<String, Integer> getSupplementSubCounts() {
        return this.supplementSubCounts;
    }
    
    /**
     *
     * @return
     */
    public HashMap<String, HashMap<YearMonth, String>> getEmailDatabase() {
        return this.emailDatabase;
    }
}
