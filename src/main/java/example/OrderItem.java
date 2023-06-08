package example;

public class OrderItem {
    private String name;
    private int quantity;
    private int orderCount; // New field for order count

    public OrderItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.orderCount = 1; // Initialize order count to 1
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
    public void incrementOrderCount() {
        orderCount++; // Increment the order count
    }
}
