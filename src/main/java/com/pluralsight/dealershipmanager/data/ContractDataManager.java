package com.pluralsight.dealershipmanager.data;

import com.pluralsight.dealershipmanager.model.Contract;
import com.pluralsight.dealershipmanager.model.Vehicle;

public class ContractDataManager {

    public void saveContract(Contract contract) {
        Vehicle vehicle = contract.getVehicleSold();
    }
}
