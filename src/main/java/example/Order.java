package example;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int tableId;
    private List<OrderItem> items;
    private double totalPrice;
    private String status;

    public Order(int tableId) {
        this.tableId = tableId;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.status = "waiting";
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
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }
}
