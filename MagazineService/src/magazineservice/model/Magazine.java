/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.model;

/**
 *
 * @author 34085068
 */
public abstract class Magazine {

    /**
     *
     */
    protected String name;

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
    public final String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public final void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Magazine Name must not be null or empty");
        }
        this.name = name;
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
        return String.format("{%s; %.2f;}", this.name, this.weeklyCost);
    }
}
