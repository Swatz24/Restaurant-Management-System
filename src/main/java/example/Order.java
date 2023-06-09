package example;

import java.util.*;

public class Order {

    private int orderId; // New field for order ID
    private int tableId;
    private List<OrderItem> items;
    private double totalPrice;
    private String status;
    private int totalPrepTime;
    private Timer timer;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    private static Map<Integer, Double> tableRevenue = new HashMap<>();

    private static int nextOrderId = 1; // Static field to generate unique order IDs

    public Order(int tableId) {
        this.orderId = nextOrderId++; // Assign a unique order ID
        this.tableId = tableId;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.status = "waiting";
        this.totalPrepTime = 0;
    }


    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public static Map<Integer, Double> getTableRevenue() {
        return tableRevenue;
    }

    public void setStatus(String status) {
        this.status = status;
        if (status.equals("preparing")) {
            startTimer();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // once the timer is done, changes status to complete
                setStatus("complete");
            }
        }, totalPrepTime * 60 * 1000); //converts minutes to milliseconds
    }

    public int getTotalPrepTime() {
        return totalPrepTime;
    }

    public void setTotalPrepTime(int totalPrepTime) {
        this.totalPrepTime = totalPrepTime;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public static void updateTableRevenue(int tableId, double totalPrice) {
        tableRevenue.put(tableId, tableRevenue.getOrDefault(tableId, 0.0) + totalPrice);
    }

    // Override the toString() method to display the order details
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order ID: ").append(orderId).append("\n");
        builder.append("Table ID: ").append(tableId).append("\n");
        builder.append(ANSI_GREEN + "Status: ").append(status).append("\n" + ANSI_RESET);
        builder.append("Items:\n");
        for (OrderItem item : items) {
            builder.append("- ").append(item.getName()).append(" (Quantity: ").append(item.getQuantity()).append(")\n");
        }
        builder.append(ANSI_CYAN + "Total Price: ").append(totalPrice).append("\n" + ANSI_RESET);
        builder.append(ANSI_CYAN + "Total Prep Time: ").append(totalPrepTime).append("\n" + ANSI_RESET);
        return builder.toString();
    }
}
