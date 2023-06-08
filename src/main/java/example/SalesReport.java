package example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class SalesReport {
    private List<Order> orderList;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");
    private String generatedReport; // Variable to store the generated report

    public SalesReport(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void generateDailySalesReport() {
        LocalDateTime current = LocalDateTime.now();
        OrderService orderService = new OrderService(new Menu(), new InventoryService());
        Map<Integer, Double> tableRevenue = orderService.getTableRevenue();
        Map<String, Integer> popularItems = orderService.getPopularItems();

        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------\n Daily Sales Report \n ")
                .append(dateTimeFormatter.format(current))
                .append("\n-----------------------------\n")
                .append("Total Revenue: $")
                .append(decimalFormatter.format(orderService.getTotalRevenue()))
                .append("\n\nMost Popular Items: \n")
                .append(popularItemsToString(popularItems))
                .append("\nTables Sales: \n")
                .append(tableRevenueToString(tableRevenue))
                .append("\nDetailed Orders: \n")
                .append(printOrders(orderList));

        generatedReport = sb.toString(); // Store the generated report
    }

    public String getGeneratedReport() {
        return generatedReport;
    }

    private String popularItemsToString(Map<String, Integer> popularItems) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : popularItems.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    private String tableRevenueToString(Map<Integer, Double> tableRevenue) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Double> entry : tableRevenue.entrySet()) {
            sb.append("Table ").append(entry.getKey()).append(": $")
                    .append(decimalFormatter.format(entry.getValue())).append("\n");
        }
        return sb.toString();
    }

    private String printOrders(List<Order> orderList) {
        StringBuilder sb = new StringBuilder();
        if (orderList.isEmpty()) {
            sb.append("No orders available.");
        } else {
            for (Order order : orderList) {
                sb.append(order.toString()).append("\n");
            }
        }
        return sb.toString();
    }
    public void saveReportToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(generatedReport);
            System.out.println("Sales report saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save sales report: " + e.getMessage());
        }
    }
}
