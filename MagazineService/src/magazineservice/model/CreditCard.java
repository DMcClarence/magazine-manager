/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 34085068
 */
public final class CreditCard extends PaymentMethod {

    private String cardNumber;
    private YearMonth expiry;

    /**
     *
     * @param cardNumber
     * @param expiry
     */
    public CreditCard(String cardNumber, YearMonth expiry) throws IllegalArgumentException {
        setCardNumber(cardNumber);
        setExpiry(expiry);
    }

    /**
     *
     * @param cardNumber
     * @param month
     * @param year
     */
    public CreditCard(String cardNumber, int year, int month) throws IllegalArgumentException {
        setCardNumber(cardNumber);
        setExpiry(year, month);
    }

    /**
     *
     * @return
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /**
     *
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) throws IllegalArgumentException {
        if (cardNumber == null) {
            throw new IllegalArgumentException("Card Number must not be null.");
        }

        Pattern cardNumberFormat = Pattern.compile("[0-9]{16}");
        Matcher cardNumberMatch = cardNumberFormat.matcher(cardNumber);

        if (!cardNumberMatch.matches()) {
            throw new IllegalArgumentException("Card Number is invalid");
        }

        this.cardNumber = cardNumber;
    }

    /**
     *
     * @return
     */
    public YearMonth getExpiry() {
        return this.expiry;
    }

    /**
     *
     * @param expiry
     */
    public void setExpiry(YearMonth expiry) throws IllegalArgumentException {
        if (expiry == null) {
            throw new IllegalArgumentException("Expiry must not be null.");
        }

        YearMonth current = YearMonth.now();
        if (expiry.isBefore(current)) {
            throw new IllegalArgumentException("Expiry must be a future date.");
        }

        this.expiry = expiry;
    }

    /**
     *
     * @param year
     * @param month
     */
    public void setExpiry(int year, int month) throws IllegalArgumentException {
        YearMonth current = YearMonth.now();
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Expiry Month must be a value between 1 and 12.");
        }
        if (year < current.getYear()
                || (year == current.getYear() && month < current.getMonthValue())) {
            throw new IllegalArgumentException("Expiry must be a future date.");
        }

        this.expiry = YearMonth.of(year, month);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s, %s;}", this.cardNumber, this.expiry.toString());
    }
}
