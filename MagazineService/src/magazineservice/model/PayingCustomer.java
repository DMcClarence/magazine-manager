/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.util.ArrayList;

/**
 *
 * @author 34085068
 */
public class PayingCustomer extends Customer {

    private final ArrayList<AssociateCustomer> associates = new ArrayList<AssociateCustomer>();
    private PaymentMethod paymentMethod;

    /**
     *
     * @param name
     * @param email
     * @param mainMag
     * @param paymentMethod
     */
    public PayingCustomer(String name, String email, MainMagazine mainMag, PaymentMethod paymentMethod) throws IllegalArgumentException {
        super(name, email, mainMag);
        setPaymentMethod(paymentMethod);
    }

    /**
     *
     * @return
     */
    public ArrayList<AssociateCustomer> getAssociates() {
        return this.associates;
    }

    /**
     *
     * @return
     */
    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     *
     * @param paymentMethod
     * @throws IllegalArgumentException
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) throws IllegalArgumentException {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment Method must not be null.");
        }

        this.paymentMethod = paymentMethod;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s; %s; %s; %s; %s;}", this.name, this.email, this.mainMag.toString(), this.associates.toString(), this.paymentMethod.toString());
    }
}
