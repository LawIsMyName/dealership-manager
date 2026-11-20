package com.pluralsight.dealershipmanager.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaseContract extends Contract{

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal price = getVehicleSold().getPrice();

        BigDecimal endingValue = price.multiply(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal leaseFee = price.multiply(new BigDecimal("0.07")).setScale(2, RoundingMode.HALF_UP);

        return endingValue.add(leaseFee);
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return LoanCalculator.calculateMonthlyPayment(getTotalPrice(), new BigDecimal("4.0"), 36);
    }
}


