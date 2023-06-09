package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RestaurantMgmtSystem {
    public static void main(String[] args) {
        String inventoryFilePath = "src/main/java/example/inventory.txt";
        System.out.println("\033[0;35mWelcome to the restaurant!!\033[0m");
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        // Create object for InventoryService class and define the filePath where you want the file to be saved.
        InventoryService inventoryService = new InventoryService(inventoryFilePath);
        // MenuService
        Menu menuService = new Menu();
        menuService.loadMenu();
        // Create a object for ObjectService class and pass menuService and inventoryService as it's parameters.
        OrderService orderService = new OrderService(menuService, inventoryService);

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


        //Beginning of login portion
        LoginSystem login = new LoginSystem();

        // Create lists to store employee usernames and hashed passwords
        List<String> employee = new ArrayList<>();
        List<String> password = new ArrayList<>();

        // Add employee usernames and hashed passwords to the lists
        employee.add("Karen(STAFF)");
        employee.add("Miosotis(STAFF)");
        employee.add("Swathi(MANAGER)");
        employee.add("Chitra(STAFF)");
        employee.add("John(MANAGER)");
        // Hash the password before storing
        password.add(login.hash("karen2023"));
        password.add(login.hash("miosotis2023"));
        password.add(login.hash("swathi2023"));
        password.add(login.hash("chitra2023"));
        password.add(login.hash("john2023"));


        // Create a UserService object with the employee and password lists
        UserService userService = new UserService(employee, password);
        while (!exitProgram) {
            System.out.println("\033[0;36m\n*** HELLO, PLEASE SIGN IN ***\n\033[0m");

            boolean loggedIn = false;
            String username = "";
            String pw = "";
            while (!loggedIn) {
                System.out.println("\033[0;33mEnter your name: \033[0m");
                username = scanner.nextLine();
                System.out.println("\033[0;33mEnter your password: \033[0m ");
                pw = scanner.nextLine();
                loggedIn = userService.login(username, pw);

                if (!loggedIn) {
                    System.out.println("\033[0;31mInvalid username or password. Please try again. \033[0m");
                }
            }

            // Authentication
            UserAuthentication userAuth = new UserAuthentication();
            String role = userAuth.authenticateUser(username, employee, password, pw);
            if (role != null) {

                if (role.equalsIgnoreCase("MANAGER")) {
                    // Manager menu options
                    System.out.println("\033[0;34mWelcome, Manager!\033[0m\n");
//                    Menu menuService = new Menu();
                    menuService.loadMenu();

                    int choice;
                    do {
                        System.out.println("\033[0;95mManager Menu:\033[0m");
                        System.out.println("\033[0;33m1.\033[0m View \033[0;33mMenu\033[0m");
                        System.out.println("\033[0;33m2.\033[0m Add \033[0;33mMenu Item\033[0m");
                        System.out.println("\033[0;34m3. Remove Menu Item\033[0m");
                        System.out.println("\033[0;33m4.\033[0m Edit \033[0;33mMenu Item\033[0m");
                        System.out.println("\033[0;33m5.\033[0m Generate Sales \033[0;33mReport\033[0m");
                        System.out.println("\033[0;33m6.\033[0m Check \033[0;33mInventory Status\033[0m");
                        System.out.println("\033[0;33m7. Add inventory\033[0m");
                        System.out.println("\033[1;93m8. Logoff\033[0m ");
                        System.out.println("\033[0;34m0. Exit\033[0m");
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (choice) {
                            case 1:
                                menuService.displayMenu();
                                inventoryService.checkIngredientAlert();
                                break;
                            case 2:
                                menuService.addItem();
                                menuService.saveMenu();
                                System.out.println("Would you like to add any ingredients to the inventory list for this menu item?");
                                System.out.println("Enter 'Y' for yes or 'N' for no:");

                                    if(scanner.nextLine().equalsIgnoreCase("y")){
                                        System.out.println("Please enter the \033[0;35mingredient name\033[0m to be added: ");
                                        String name = scanner.nextLine();
                                        System.out.println("Please enter the \033[0;35mnumber of units\033[0m you are adding: ");
                                        int quantity = Integer.parseInt(scanner.nextLine());
                                        inventoryService.addIngredient(name, quantity);
                                        break;
                                    }

                                break;
                            case 3:
                                menuService.removeItem();
                                menuService.saveMenu();
                                break;
                            case 4:
                                menuService.editItem();
                                menuService.saveMenu();
                                break;
                            case 5:
                                 List<Order> order =  orderService.getOrderList();
                                 SalesReport salesReport = new SalesReport(order);
                                // Generate the sales report
                                salesReport.generateDailySalesReport(orderService);

                                // Save the sales report to a file
                                String report = salesReport.getGeneratedReport();
                              break;

                            case 6:
                                inventoryService.checkInventoryStatus();
                                inventoryService.checkIngredientAlert();
                                break;
                            case 7:
                                System.out.println("Please enter the ingredient name to be added: ");
                                String name = scanner.nextLine();
                                System.out.println("Please enter the number of units you are adding: ");
                                int quantity = Integer.parseInt(scanner.nextLine());
                                inventoryService.addIngredient(name, quantity);
                                break;
                            case 8:
                                loggedIn = false;
                                System.out.println("Logged out successfully.");
                                break;
                            case 0:
                                loggedIn = false;
                                exitProgram = true;
                                System.out.println("Exiting...");
                                break;
                            default:
                                System.out.println("\033[0;31m. Invalid choice. Please try again. \033[0m");
                        }
                    } while (loggedIn && !exitProgram && choice != 0 && choice != 8);

                    //menuService.saveMenuItems();
                } else if (role.equalsIgnoreCase("STAFF")) {
                    // Staff menu options
                    System.out.println("\033[0;34mWelcome, Staff!\033[0m");


                    int choice;
                    do {
                        System.out.println("\033[1;95mStaff Menu:\033[0m");

                        System.out.println("\033[0;33m1. Take Order\033[0m");
                        System.out.println("\033[0;33m2. Assign Table\033[0m");
                        System.out.println("\033[0;33m3. Reserve Table\033[0m");
                        System.out.println("\033[0;33m4. Release Table\033[0m");
                        System.out.println("\033[0;33m5. Display all Tables\033[0m");
                        System.out.println("\033[0;33m6. Display the orders\033[0m");
                        System.out.println("\033[1;93m7. Log Out\033[0m");
                        System.out.println("\033[1;93m0. Exit\033[0m");
                        System.out.print("\033[4;32mEnter your choice: \033[0m");
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character



                        switch (choice) {
                            case 1:
                                orderService.takeOrder(tableManager, scanner);
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

                            case 6:
                                // Display all orders

                                orderService.displayOrders();
                                orderService.displayOrderCounts();

                                break;

                            case 7:
                                loggedIn = false;
                                System.out.println("Logged out successfully.");
                                break;
                            case 0:
                                System.out.println("Exiting...");
                                exitProgram = true;
                                loggedIn = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }while (loggedIn && !exitProgram && choice != 0 && choice != 7);
                }
            } else {
                System.out.println("Invalid username or password. Exiting...");
            }
        }


    }
}

