/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import magazineservice.model.MagazineDatabase;
import magazineservice.model.MainMagazine;

/**
 *
 * @author 34085068
 */
public class MagazineDatabaseController {
    private MagazineDatabase dbRef;
    
    public MagazineDatabaseController(MagazineDatabase db) {
        this.dbRef = db;
    }
    
    public MainMagazine getMainMagazine() {
        return dbRef.getMainMagazine();
    }
    
    public void createMainMagazine(String title, double weeklyCost) {
        dbRef.createMainMagazine(title, weeklyCost);
    }
}
