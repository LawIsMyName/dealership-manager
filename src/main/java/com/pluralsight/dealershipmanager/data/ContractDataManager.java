package com.pluralsight.dealershipmanager.data;

import com.pluralsight.dealershipmanager.model.Contract;
import com.pluralsight.dealershipmanager.model.LeaseContract;
import com.pluralsight.dealershipmanager.model.SalesContract;
import com.pluralsight.dealershipmanager.model.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContractDataManager {

    public boolean saveContract(Contract contract) {
        Vehicle vehicle = contract.getVehicleSold();
        String date = LocalDate.parse(contract.getDate()).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String line = "";

        if (contract instanceof SalesContract) {
            line = "SALE";
        } else if (contract instanceof LeaseContract) {
            line = "LEASE";
        }

        line += "|" + date;
        line += "|" + contract.getCustomerName();
        line += "|" + contract.getCustomerEmail();
        line += "|" + vehicle.getVin();
        line += "|" + vehicle.getYear();
        line += "|" + vehicle.getMake();
        line += "|" + vehicle.getModel();
        line += "|" + vehicle.getVehicleType();
        line += "|" + vehicle.getColor();
        line += "|" + vehicle.getOdometer();
        line += "|" + formatMoney(vehicle.getPrice());

        if (contract instanceof SalesContract sale) {
            line += "|" + formatMoney(sale.getTotalPrice());
            line += "|" + formatMoney(sale.getMonthlyPayment());
        } else if (contract instanceof LeaseContract lease) {
            line += "|" + formatMoney(lease.getTotalPrice());
            line += "|" + formatMoney(lease.getMonthlyPayment());
        }

        try (FileWriter writer = new FileWriter("contracts.csv", true)) {

            writer.write(line + "\n");

            System.out.println("Contract saved successfully!");
            return true;

        } catch (IOException e) {
            System.out.println("Error saving contract.");
            return false;
        }
    }

    private String formatMoney(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

}
