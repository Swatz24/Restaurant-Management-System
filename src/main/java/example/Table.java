package example;

public class Table {
    private int tableId;
    private int tableSize;
    private String status;
    private double revenue;

    public Table(int tableId, int tableSize, String status) {
        this.tableId = tableId;
        this.tableSize = tableSize;
        this.status = status;
        this.revenue = 0;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
