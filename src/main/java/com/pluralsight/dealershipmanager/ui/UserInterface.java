package com.pluralsight.dealershipmanager.ui;

import com.pluralsight.dealershipmanager.data.ContractDataManager;
import com.pluralsight.dealershipmanager.data.DealershipFileManager;
import com.pluralsight.dealershipmanager.model.Dealership;
import com.pluralsight.dealershipmanager.model.Vehicle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import com.pluralsight.dealershipmanager.model.Contract;
import com.pluralsight.dealershipmanager.model.SalesContract;
import com.pluralsight.dealershipmanager.model.LeaseContract;

import java.time.LocalDate;

public class UserInterface {

    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        dealership = fileManager.getDealership();

    }

    public void display() {

        init();

        if (dealership == null) {
            System.out.println("Unable to load dealership inventory.");
            return;
        }

        boolean running = true;

        while (running) {

            displayMenu();

            String command = scanner.nextLine().trim();

            switch (command) {
                case "1" -> processGetByPriceRequest();
                case "2" -> processGetByMakeModelRequest();
                case "3" -> processGetByYearRequest();
                case "4" -> processGetByColorRequest();
                case "5" -> processGetByMileageRequest();
                case "6" -> processGetByTypeRequest();
                case "7" -> processGetAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "10" -> processSellOrLeaseVehicleRequest();
                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void displayMenu() {

        System.out.println();
        System.out.println("---- CAR DEALERSHIP ----");
        System.out.println("1) Find vehicles by price");
        System.out.println("2) Find vehicles by make/model");
        System.out.println("3) Find vehicles by year");
        System.out.println("4) Find vehicles by color");
        System.out.println("5) Find vehicles by mileage");
        System.out.println("6) Find vehicles by type");
        System.out.println("7) List all vehicles");
        System.out.println("8) Add a vehicle");
        System.out.println("9) Remove a vehicle");
        System.out.println("10) Sell or lease a vehicle");
        System.out.println("0) Exit");
        System.out.print("Enter your choice: ");
    }

    private void processGetByPriceRequest() {
        try {
            System.out.println("---- SEARCH BY PRICE ----");

            System.out.print("Enter minimum price: ");
            BigDecimal min = new BigDecimal(scanner.nextLine());

            System.out.print("Enter maximum price: ");
            BigDecimal max = new BigDecimal(scanner.nextLine());

            if (min.compareTo(max) > 0) {
                System.out.println("Minimum price cannot be greater than maximum price.");
                return;
            }

            if (min.compareTo(BigDecimal.ZERO) < 0 || max.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }

            ArrayList<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
            displayVehicles(vehicles);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Price. Please enter a valid amount.");
        }
    }

    private void processGetByMakeModelRequest() {
        System.out.println("---- SEARCH BY MAKE/MODEL ----");

        System.out.print("Enter make: ");
        String make = scanner.nextLine();

        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        ArrayList<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    private void processGetByYearRequest() {
        try {
            System.out.println("---- SEARCH BY YEAR ----");

            System.out.print("Enter minimum year: ");
            int min = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter maximum year: ");
            int max = Integer.parseInt(scanner.nextLine());

            if (min > max) {
                System.out.println("Minimum year cannot be greater than maximum year.");
                return;
            }

            ArrayList<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);

            displayVehicles(vehicles);
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Please enter a whole number.");
        }

    }

    private void processGetByColorRequest() {
        System.out.println("---- SEARCH BY COLOR ----");

        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        ArrayList<Vehicle> vehicles = dealership.getVehiclesByColor(color);

        displayVehicles(vehicles);
    }

    private void processGetByMileageRequest() {
        try {
            System.out.println("---- SEARCH BY MILEAGE ----");

            System.out.print("Enter minimum mileage: ");
            int min = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter maximum mileage: ");
            int max = Integer.parseInt(scanner.nextLine());

            if (min > max) {
                System.out.println("Minimum mileage cannot be greater than maximum mileage.");
                return;
            }

            if (min < 0 || max < 0) {
                System.out.println("Mileage cannot be negative.");
                return;
            }

            ArrayList<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);

            displayVehicles(vehicles);
        } catch (NumberFormatException e) {
            System.out.println("Invalid mileage. Please enter a whole number.");
        }
    }

    private void processGetByTypeRequest() {
        System.out.println("---- SEARCH BY VEHICLE TYPE ----");

        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();

        ArrayList<Vehicle> vehicles = dealership.getVehiclesByType(type);

        displayVehicles(vehicles);
    }

    private void processGetAllVehiclesRequest() {
        ArrayList<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    private void processAddVehicleRequest() {
        System.out.println("---- ADD VEHICLE ----");

        int vin = promptForInt("Enter VIN: ");

        if (vin <= 0) {
            System.out.println("VIN must be greater than zero.");
            return;
        }

        for (Vehicle existingVehicle : dealership.getAllVehicles()) {

            if (existingVehicle.getVin() == vin) {
                System.out.println("A vehicle with this VIN already exists.");
                return;
            }
        }

        int year = promptForInt("Enter year: ");

        if (year < 1886) {
            System.out.println("Please enter a valid vehicle year.");
            return;
        }

        String make = promptForString("Enter make: ");

        String model = promptForString("Enter model: ");

        String type = promptForString("Enter vehicle type: ");

        String color = promptForString("Enter color: ");

        int odometer = promptForInt("Enter mileage: ");

        if (odometer < 0) {
            System.out.println("Mileage cannot be negative.");
            return;
        }

        BigDecimal price = promptForBigDecimal("Enter price: ");

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);

        dealership.addVehicle(vehicle);

        DealershipFileManager fileManager = new DealershipFileManager();

        fileManager.saveDealership(dealership);

        System.out.println("Vehicle added successfully!");
    }

    private void processRemoveVehicleRequest() {
        System.out.println("---- REMOVE VEHICLE ----");

        int vin = promptForInt("Enter VIN vehicle to remove: ");

        Vehicle vehicleToRemove = null;

        for (Vehicle vehicle : dealership.getAllVehicles()) {

            if (vehicle.getVin() == vin) {
                vehicleToRemove = vehicle;
                break;
            }
        }

        if (vehicleToRemove != null) {

            dealership.removeVehicle(vehicleToRemove);

            DealershipFileManager fileManager = new DealershipFileManager();

            fileManager.saveDealership(dealership);

            System.out.println("Vehicle removed successfully!");

        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private void processSellOrLeaseVehicleRequest() {
        System.out.println("---- SELL OR LEASE VEHICLE ----");

        int vin = promptForInt("Enter VIN: ");

        Vehicle selectedVehicle = null;

        for (Vehicle vehicle : dealership.getAllVehicles()) {

            if (vehicle.getVin() == vin) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        String customerName = promptForString("Enter customer name: ");
        String customerEmail = promptForString("Enter customer email: ");
        String contractType = promptForString("Sale or lease: ");

        Contract contract = null;
        String contractDate = LocalDate.now().toString();

        if (contractType.equalsIgnoreCase("sale")) {

            String financeAnswer = promptForString("Finance vehicle? (yes/no): ");

            if (!financeAnswer.equalsIgnoreCase("yes") && !financeAnswer.equalsIgnoreCase("no")) {
                System.out.println("Invalid financing option.");
                return;
            }

            boolean financed = financeAnswer.equalsIgnoreCase("yes");

            contract = new SalesContract(contractDate, customerName, customerEmail, selectedVehicle, financed);

        } else if (contractType.equalsIgnoreCase("lease")) {

            int vehicleAge = LocalDate.now().getYear() - selectedVehicle.getYear();

            if (vehicleAge > 3) {
                System.out.println("Vehicles over 3 years old cannot be leased.");
                return;
            }

            dealership.removeVehicle(selectedVehicle);

        } else {

            System.out.println("Invalid contract type.");
            return;
        }

        if (contract == null) {
            System.out.println("Unable to create contract.");
            return;
        }

        System.out.println();
        System.out.println("---- CONTRACT SUMMARY ----");
        System.out.println("Customer: " + customerName);
        System.out.println("Vehicle: " + selectedVehicle.getYear() + " " + selectedVehicle.getMake() + " " + selectedVehicle.getModel());
        System.out.printf("Total Price: $%.2f%n", contract.getTotalPrice());
        System.out.printf("Monthly Payment: $%.2f%n", contract.getMonthlyPayment());

        ContractDataManager manager = new ContractDataManager();
        boolean saved = manager.saveContract(contract);

        if (!saved) {
            System.out.println("Contract could not be saved. Vehicle will remain in inventory.");
            return;
        }

        dealership.removeVehicle(selectedVehicle);

        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership);

        System.out.println("Vehicle removed from inventory.");

    }

    private void displayVehicles(ArrayList<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.println();
        System.out.println("---- VEHICLE INVENTORY ----");

        System.out.printf("%-7s %-6s %-12s %-12s %-10s %-10s %-10s %12s%n", "VIN", "YEAR", "MAKE", "MODEL", "TYPE", "COLOR", "MILEAGE", "PRICE");

        System.out.println("------------------------------------------------------------------------------------------");

        for (Vehicle vehicle : vehicles) {

            System.out.printf("%-7d %-6d %-12s %-12s %-10s %-10s %-10d $%11.2f%n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
        }

        System.out.println();
        System.out.println("Total vehicles: " + vehicles.size());
    }

    //-------------------------------------------------- input helpers

    private int promptForInt(String prompt) {

        while (true) {

            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());


            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private BigDecimal promptForBigDecimal(String prompt) {

        while (true) {

            try {
                System.out.print(prompt);
                return new BigDecimal(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        }
    }

    private String promptForString(String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("Input cannot be empty.");
            } else if (input.contains("|")) {
                System.out.println("Input cannot contain |.");
            } else {
                return input;
            }
        }
    }
}





