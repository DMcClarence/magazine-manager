/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author 34085068
 */
public class CustomerDatabase implements Serializable {
    private HashMap<String, Customer> db;
    
    public CustomerDatabase() {
        db = new HashMap<String, Customer>();
    }
    
    public HashMap<String, Customer> getDatabase() {
        return this.db;
    }
}
