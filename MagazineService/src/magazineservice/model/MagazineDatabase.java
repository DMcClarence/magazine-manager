/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

/**
 *
 * @author 34085068
 */
public class MagazineDatabase {
    private MainMagazine mainMagazine = null;
    
    public MainMagazine getMainMagazine() {
        return this.mainMagazine;
    }
    
    public void createMainMagazine(String title, double weeklyCost) {
        if(mainMagazine == null) {
            this.mainMagazine = new MainMagazine(title, weeklyCost);
        }
    }
}
