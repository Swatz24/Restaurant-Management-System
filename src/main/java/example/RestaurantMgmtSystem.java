package example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RestaurantMgmtSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Register user
        UserAuthentication userAuth = new UserAuthentication();
        userAuth.registerUser("manager", "managerpassword", UserRole.MANAGER);
        userAuth.registerUser("staff", "staffpassword", UserRole.STAFF);

        // Add inventory

        InventoryService inventoryService = new InventoryService("C:\\CTAC\\RestaurantMgmtSystem\\untitled\\src\\main\\java\\example\\inventory.txt");

        // Adding items to the inventory


        // Table Manager
        TableManager tableManager = new TableManager();

        // Add tables
        Table table1 = new Table(1, 2, "Available");
        Table table2 = new Table(2, 2, "Available");
        Table table3 = new Table(3, 2, "Available");
        Table table4 = new Table(4, 4, "Available");
        Table table5 = new Table(5, 4, "Available");

        tableManager.addTable(table1);
        tableManager.addTable(table2);
        tableManager.addTable(table3);
        tableManager.addTable(table4);
        tableManager.addTable(table5);


        // Authentication
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check user role
        UserRole role = userAuth.authenticateUser(username, password);
        if (role != null) {
            if (role == UserRole.MANAGER) {
                // Manager menu options
                System.out.println("Welcome, Manager!");
                Menu menuService = new Menu("C:\\CTAC\\RestaurantMgmtSystem\\untitled\\src\\main\\java\\example\\menu.txt");
                menuService.loadMenu();

                int choice;
                do {
                    System.out.println("Manager Menu:");
                    System.out.println("1. View Menu");
                    System.out.println("2. Add Menu Item");
                    System.out.println("3. Remove Menu Item");
                    System.out.println("4. Generate Sales Report");
                    System.out.println("5. Check Inventory Status");
                    System.out.println("6. Add inventory ");
                    System.out.println("0. Exit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    switch (choice) {
                        case 1:
                           menuService.displayMenu();
                            break;
                        case 2:
                            System.out.print("Enter item name: ");
                            String itemName = scanner.nextLine();
                            System.out.print("Enter item description: ");
                            String itemDescription = scanner.nextLine();
                            System.out.print("Enter preparation time: ");
                            int preparationTime = scanner.nextInt();
                            System.out.print("Enter item price: ");
                            double itemPrice = scanner.nextDouble();
                            scanner.nextLine(); // Consume the newline character
                            System.out.print("Enter ingredients (comma-separated): ");
                            String ingredientInput = scanner.nextLine();
                            List<String> ingredients = Arrays.asList(ingredientInput.split(","));
                            MenuItem newItem = new MenuItem(itemName, itemDescription, preparationTime, itemPrice, ingredients);
                            menuService.addItem(newItem);

                            break;
                        case 3:
                            System.out.print("Enter the item name to remove: ");
                            String itemToRemove = scanner.nextLine();
                            menuService.removeItem(itemToRemove);
                            break;
                        case 4:
                           // SalesReportService salesReportService = new SalesReportService();
                            //salesReportService.generateSalesReport();
                            break;
                        case 5:

                            inventoryService.checkInventoryStatus();
                            break;

                        case 6:
                            System.out.println("Please enter the ingredient name to be added: ");
                            String name = scanner.nextLine();
                            System.out.println("Please enter the number of units you are adding: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            inventoryService.addIngredient(name,quantity);
                            break;
                        case 0:
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (choice != 0);

                //menuService.saveMenuItems();
            } else if (role == UserRole.STAFF) {
                // Staff menu options
                System.out.println("Welcome, Staff!");
               // TableService tableService = new TableService();
              //  MenuService menuService = new MenuService();
                //menuService.loadMenuItems();

                int choice;
                do {
                    System.out.println("Staff Menu:");

                    System.out.println("1. Take Order");
                    System.out.println("2. Assign Table");
                    System.out.println("3. Reserve Table");
                    System.out.println("4. Release Table");
                    System.out.println("5. Display all Tables");
                    System.out.println("0. Exit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    switch (choice) {
                        case 1:
                            // Take order
                            // ...
                            break;
                        case 2:
                            // Assign table
                            System.out.print("Enter party size: ");
                            int partySize = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character

                            // Find available table based on party size
                            List<Table> availableTablesForId = tableManager.findAvailableTable(partySize);

                            if (availableTablesForId.isEmpty()) {
                                System.out.println("No available tables for a party size of " + partySize);
                            } else {
                                System.out.println("Available Tables for a party size of " + partySize + ":");
                                for (Table table : availableTablesForId) {
                                    System.out.println("Table ID: " + table.getTableId() + ", Size: " + table.getTableSize());
                                }

                                System.out.println("Enter the table Id to assign to the party?");
                                int tableId = Integer.parseInt(scanner.nextLine());

                                // Check if the selected table is available and has the correct party size
                                Table selectedTable = tableManager.getTableById(tableId);
                                if (selectedTable != null && selectedTable.getStatus().equals("Available") && selectedTable.getTableSize() >= partySize) {
                                    tableManager.assignCustomerToTable(tableId);
                                    System.out.println("Table " + tableId + " assigned to the party successfully.");
                                } else {
                                    System.out.println("Invalid table selection. Please choose an available table with a size greater than or equal to the party size.");
                                }
                            }

                            break;
                        case 3:
                            // Reserve table
                            System.out.print("Enter table ID to reserve: ");
                            int tableIdToReserve = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            tableManager.reserveTable(tableIdToReserve);
                            break;
                        case 4:
                            // Release table
                            System.out.print("Enter table ID to release: ");
                            int tableIdToRelease = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            Table tableToRelease = tableManager.getTableById(tableIdToRelease);
                            if (tableToRelease != null && tableToRelease.getStatus() != "Available") {
                                tableToRelease.setStatus("Available");
                                System.out.println("Table released successfully. Table ID: " + tableToRelease.getTableId());
                            } else {
                                System.out.println("Table not found.");
                            }
                            break;
                        case 5:
                            // Display all tables
                            List<Table> availableTables = tableManager.getAllTables();

                            break;
                        case 0:
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (choice != 0);
            }
        } else {
            System.out.println("Invalid username or password. Exiting...");
        }
    }


    }

