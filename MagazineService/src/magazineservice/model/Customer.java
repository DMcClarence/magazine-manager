/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 34085068
 */
public abstract class Customer {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected String email;

    /**
     *
     */
    protected MainMagazine mainMag;

    /**
     *
     */
    protected final ArrayList<SupplementMagazine> suppMags = new ArrayList<SupplementMagazine>();

    /**
     *
     * @param name
     * @param email
     * @param mainMag
     * @throws IllegalArgumentException
     */
    public Customer(String name, String email, MainMagazine mainMag) throws IllegalArgumentException {
        setName(name);
        setEmail(email);
        setMainMag(mainMag);
    }

    /**
     *
     * @return
     */
    public final String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public final void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Customer Name must not be null or empty.");
        } else {
            this.name = name;
        }
    }

    /**
     *
     * @return
     */
    public final String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email
     */
    public final void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Customer Email must not be null or empty.");
        }

        Pattern emailFormat = Pattern.compile("[a-z0-9.?!\\{\\}~_\\-+/]+@[a-z0-9\\-]+.[a-z\\-.]+", Pattern.CASE_INSENSITIVE);
        Matcher emailMatch = emailFormat.matcher(email);

        if (!emailMatch.matches()) {
            throw new IllegalArgumentException("Email Format is invalid");
        }

        this.email = email;
    }

    /**
     *
     * @return
     */
    public final MainMagazine getMainMag() {
        return this.mainMag;
    }

    /**
     *
     * @param mainMag
     */
    public final void setMainMag(MainMagazine mainMag) {
        if (mainMag == null) {
            throw new IllegalArgumentException("Main Magazine must not be null.");
        } else {
            this.mainMag = mainMag;
        }
    }

    /**
     *
     * @return
     */
    public final ArrayList<SupplementMagazine> getSuppMags() {
        return this.suppMags;
    }

    /**
     *
     * @return
     */
    @Override
    public abstract String toString();
}
