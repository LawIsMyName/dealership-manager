package com.pluralsight.dealershipmanager.data;

import com.pluralsight.dealershipmanager.model.Contract;
import com.pluralsight.dealershipmanager.model.Vehicle;

import java.time.format.DateTimeFormatter;

public class ContractDataManager {

    public void saveContract(Contract contract) {
        Vehicle vehicle = contract.getVehicleSold();
        String date = contract.getContractDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
