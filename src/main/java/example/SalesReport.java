package example;


import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.regex.Pattern;


public class SalesReport {
    private List<Order> orderList;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");
    private String generatedReport;
    public SalesReport(List<Order> orderList) {
        this.orderList = orderList;
    }

    // For text colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    public void generateDailySalesReport(OrderService orderService) {
        LocalDateTime current = LocalDateTime.now();

        Map<String, Integer> popularItems = orderService.getPopularItemsSorted();
        Map<Integer, Double> tableRevenue = orderService.getTableRevenueSorted();

        String report = ANSI_GREEN + "-----------------------------\n" +ANSI_RESET + ANSI_YELLOW +
                "Daily Sales Report" + ANSI_RESET + "\n" +
                "Date: " + dateTimeFormatter.format(current) + ANSI_GREEN +  "\n-----------------------------\n" + ANSI_RESET + ANSI_YELLOW +
                "Total Revenue: $" + decimalFormatter.format(calculateTotalRevenue()) + ANSI_RESET + "\n\n" + ANSI_CYAN +
                "Most Popular Items: \n" + ANSI_RESET +
                popularItemsToString(popularItems) + "\n" + ANSI_CYAN +
                "Tables Sales: \n" + ANSI_RESET +
                tableRevenueToString(tableRevenue) + "\n" + ANSI_CYAN +
                "Detailed Orders: \n" + ANSI_RESET +
                printOrders(orderList);


        this.generatedReport = report;
        System.out.println(report);
        String filePath = "src/main/java/example/salesReport.txt";
        saveReportToFile(filePath);
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
            sb.append(ANSI_YELLOW + rank + ANSI_RESET).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" orders\n");
            rank++;
        }
        return sb.toString();
    }

    public String tableRevenueToString(Map<Integer, Double> tableRevenue) {
        StringBuilder sb = new StringBuilder();
        int rank = 1;
        for (Map.Entry<Integer, Double> entry : tableRevenue.entrySet()) {
            sb.append(ANSI_YELLOW + rank + ANSI_RESET).append(". Table ").append(entry.getKey()).append(": $").append(decimalFormatter.format(entry.getValue())).append("\n");
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
        String reportWithoutEscapeSequences = generatedReport.replaceAll("\u001B\\[[;\\d]*m", "");
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdir();
            }

            FileWriter writer = new FileWriter(file);
            writer.write(reportWithoutEscapeSequences);
            writer.close();

            System.out.println("Sales report saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save sales report: " + e.getMessage());
        }
    }

}
