/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.io.Serializable;

/**
 *
 * @author 34085068
 */
public class MagazineServiceDatabase implements Serializable {
    private CustomerDatabase customerDB;
    private MagazineDatabase magazineDB;
    
    public MagazineServiceDatabase(String title, double weeklyCost) {
        customerDB = new CustomerDatabase();
        magazineDB = new MagazineDatabase(title, weeklyCost);
    }
    
    public CustomerDatabase getCustomerDatabase() {
        return this.customerDB;
    }
    
    public MagazineDatabase getMagazineDatabase() {
        return this.magazineDB;
    }
}
