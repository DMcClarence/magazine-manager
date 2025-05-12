/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 34085068
 */
public final class DirectDebit extends PaymentMethod {

    private String accountNumber;

    /**
     *
     * @param accountNumber
     */
    public DirectDebit(String accountNumber) throws IllegalArgumentException {
        setAccountNumber(accountNumber);
    }

    /**
     *
     * @return
     */
    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     *
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) throws IllegalArgumentException {
        if (accountNumber == null) {
            throw new IllegalArgumentException("Account Number must not be null.");
        }

        Pattern accountNumberFormat = Pattern.compile("[0-9]{8}");
        Matcher accountNumberMatch = accountNumberFormat.matcher(accountNumber);

        if (!accountNumberMatch.matches()) {
            throw new IllegalArgumentException("Account Number is invalid");
        }

        this.accountNumber = accountNumber;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s;}", accountNumber);
    }
}
