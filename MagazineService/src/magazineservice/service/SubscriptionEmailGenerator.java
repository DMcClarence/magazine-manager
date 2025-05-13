/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.service;

import magazineservice.model.Customer;
import magazineservice.model.SupplementMagazine;

/**
 *
 * @author 34085068
 */
public final class SubscriptionEmailGenerator implements Generatable<String> {

    private Customer recipient;

    /**
     *
     */
    public SubscriptionEmailGenerator() {
        this.recipient = null;
    }

    /**
     *
     * @param recipient
     */
    public SubscriptionEmailGenerator(Customer recipient) {
        this.recipient = recipient;
    }

    /**
     *
     * @param recipient
     */
    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }

    /**
     *
     * @return @throws NullPointerException
     */
    @Override
    public String generate() throws NullPointerException {
        if (this.recipient == null) {
            throw new NullPointerException("Recipient must be set to use the generate method.");
        }

        StringBuilder magazineBreakdown = new StringBuilder();

        for (SupplementMagazine sm : recipient.getSuppMags()) {
            magazineBreakdown.append("- ");
            magazineBreakdown.append(sm.getTitle());
            magazineBreakdown.append("\n");
        }

        return String.format("""
                             Dear %s,
                             
                             Your copy of %s and your subscribed supplements are 
                             ready for this week.
                             
                             Here is a list of the supplements you are currently subscribed 
                             to for this month:
                             
                             %s""", recipient.getName(), recipient.getMainMag().getTitle(), magazineBreakdown.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s;}", recipient.toString());
    }
}
