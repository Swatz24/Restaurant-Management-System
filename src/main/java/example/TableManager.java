package example;

import java.util.ArrayList;
import java.util.List;

public class TableManager {
    private List<Table> tables;

    public TableManager() {
        this.tables = new ArrayList<>();
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void removeTable(Table table) {
        tables.remove(table);
    }

    public List<Table> getAllTables() {
        for (Table table : tables) {
            System.out.println("Table ID: " + table.getTableId() + " | Table Size: " + table.getTableSize() + " | Status: " + table.getStatus());
        }
        return tables;
    }


    public Table getTableById(int tableId) {
        for (Table table : tables) {
            if (table.getTableId() == tableId) {
                return table;
            }
        }
        return null; // Table not found
    }

    public void assignCustomerToTable(int tableId) {
        Table table = getTableById(tableId);
        if (table != null) {
            table.setStatus("Occupied");
            System.out.println("Assigned table "+  tableId + " successfully!!");
        }
    }

    public void reserveTable(int tableId) {
        Table table = getTableById(tableId);
        if (table != null) {
            if (table.getStatus().equals("Available")) {
                table.setStatus("Reserved");
                System.out.println("Table " + table.getTableId() + " reserved successfully.");
            } else {
                System.out.println("Table " + table.getTableId() + " is already occupied or reserved.");
            }
        } else {
            System.out.println("Table not found.");
        }
    }


    public void releaseTable(int tableId) {
        Table table = getTableById(tableId);
        if (table != null) {
            table.setStatus("Available");
        }
    }
    public List<Table> findAvailableTable(int partySize) {
        List<Table> availableTables = new ArrayList<>();
        for (Table table : tables) {
            if (table.getStatus().equals("Available") && table.getTableSize() >= partySize) {
                availableTables.add(table);
            }
        }
        return availableTables;
    }
}

