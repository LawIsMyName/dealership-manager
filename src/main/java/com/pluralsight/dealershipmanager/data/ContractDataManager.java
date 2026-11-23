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
            BigDecimal price = sale.getVehicleSold().getPrice();
            BigDecimal salesTax = price.multiply(new BigDecimal("0.05"));
            BigDecimal recordingFee = new BigDecimal("100.00");

            line += "|" + formatMoney(salesTax);
            line += "|" + formatMoney(recordingFee);
            line += "|" + formatMoney(sale.getProcessingFee());
            line += "|" + formatMoney(sale.getTotalPrice());
            line += "|" + (sale.isFinanced() ? "YES" : "NO");
            line += "|" + formatMoney(sale.getMonthlyPayment());

        } else if (contract instanceof LeaseContract lease) {
            BigDecimal price = lease.getVehicleSold().getPrice();

            BigDecimal endingValue = price.multiply(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal leaseFee = price.multiply(new BigDecimal("0.07")).setScale(2, RoundingMode.HALF_UP);

            line += "|" + formatMoney(endingValue);
            line += "|" + formatMoney(leaseFee);
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
