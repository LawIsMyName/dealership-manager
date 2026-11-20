package com.pluralsight.dealershipmanager;

import com.pluralsight.dealershipmanager.data.ContractDataManager;
import com.pluralsight.dealershipmanager.model.LeaseContract;
import com.pluralsight.dealershipmanager.model.SalesContract;
import com.pluralsight.dealershipmanager.model.Vehicle;
import com.pluralsight.dealershipmanager.ui.UserInterface;

import java.math.BigDecimal;

public class Program {
    public static void main(String[] args) {
        Vehicle saleVehicle = new Vehicle(10001, 2020, "Ford", "Focus", "Sedan", "Blue", 90000, new BigDecimal("995.00"));

        SalesContract sale = new SalesContract("2026-11-20", "Test Customer", "test@example.com", saleVehicle, false);

        System.out.println("Sale Total: $" + sale.getTotalPrice());
        System.out.println("Sale Monthly Payment: $" + sale.getMonthlyPayment());

        Vehicle leaseVehicle = new Vehicle(10002, 2024, "Toyota", "Camry", "Sedan", "Red", 10000, new BigDecimal("31995.00"));

        LeaseContract lease = new LeaseContract("2026-11-20", "Test Customer", "test@example.com", leaseVehicle);

        System.out.println("Lease Total: $" + lease.getTotalPrice());
        System.out.println("Lease Monthly Payment: $" + lease.getMonthlyPayment());

        UserInterface userInterface = new UserInterface();

        userInterface.display();

        ContractDataManager manager = new ContractDataManager();

        manager.saveContract(sale);
    }
}



