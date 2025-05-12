/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package magazineservice.controller;

import magazineservice.model.Customer;
import magazineservice.model.SupplementMagazine;


/**
 *
 * @author 34085068
 */
public interface CustomerController {

    /**
     *
     * @param customer
     * @param suppMag
     */
    void subscribeToSupplementMagazine(Customer customer, SupplementMagazine suppMag);

    /**
     *
     * @param customer
     * @param suppMag
     */
    void unsubscribeFromSupplementMagazine(Customer customer, SupplementMagazine suppMag);
}
