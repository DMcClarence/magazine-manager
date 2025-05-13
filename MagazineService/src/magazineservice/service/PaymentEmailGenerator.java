/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.service;

import magazineservice.model.*;
import java.time.YearMonth;

/**
 *
 * @author 34085068
 */
public final class PaymentEmailGenerator implements Generatable<String> {

    private PayingCustomer recipient;
    private YearMonth period;

    /**
     *
     */
    public PaymentEmailGenerator() {
        this.recipient = null;
        this.period = null;
    }

    /**
     *
     * @param recipient
     * @param period
     */
    public PaymentEmailGenerator(PayingCustomer recipient, YearMonth period) {
        this.recipient = recipient;
        this.period = period;
    }

    /**
     *
     * @param recipient
     * @param month
     * @param year
     */
    public PaymentEmailGenerator(PayingCustomer recipient, int month, int year) {
        this.recipient = recipient;
        this.period = YearMonth.of(year, month);
    }

    /**
     *
     * @param recipient
     */
    public void setRecipient(PayingCustomer recipient) {
        this.recipient = recipient;
    }

    /**
     *
     * @param period
     */
    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    /**
     *
     * @param month
     * @param year
     */
    public void setPeriod(int month, int year) {
        this.period = YearMonth.of(year, month);
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

        if (this.period == null) {
            throw new NullPointerException("Payment Period must be set to use the generate method.");
        }

        StringBuilder paymentBreakdown = new StringBuilder();
        double subscriptionsCost = CostCalculator.calculateTotalCostForMonth(recipient.getMainMag(), recipient.getSuppMags(), period);

        paymentBreakdown.append(recipient.getName());
        paymentBreakdown.append(":\n");
        paymentBreakdown.append(recipient.getMainMag().getTitle());
        paymentBreakdown.append(" - $");
        paymentBreakdown.append(String.format("%.2f", CostCalculator.calculateTotalCostForMonth(recipient.getMainMag(), period)));
        paymentBreakdown.append("\n");

        for (SupplementMagazine sm : recipient.getSuppMags()) {
            paymentBreakdown.append(sm.getTitle());
            paymentBreakdown.append(" - $");
            paymentBreakdown.append(String.format("%.2f", CostCalculator.calculateTotalCostForMonth(sm, period)));
            paymentBreakdown.append("\n");
        }
        if (!recipient.getAssociates().isEmpty()) {
            paymentBreakdown.append("\n");
        }

        for (AssociateCustomer ac : recipient.getAssociates()) {
            subscriptionsCost += CostCalculator.calculateTotalCostForMonth(ac.getMainMag(), ac.getSuppMags(), period);

            paymentBreakdown.append(ac.getName());
            paymentBreakdown.append(":\n");
            paymentBreakdown.append(ac.getMainMag().getTitle());
            paymentBreakdown.append(" - $");
            paymentBreakdown.append(String.format("%.2f", CostCalculator.calculateTotalCostForMonth(ac.getMainMag(), period)));
            paymentBreakdown.append("\n");

            for (SupplementMagazine sm : ac.getSuppMags()) {
                paymentBreakdown.append(sm.getTitle());
                paymentBreakdown.append(" - $");
                paymentBreakdown.append(String.format("%.2f", CostCalculator.calculateTotalCostForMonth(sm, period)));
                paymentBreakdown.append("\n");
            }
            if (ac != recipient.getAssociates().getLast()) {
                paymentBreakdown.append("\n");
            }
        }

        return String.format("""
                             Dear %s,
                             
                             $%.2f will be charged to your designated payment method for the
                             following subscriptions for next month:
                             
                             %s""", recipient.getName(), subscriptionsCost, paymentBreakdown.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s; %s;}", recipient.toString(), period.toString());
    }
}
