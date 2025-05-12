/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.controller;

import magazineservice.model.*;
import magazineservice.model.SupplementMagazine;

/**
 *
 * @author 34085068
 */
public class PayingCustomerController implements CustomerController {

    /**
     *
     * @param benefactor
     * @param associate
     */
    public void addAssociateCustomer(PayingCustomer benefactor, AssociateCustomer associate) {
        benefactor.getAssociates().add(associate);
    }

    /**
     *
     * @param benefactor
     * @param associate
     */
    public void removeAssociateCustomer(PayingCustomer benefactor, AssociateCustomer associate) {
        benefactor.getAssociates().remove(associate);
    }

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
