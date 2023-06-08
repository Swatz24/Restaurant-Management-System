package example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TableManagerTest {
    private TableManager tableManager;

    @BeforeEach
    public void setUp() {
        // Initialize the TableManager and add some tables for testing
        tableManager = new TableManager();
        tableManager.addTable(new Table(1, 4, "Available"));
        tableManager.addTable(new Table(2, 2, "Reserved"));
        tableManager.addTable(new Table(3, 4, "Occupied"));
    }

    @AfterEach
    public void tearDown() {
        // Clear the tables after each test
        tableManager.getAllTables().clear();
    }

    // Testing the method GetTableById()

    @Test
    public void testGetTableByIdExistingTable() {
        // Test when the table with the given ID exists
        Table table = tableManager.getTableById(2);
        assertNotNull(table); // Assert that the table is not null
        assertEquals(2, table.getTableId()); // Assert that the correct table is returned
    }

    @Test
    public void testGetTableByIdNonExistingTable() {
        // Test when the table with the given ID does not exist
        Table table = tableManager.getTableById(4);
        assertNull(table); // Assert that the table is null
    }

    // Testing the method AssignCustomerToTable()

    @Test
    public void testAssignCustomerToTableExistingTable() {
        // Test when assigning a customer to an existing table
        tableManager.assignCustomerToTable(2);
        Table table = tableManager.getTableById(2);
        assertNotNull(table); // Assert that the table is not null
        assertEquals("Occupied", table.getStatus()); // Assert that the table status is updated correctly
    }

    @Test
    public void testAssignCustomerToTableNonExistingTable() {
        // Test when assigning a customer to a non-existing table
        tableManager.assignCustomerToTable(4);
        Table table = tableManager.getTableById(4);
        assertNull(table); // Assert that the table is null
    }

    // Testing the method reserveTable()

    @Test
    public void testReserveTableExistingTable() {
        // Test when reserving an existing table
        tableManager.reserveTable(1);
        Table table = tableManager.getTableById(1);
        assertNotNull(table); // Assert that the table is not null
        assertEquals("Reserved", table.getStatus()); // Assert that the table status is updated correctly
    }

    @Test
    public void testReserveTableNonExistingTable() {
        // Test when reserving a non-existing table
        tableManager.reserveTable(4);
        Table table = tableManager.getTableById(4);
        assertNull(table); // Assert that the table is null
    }

    // Testing the method releaseTable()

    @Test
    public void testReleaseTableExistingTable() {
        // Test when releasing an existing table
        tableManager.releaseTable(3);
        Table table = tableManager.getTableById(3);
        assertNotNull(table); // Assert that the table is not null
        assertEquals("Available", table.getStatus()); // Assert that the table status is updated correctly
    }

    @Test
    public void testReleaseTableNonExistingTable() {
        // Test when releasing a non-existing table
        tableManager.releaseTable(4);
        Table table = tableManager.getTableById(4);
        assertNull(table); // Assert that the table is null
    }

}
