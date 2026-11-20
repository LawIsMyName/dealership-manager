package com.pluralsight.dealershipmanager.data;

import com.pluralsight.dealershipmanager.model.Contract;
import com.pluralsight.dealershipmanager.model.LeaseContract;
import com.pluralsight.dealershipmanager.model.SalesContract;
import com.pluralsight.dealershipmanager.model.Vehicle;

import java.time.format.DateTimeFormatter;

public class ContractDataManager {

    public void saveContract(Contract contract) {
        Vehicle vehicle = contract.getVehicleSold();
        String date = contract.getDate().format(String.valueOf(DateTimeFormatter.ofPattern("yyyyMMdd")));
        String line = "";

        if (contract instanceof SalesContract) {
            line = "SALE";
        } else if (contract instanceof LeaseContract) {
            line = "LEASE";
        }
    }
}
