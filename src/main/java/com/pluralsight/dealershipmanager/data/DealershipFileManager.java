package com.pluralsight.dealershipmanager.data;

import com.pluralsight.dealershipmanager.model.Dealership;
import com.pluralsight.dealershipmanager.model.Vehicle;

import java.io.*;
import java.math.BigDecimal;

public class DealershipFileManager {

    public Dealership getDealership() {

        Dealership dealership = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"));

            String line = reader.readLine();

            if (line != null) {
                String[] dealershipInfo = line.split("\\|");

                String name = dealershipInfo[0];
                String address = dealershipInfo[1];
                String phone = dealershipInfo[2];

                dealership = new Dealership(name, address, phone);
            }

            while ((line = reader.readLine()) != null) {

                if (!line.isBlank()) {

                    String[] parts = line.split("\\|");

                    int vin = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[1]);
                    String make = parts[2];
                    String model = parts[3];
                    String type = parts[4];
                    String color = parts[5];
                    int odometer = Integer.parseInt(parts[6]);
                    BigDecimal price = new BigDecimal(parts[7]);

                    Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);

                    dealership.addVehicle(vehicle);
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }

        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.csv"));


            writer.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());

            writer.newLine();


            for (Vehicle vehicle : dealership.getAllVehicles()) {

                writer.write(vehicle.getVin() + "|" +
                                vehicle.getYear() + "|" +
                                vehicle.getMake() + "|" +
                                vehicle.getModel() + "|" +
                                vehicle.getVehicleType() + "|" +
                                vehicle.getColor() + "|" +
                                vehicle.getOdometer() + "|" +
                                vehicle.getPrice());

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving dealership inventory.");
        }
    }
}

