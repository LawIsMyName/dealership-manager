package com.pluralsight.dealershipmanager.model;

import java.math.BigDecimal;

public class SalesContract extends Contract {

    private boolean financed;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financed) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financed = financed;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    public BigDecimal getProcessingFee() {
        BigDecimal price = getVehicleSold().getPrice();

        if (price.compareTo(new BigDecimal("10000")) < 0) {
            return new BigDecimal("295.00");
        }

        return new BigDecimal("495.00");
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal price = getVehicleSold().getPrice();
        BigDecimal salesTax = price.multiply(new BigDecimal("0.05"));
        BigDecimal recordingFee = new BigDecimal("100.00");

        return price.add(salesTax).add(recordingFee).add(getProcessingFee());
    }

}
