package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RestaurantMgmtSystem {
    public static void main(String[] args) {
        System.out.println("Welcome to the restaurant!!");
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        // Create object for InventoryService class and define the filePath where you want the file to be saved.
        InventoryService inventoryService = new InventoryService("C:\\CTAC\\RestaurantMgmtSystem\\untitled\\src\\main\\java\\example\\inventory.txt");
        // MenuService
        Menu menuService = new Menu();
        menuService.loadMenu();
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
            System.out.println("\n*** HELLO, PLEASE SIGN IN ***\n");

            boolean loggedIn = false;
            String username = "";
            String pw = "";
            while (!loggedIn) {
                System.out.println("Enter your name: ");
                username = scanner.nextLine();
                System.out.println("Enter your password: ");
                pw = scanner.nextLine();
                loggedIn = userService.login(username, pw);

                if (!loggedIn) {
                    System.out.println("Invalid username or password. Please try again.");
                }
            }

            // Authentication
            UserAuthentication userAuth = new UserAuthentication();
            String role = userAuth.authenticateUser(username, employee, password, pw);
            System.out.println(pw);
//        System.out.println("Role: " + role);
        System.out.println("pwd" + pw);
            if (role != null) {

                if (role.equalsIgnoreCase("MANAGER")) {
                    // Manager menu options
                    System.out.println("Welcome, Manager!");
//                    Menu menuService = new Menu();
                    menuService.loadMenu();

                    int choice;
                    do {
                        System.out.println("Manager Menu:");
                        System.out.println("1. View Menu");
                        System.out.println("2. Add Menu Item");
                        System.out.println("3. Remove Menu Item");
                        System.out.println("4. Edit Menu Item");
                        System.out.println("5. Generate Sales Report");
                        System.out.println("6. Check Inventory Status");
                        System.out.println("7. Add inventory ");
                        System.out.println("8. Logoff ");
                        System.out.println("0. Exit");
                        System.out.print("Enter your choice: ");
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
                                        System.out.println("Please enter the ingredient name to be added: ");
                                        String name = scanner.nextLine();
                                        System.out.println("Please enter the number of units you are adding: ");
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
//                                salesReport.saveReportToFile("C:\\CTAC\\RestaurantMgmtSystem\\untitled\\src\\main\\java\\example\\salesReport.txt");
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
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } while (loggedIn && !exitProgram && choice != 0 && choice != 8);

                    //menuService.saveMenuItems();
                } else if (role.equalsIgnoreCase("STAFF")) {
                    // Staff menu options
                    System.out.println("Welcome, Staff!");


                    int choice;
                    do {
                        System.out.println("Staff Menu:");

                        System.out.println("1. Take Order");
                        System.out.println("2. Assign Table");
                        System.out.println("3. Reserve Table");
                        System.out.println("4. Release Table");
                        System.out.println("5. Display all Tables");
                        System.out.println("6. Display the orders");
                        System.out.println("7. Log Out");
                        System.out.println("0. Exit");
                        System.out.print("Enter your choice: ");
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

