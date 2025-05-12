/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import magazineservice.model.Customer;
import magazineservice.model.SupplementMagazine;

/**
 *
 * @author 34085068
 */
public class AssociateCustomerController implements CustomerController {

    /**
     *
     * @param customer
     * @param suppMag
     */
    @Override
    public void subscribeToSupplementMagazine(Customer customer, SupplementMagazine suppMag) {
        customer.getSuppMags().add(suppMag);
    }

    /**
     *
     * @param customer
     * @param suppMag
     */
    @Override
    public void unsubscribeFromSupplementMagazine(Customer customer, SupplementMagazine suppMag) {
        customer.getSuppMags().remove(suppMag);
    }
}
