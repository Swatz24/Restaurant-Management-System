package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//Manages the orders
public class OrderService {

    //Method - returns order with by orderID orders
    private List<Order> orders;
    Scanner scanner = new Scanner(System.in);

    private List<Integer> orderedTableIDs;

    //Constructor that initializes 'orders' list as an empty array
    public OrderService () {
        orders = new ArrayList<>();
        orderedTableIDs = new ArrayList<>();
    }

    public void placeOrder(Order order, int tableID) {
        orders.add(order);
        orderedTableIDs.add(tableID);
        System.out.println("Order ID: " + order.getOrderID() + " placed for Table ID: " + tableID);
    }

    public void updateOrderStatus(int orderID, String newOrderStatus) {
        Order order = getOrderByID(orderID);

        if (order != null) {
            order.setOrderStatus(newOrderStatus);
            System.out.println("Order ID: " + orderID + " updated.");
        } else {
            System.out.println("Order ID " + orderID + " not found.");
        }
    }
    public Order getOrderByID (int orderID) {

        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;
    }
    //Method - returns all orders
    public List<Order> getAllOrders() {

        return orders;
    }

    //Method to calculate items total price
    public int calculateTotalPrice(Order order) {
        Map<String, Integer> itemsOrdered = order.getItemsOrdered();
        int totalPrice = 0;

        for (Map.Entry<String, Integer> entry : itemsOrdered.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double itemPrice = Order.getItemPrice(itemName);
            totalPrice += itemPrice * quantity;
        }
        return totalPrice;
    }

//    public void takeOrder(TableManager tableManager,  Scanner scanner) {
//        System.out.println("List of tables occupied: ");
//        tableManager.displayOccupiedTables();
//
//        System.out.print("Enter the table ID for the order: ");
//        int tableId = scanner.nextInt();
//        scanner.nextLine(); // Consume the newline character
//
//        Table table = tableManager.getTableById(tableId);
//        if (table != null && table.getStatus().equals("Occupied")) {
//            System.out.println("Placing order for Table " + tableId + ":");
//
//            // Create a new order for the table
//            Order order = new Order(table);
//
//            // Get the menu items from the user until they choose to stop
//            boolean addMoreItems = true;
//            while (addMoreItems) {
//                System.out.println("Select an item from the menu or enter '0' to finish the order:");
//                //tableManager.getMenuService().displayMenu();
//
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // Consume the newline character
//
//                if (choice == 0) {
//                    addMoreItems = false;
//                } else {
//                    MenuItem menuItem = tableManager.getMenuService().getMenuItemByIndex(choice - 1);
//                    if (menuItem != null) {
//                        order.addItem(menuItem);
//                        System.out.println(menuItem.getName() + " added to the order.");
//                    } else {
//                        System.out.println("Invalid menu item selection. Please try again.");
//                    }
//                }
//            }
//
//            // Add the order to the list of orders
//            orders.add(order);
//            System.out.println("Order placed successfully.");
//        } else {
//            System.out.println("Invalid table selection. Please choose an occupied table.");
//        }
//    }
//    }


}

