/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

/**
 *
 * @author 34085068
 */
public class MagazineServiceDatabase {
    private CustomerDatabase customerDB;
    
    public MagazineServiceDatabase() {
        customerDB = new CustomerDatabase();
    }
    
    public CustomerDatabase getCustomerDatabase() {
        return this.customerDB;
    }
}
