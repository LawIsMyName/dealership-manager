# Car Dealership Application
## Project Overview
The Car Dealership Application is a Java console based program that allows users to manage a dealership's vehicle inventory. Users can search for vehicles using different criteria, view the entire inventory, add new vehicles, and remove existing vehicles.


The Car Dealership Application is a Java console-based program that allows users to manage a dealership's vehicle inventory. Users can search for vehicles using different criteria, view the entire inventory, add new vehicles, and remove existing vehicles.

The application uses object-oriented programming, ArrayLists, file handling, and input validation to manage vehicle information.
## Features
### Users can search the dealership inventory using six different options:

1. Search by Price: Find vehicles within a minimum and maximum price.

2. Search by Make and Model: Find vehicles matching a specific manufacturer and model.

3. Search by Year: Find vehicles within a specified year range.

4. Search by Color: Find vehicles matching a selected color.

5. Search by Mileage: Find vehicles within a minimum and maximum mileage range.

6. Search by Vehicle Type: Find vehicles based on their type.
  ## How to Run the Application

1. Clone or download this repository.

2. Open the project in IntelliJ IDEA.

3. Make sure the project is configured to use a compatible Java JDK.

4. Ensure inventory.csv is available in the project's expected location.

5. Open Program.java.

6. Run the main() method.

7. Use the numbered menu options to interact with the dealership.

##  View Inventory


### Users can display all vehicles currently available in the dealership inventory.

### Vehicle information is displayed in a formatted table containing:


Users can display all vehicles currently available in the dealership inventory.

Vehicle information is displayed in a formatted table containing:

1. VIN

2. Year

3. Make

4. Model

5. Vehicle Type

6. Color

7. Mileage

8. Price

The application also displays the total number of vehicles returned.

The application also displays the total number of vehicles returned.

Add Vehicles

### Users can add new vehicles by entering their information.

### The application validates the information before adding the vehicle to the inventory.

### Validation includes:
1. VIN must be greater than zero.

2. Duplicate VINs are not allowed.

3. Vehicle year must be 1886 or later.

4. Make, model, vehicle type, and color cannot be empty.

5. Mileage cannot be negative.

6. Price cannot be negative.

7. Invalid numeric inputs are rejected.

8. Vehicle text fields cannot contain the | character when using the string input helper.
  ## Application Menu

### When the application starts, users are presented with the following menu:

Application Menu

When the application starts, users are presented with the following menu:
```
---- CAR DEALERSHIP ----
1) Find vehicles by price
2) Find vehicles by make/model
3) Find vehicles by year
4) Find vehicles by color
5) Find vehicles by mileage
6) Find vehicles by type
7) List all vehicles
8) Add a vehicle
9) Remove a vehicle
0) Exit
   Enter your choice:
```

## Interesting Code

One interesting part of this project is the reusable input validation methods.

Instead of writing the same validation logic multiple times, I created helper methods to handle different types of user input.
## String Input Validation
```java
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
```