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
public abstract class Magazine implements Serializable {

    /**
     *
     */
    protected String title;

    /**
     *
     */
    protected double weeklyCost;

    /**
     *
     * @param name
     * @param weeklyCost
     */
    public Magazine(String name, double weeklyCost) throws IllegalArgumentException {
        setName(name);
        setWeeklyCost(weeklyCost);
    }

    /**
     *
     * @return
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     *
     * @param title
     */
    public final void setName(String title) throws IllegalArgumentException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Magazine Name must not be null or empty");
        }
        this.title = title;
    }

    /**
     *
     * @return
     */
    public final double getWeeklyCost() {
        return this.weeklyCost;
    }

    /**
     *
     * @param weeklyCost
     * @throws IllegalArgumentException
     */
    public final void setWeeklyCost(double weeklyCost) throws IllegalArgumentException {
        if (weeklyCost < 0.0) {
            throw new IllegalArgumentException("Weekly Cost can not be negative.");
        } else {
            this.weeklyCost = weeklyCost;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{%s; %.2f;}", this.title, this.weeklyCost);
    }
}
