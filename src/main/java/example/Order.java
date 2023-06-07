package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Represents an individual order
public class Order {

    private int orderID;
    private static int nextOrderID = 1;
    private Map<String, Integer> itemsOrdered;//Stores items and amounts
    private int totalPrice;
    private String orderStatus;//Status to show waiting, preparing, or ready
//    private Map<String, Integer> itemPrices;

    //Stores prices
    private static Map<String, Double> itemPrices = createItemPrices();

    //Constructor
    public Order(int orderID, Map<String, Integer> itemsOrdered, int totalPrice, String orderStatus) {
        this.orderID = nextOrderID++;
        this.itemsOrdered = itemsOrdered;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
    // Getters and setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Map<String, Integer> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(Map<String, Integer> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public int getTotalPriceD() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderID +
                "\nItems Ordered: " + itemsOrdered +
                "\nTotal Price: " + totalPrice +
                "\nOrder Status: " + orderStatus + "\n";
    }

    //Method creates and returns item prices
    private static Map<String, Double> createItemPrices() {
        Map<String, Double> prices = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    double itemPrice = Double.parseDouble(parts[1].trim());
                    prices.put(itemName, itemPrice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prices;
    }

    // Method to retrieve the price of an item
    public static double getItemPrice(String itemName) {
        if (itemPrices.containsKey(itemName)) {
            return itemPrices.get(itemName);
        } else {
            throw new IllegalArgumentException("Item not found: " + itemName);
        }
    }


        private List<MenuItem> items;
        private List<Integer> quantities;

        public Order() {
            items = new ArrayList<>();
            quantities = new ArrayList<>();
        }

        public void addItem(MenuItem item, int quantity) {
            items.add(item);
            quantities.add(quantity);
        }

        // Other methods and members of the Order class...
    }



