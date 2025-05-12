/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

/**
 *
 * @author 34085068
 */
public class AssociateCustomer extends Customer {

    private PayingCustomer benefactor;

    /**
     *
     * @param name
     * @param email
     * @param mainMag
     * @param benefactor
     */
    public AssociateCustomer(String name, String email, MainMagazine mainMag, PayingCustomer benefactor) throws IllegalArgumentException {
        super(name, email, mainMag);
        setBenefactor(benefactor);
    }

    /**
     *
     * @return
     */
    public final PayingCustomer getBenefactor() {
        return this.benefactor;
    }

    /**
     *
     * @param benefactor
     */
    public final void setBenefactor(PayingCustomer benefactor) throws IllegalArgumentException {
        if (benefactor == null) {
            throw new IllegalArgumentException("Benefactor must not be null.");
        } else {
            this.benefactor = benefactor;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s; %s; %s; {%s; %s};}", this.name, this.email, this.mainMag.toString(), this.benefactor.getName(), this.benefactor.getEmail());
    }
}
