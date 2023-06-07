package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private final InventoryService inventoryService;
    private List<Order> orders;
    private Menu menu;

    public OrderService(Menu menu, InventoryService inventoryService) {
        this.orders = new ArrayList<>();
        this.menu = menu;
        this.inventoryService = inventoryService; // Add this line
    }


    public void takeOrder(TableManager tableManager, Scanner scanner) {
        // Display occupied tables
        System.out.println("List of occupied tables:");
        tableManager.displayOccupiedTables();

        // Get the table ID from the user
        System.out.print("Enter the table ID: ");
        int tableId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Find the table by ID
        Table table = tableManager.getTableById(tableId);
        if (table != null && table.getStatus().equals("Occupied")) {
            // Create a new order
            Order order = new Order(tableId);

            boolean addMoreItems = true;
            while (addMoreItems) {
                // Display the menu

                menu.displayMenu();

                // Get the menu item name and quantity from the user
                System.out.print("Enter the menu item name: ");

                String itemName = scanner.nextLine(); // Consume the newline character
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Find the menu item by name
                MenuItem menuItem = menu.getMenuItemByName(itemName);

                if (menuItem != null ) {
                    System.out.println("The item you ordered is:  "+ menuItem.getName());
                    // Check if the item is available in the menu

                    List<String> ingredientsNeeded =   menuItem.getIngredients();

//                    System.out.println(ingredientsNeeded.size());
//
//                    System.out.println(inventoryService.isIngredientAvailable(ingredientsNeeded.get(0)));

                    boolean allIngredientsAvailable = true;

                    for (String ingredient : ingredientsNeeded) {
                        if (!inventoryService.isIngredientAvailable(ingredient, quantity)) {
                            allIngredientsAvailable = false;
                            break;
                        }
                    }


                        if (allIngredientsAvailable) {
                            // Add the item to the order
                            OrderItem orderItem = new OrderItem(menuItem.getName(), quantity);
                            order.addItem(orderItem);
                            System.out.println("Item added to the order.");
                            // Update the inventory
                            for (String ingredient : ingredientsNeeded) {
                                inventoryService.useIngredient(ingredient, quantity);
                            }
                        } else {
                            System.out.println("Insufficient ingredients to prepare the item.");
                        }
                    } else {
                        System.out.println("Item is not available in the menu.");
                    }


                // Ask the user if they want to add more items to the order
                System.out.print("Add more items to the order? (y/n): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("y")) {
                    addMoreItems = false;
                }
            }

            // Calculate the total price of the order
            double totalPrice = calculateTotalPrice(order);
            order.setTotalPrice(totalPrice);

            // Set the status of the order to "waiting"
            order.setStatus("waiting");

            // Add the order to the list of orders
            orders.add(order);

            System.out.println("Order placed successfully.");
        } else {
            System.out.println("Invalid table ID or the table is not occupied.");
        }
    }

    private double calculateTotalPrice(Order order) {
        double totalPrice = 0;
        for (OrderItem item : order.getItems()) {
            MenuItem menuItem = menu.getMenuItemByName(item.getName());
            if (menuItem != null) {
                totalPrice += menuItem.getPrice() * item.getQuantity();
            }
        }
        return totalPrice;
    }
}
