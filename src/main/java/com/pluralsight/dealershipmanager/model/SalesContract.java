package com.pluralsight.dealershipmanager.model;

public class SalesContract extends Contract {

    private boolean financed;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financed) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financed = financed;
    }
}
