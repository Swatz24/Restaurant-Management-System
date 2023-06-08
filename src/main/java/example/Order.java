package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Order {

    private int orderId; // New field for order ID
    private int tableId;
    private List<OrderItem> items;
    private double totalPrice;
    private String status;
    private int totalPrepTime;
    private Timer timer;

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

    // Override the toString() method to display the order details
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order ID: ").append(orderId).append("\n");
        builder.append("Table ID: ").append(tableId).append("\n");
        builder.append("Status: ").append(status).append("\n");
        builder.append("Items:\n");
        for (OrderItem item : items) {
            builder.append("- ").append(item.getName()).append(" (Quantity: ").append(item.getQuantity()).append(")\n");
        }
        builder.append("Total Price: ").append(totalPrice).append("\n");
        builder.append("Total Prep Time: ").append(totalPrepTime).append("\n");
        return builder.toString();
    }
}
