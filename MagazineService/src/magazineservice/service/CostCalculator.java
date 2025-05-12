/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineservice.service;

import magazineservice.model.*;
import java.time.YearMonth;
import java.util.List;

/**
 *
 * @author 34085068
 */
public final class CostCalculator {

    /**
     *
     * @param mainMag
     * @param suppMags
     * @param period
     * @return
     */
    public static double calculateTotalCostForMonth(MainMagazine mainMag, List<SupplementMagazine> suppMags, YearMonth period) throws NullPointerException {
        if (mainMag == null) {
            throw new NullPointerException();
        }

        double cost = calculateTotalCostPerDay(mainMag, suppMags);

        switch (period.getMonthValue()) {
            case 2 -> {
                if (period.isLeapYear()) {
                    cost *= 29;
                } else {
                    cost *= 28;
                }
            }
            case 4, 6, 9, 11 ->
                cost *= 30;
            default ->
                cost *= 31;
        }

        return cost;
    }

    /**
     *
     * @param mag
     * @param period
     * @return
     */
    public static double calculateTotalCostForMonth(Magazine mag, YearMonth period) throws NullPointerException {
        if (mag == null) {
            throw new NullPointerException("Magazine must not be null.");
        }

        double cost = calculateTotalCostPerDay(mag);

        switch (period.getMonthValue()) {
            case 2 -> {
                if (period.isLeapYear()) {
                    cost *= 29;
                } else {
                    cost *= 28;
                }
            }
            case 4, 6, 9, 11 ->
                cost *= 30;
            default ->
                cost *= 31;
        }

        return cost;
    }

    /**
     *
     * @param mainMag
     * @param suppMags
     * @return
     */
    public static double calculateTotalCostPerDay(MainMagazine mainMag, List<SupplementMagazine> suppMags) throws NullPointerException {
        if (mainMag == null) {
            throw new NullPointerException("Main Magazine must not be null.");
        }

        double cost = mainMag.getWeeklyCost();

        if (suppMags != null || !suppMags.isEmpty()) {
            for (SupplementMagazine sm : suppMags) {
                cost += sm.getWeeklyCost();
            }
        }

        return (cost / 7.00);
    }

    /**
     *
     * @param mag
     * @return
     */
    public static double calculateTotalCostPerDay(Magazine mag) throws NullPointerException {
        if (mag == null) {
            throw new NullPointerException("Magazine must not be null.");
        }
        return (mag.getWeeklyCost() / 7.00);
    }
}
