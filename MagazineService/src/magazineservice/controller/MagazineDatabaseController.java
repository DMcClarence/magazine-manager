/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import java.util.ArrayList;
import magazineservice.model.Magazine;
import magazineservice.model.MagazineDatabase;
import magazineservice.model.MainMagazine;
import magazineservice.model.SupplementMagazine;

/**
 *
 * @author 34085068
 */
public class MagazineDatabaseController {
    private MagazineDatabase dbRef;
    
    /**
     *
     * @param db
     */
    public MagazineDatabaseController(MagazineDatabase db) {
        this.dbRef = db;
    }
    
    /**
     *
     * @return
     */
    public MainMagazine getMainMagazine() {
        return dbRef.getMainMagazine();
    }
    
    /**
     *
     * @param title
     * @return
     */
    public SupplementMagazine getSupplementMagazine(String title) {
        return dbRef.getSupplementMagazines().get(title);
    }
    
    /**
     *
     * @param title
     * @return
     */
    public Magazine getMagazine(String title) {
        if(title.equalsIgnoreCase(dbRef.getMainMagazine().getTitle())) {
            return dbRef.getMainMagazine();
        }
        
        return dbRef.getSupplementMagazines().get(title);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<SupplementMagazine> getAllSupplementMagazines() {
        return new ArrayList<SupplementMagazine>(dbRef.getSupplementMagazines().values());
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Magazine> getAllMagazines() {
        ArrayList<Magazine> magazines = new ArrayList<Magazine>();
        
        magazines.add(dbRef.getMainMagazine());
        magazines.addAll(dbRef.getSupplementMagazines().values());
        
        return magazines;
    }
    
    /**
     *
     * @param supplementMagazine
     */
    public void addSupplementMagazine(SupplementMagazine supplementMagazine) {
        dbRef.getSupplementMagazines().put(supplementMagazine.getTitle(), supplementMagazine);
    }
    
    /**
     *
     * @param title
     */
    public void removeSupplementMagazine(String title) {
        dbRef.getSupplementMagazines().remove(title);
    }
}
