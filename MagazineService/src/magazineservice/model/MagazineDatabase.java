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
public class MagazineDatabase implements Serializable {
    private MainMagazine mainMagazine;
    private HashMap<String, SupplementMagazine> supplementMagazines;
    
    /**
     *
     * @param title
     * @param weeklyCost
     */
    public MagazineDatabase(String title, double weeklyCost) {
        mainMagazine = new MainMagazine(title, weeklyCost);
        supplementMagazines = new HashMap<String, SupplementMagazine>();
    }
    
    /**
     *
     * @return
     */
    public MainMagazine getMainMagazine() {
        return this.mainMagazine;
    }
    
    /**
     *
     * @return
     */
    public HashMap<String, SupplementMagazine> getSupplementMagazines() {
        return this.supplementMagazines;
    }
}
