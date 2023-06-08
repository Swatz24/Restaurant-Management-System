package example;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//private static final String ANSI_RESET = "\u001B[0m"


public class SalesReport {
    private List<Order> orderList;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");
    private String generatedReport;
    public SalesReport(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void generateDailySalesReport(OrderService orderService) {
        LocalDateTime current = LocalDateTime.now();

        Map<String, Integer> popularItems = orderService.getPopularItemsSorted();
        Map<Integer, Double> tableRevenue = orderService.getTableRevenueSorted();

        String report = "-----------------------------\n" +
                "Daily Sales Report \n " +
                "Date: " + dateTimeFormatter.format(current) + "\n-----------------------------\n" +
                "Total Revenue: $" + decimalFormatter.format(calculateTotalRevenue()) + "\n\n" +
                "Most Popular Items: \n" +
                popularItemsToString(popularItems) + "\n" +
                "Tables Sales: \n" +
                tableRevenueToString(tableRevenue) + "\n" +
                "Detailed Orders: \n" +
                printOrders(orderList);
        this.generatedReport = report;
        System.out.println(report);
        saveReportToFile("C:\\CTAC\\RestaurantMgmtSystem\\untitled\\src\\main\\java\\example\\salesReport.txt");
    }

    
    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Order order : orderList) {
            totalRevenue += order.getTotalPrice();
        }
        return totalRevenue;
    }

    public String popularItemsToString(Map<String, Integer> popularItems) {
        StringBuilder sb = new StringBuilder();
        int rank = 1;
        for (Map.Entry<String, Integer> entry : popularItems.entrySet()) {
            sb.append(rank).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" orders\n");
            rank++;
        }
        return sb.toString();
    }

    public String tableRevenueToString(Map<Integer, Double> tableRevenue) {
        StringBuilder sb = new StringBuilder();
        int rank = 1;
        for (Map.Entry<Integer, Double> entry : tableRevenue.entrySet()) {
            sb.append(rank).append(". Table ").append(entry.getKey()).append(": $").append(decimalFormatter.format(entry.getValue())).append("\n");
            rank++;
        }
        return sb.toString();
    }

    public String printOrders(List<Order> orderList) {
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


    public String getGeneratedReport() {
        return generatedReport;
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
