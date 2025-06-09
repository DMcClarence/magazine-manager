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
public abstract class PaymentMethod implements Payable, Serializable {

    /**
     *
     */
    public PaymentMethod() {
    }

    /**
     *
     * @return
     */
    @Override
    public abstract String toString();
}
