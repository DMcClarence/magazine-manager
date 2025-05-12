/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

/**
 *
 * @author 34085068
 */
public abstract class PaymentMethod implements Payable {

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
