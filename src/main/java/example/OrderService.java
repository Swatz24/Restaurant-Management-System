package example;

import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    private final InventoryService inventoryService;

    private Map<String, Integer> itemOrderCounts; // Map to track order counts
    private List<Order> orders;
    private Menu menu;

    public OrderService(Menu menu, InventoryService inventoryService) {
        this.orders = new ArrayList<>();
        this.menu = menu;
        this.inventoryService = inventoryService; // Add this line
        this.itemOrderCounts = new HashMap<>();
    }


    public void takeOrder(TableManager tableManager, Scanner scanner) {
        // Display occupied tables
        System.out.println("List of occupied tables:");


        boolean hasOcuupiedTables =tableManager.displayOccupiedTables();
        if(!hasOcuupiedTables){
            System.out.println("There are no tables that are currently occupied. So cannot take order now.");
            return;
        }

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

                // Get the menu item name from the user
                System.out.print("Enter the menu item name (or 'exit' to finish): ");
                String itemName = scanner.nextLine();
                if (itemName.equalsIgnoreCase("exit")) {
                    addMoreItems = false;
                    continue;
                }

                // Get the menu item by name
                MenuItem menuItem = menu.getMenuItemByName(itemName);

                if (menuItem != null) {
                    // Get the quantity from the user
                    System.out.print("Enter the quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    // Check if the item is available in the menu
                    List<String> ingredientsNeeded = menuItem.getIngredients();
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
                        // System.out.println(order);
                        System.out.println("Item added to the order.");

                        // Update the order count for the menu item
                        if (itemOrderCounts.containsKey(menuItem.getName())) {
                            int count = itemOrderCounts.get(menuItem.getName());
                            itemOrderCounts.put(menuItem.getName(), count + quantity);
                        } else {
                            itemOrderCounts.put(menuItem.getName(), quantity);
                        }
                        // Update the total preparation time
                        int itemPrepTime = menuItem.getPrepTime();
                        order.setTotalPrepTime(order.getTotalPrepTime() + (itemPrepTime * quantity));

                        // Update the inventory
                        for (String ingredient : ingredientsNeeded) {
                            inventoryService.useIngredient(ingredient, quantity);
                            inventoryService.checkIngredientAlert();
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
            System.out.println("Total Price: " + totalPrice);

            // Set the status of the order to "waiting"
            order.setStatus("waiting");
            System.out.println("Order Status: " + order.getStatus());



            // Add the order to the list of orders
            orders.add(order);
            System.out.println(order);

            System.out.println("Order placed successfully.");

            order.setStatus("preparing");
            System.out.println("Preparing order now.");

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

    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            System.out.println("List of Orders:");
            for (Order order : orders) {
                System.out.println(order.toString());
            }
        }
    }


    public void displayOrderCounts() {
        for (Map.Entry<String, Integer> entry : itemOrderCounts.entrySet()) {
            String itemName = entry.getKey();
            int orderCount = entry.getValue();
            System.out.println(itemName + ": " + orderCount);
        }
    }

    /////////////////Methods to help generate SAles report///////////////////

    public double getTotalRevenue() {
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();
        }
        return totalRevenue;
    }


    public Map<String, Integer> getPopularItemsSorted() {
        Map<String, Integer> popularItems = new HashMap<>();
        for (Map.Entry<String, Integer> entry : itemOrderCounts.entrySet()) {
            String itemName = entry.getKey();
            int orderCount = entry.getValue();
            popularItems.put(itemName, orderCount);
        }
        return popularItems.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }


    public Map<Integer, Double> getTableRevenueSorted() {
        Map<Integer, Double> tableRevenue = new HashMap<>();
        for (Order order : orders) {
            int tableId = order.getTableId();
            double totalPrice = order.getTotalPrice();
            if (tableRevenue.containsKey(tableId)) {
                totalPrice += tableRevenue.get(tableId);
            }
            tableRevenue.put(tableId, totalPrice);
        }
        return tableRevenue.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public List<Order> getOrderList() {
        return orders;
    }

}

