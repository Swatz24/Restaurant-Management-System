package example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {
    private static final String INVENTORY_FILE_PATH = "inventory.txt";
    private InventoryService inventoryService;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary inventory file for testing
        File inventoryFile = new File(INVENTORY_FILE_PATH);
        inventoryFile.createNewFile();

        // Initialize the InventoryService with the temporary file path
        inventoryService = new InventoryService(INVENTORY_FILE_PATH);

        // Redirect System.out to a ByteArrayOutputStream
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    // Testing the method addIngredient

    @Test
    public void testAddIngredient() throws IOException {
        // Clear the inventory before each test
        Map<String, InventoryItem> inventory = inventoryService.getInventory();
        inventory.clear();

        // Add an ingredient
        inventoryService.addIngredient("Flour", 10);

        // Retrieve the added ingredient from the inventory
        InventoryItem addedItem = inventory.get("Flour");
        assertNotNull(addedItem); // Assert that the item is not null
        assertEquals(10, addedItem.getQuantity()); // Assert that the quantity is correct

        // Verify the contents of the inventory file
        String expectedContent = "Flour,10\n";
        String actualContent = Files.readString(Path.of(INVENTORY_FILE_PATH));
        assertEquals(expectedContent, actualContent);
    }


    // Testing the method useIngredient

    @Test
    public void testUseIngredient_WithSufficientQuantity() throws IOException {
        // Clear the inventory before each test
        Map<String, InventoryItem> inventory = inventoryService.getInventory();
        inventory.clear();

        // Add an ingredient to the inventory
        inventoryService.addIngredient("Flour", 10);

        // Use the ingredient with a quantity of 5
        inventoryService.useIngredient("Flour", 5);

        // Retrieve the ingredient from the inventory after using it
        InventoryItem usedItem = inventory.get("Flour");
        assertNotNull(usedItem); // Assert that the item is not null
        assertEquals(5, usedItem.getQuantity()); // Assert that the quantity is updated correctly

        // Verify the system output
        String expectedOutput = "Insufficient quantity of Flour";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testUseIngredient_WithInsufficientQuantity() throws IOException {
        // Clear the inventory before each test
        Map<String, InventoryItem> inventory = inventoryService.getInventory();
        inventory.clear();

        // Add an ingredient to the inventory
        inventoryService.addIngredient("Flour", 10);

        // Use the ingredient with a quantity of 15 (greater than available)
        inventoryService.useIngredient("Flour", 15);

        // Verify the system output
        String expectedOutput = "Insufficient quantity of Flour";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);

        // Verify that the ingredient quantity remains unchanged
        InventoryItem item = inventory.get("Flour");
        assertNotNull(item);
        assertEquals(10, item.getQuantity());

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testUseIngredient_WithNonexistentIngredient() throws IOException {
        // Clear the inventory before each test
        Map<String, InventoryItem> inventory = inventoryService.getInventory();
        inventory.clear();

        // Use a nonexistent ingredient
        inventoryService.useIngredient("Salt", 5);

        // Verify the system output
        String expectedOutput = "Ingredient Salt not found";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);

        // Verify that the inventory remains empty
        assertTrue(inventory.isEmpty());

        // Restore System.out
        System.setOut(System.out);
    }

    // Testing the method checkIngredientQuantity

    @Test
    public void testCheckIngredientQuantity_WithExistingIngredient() throws IOException {
        // Clear the inventory before each test
        Map<String, InventoryItem> inventory = inventoryService.getInventory();
        inventory.clear();

        // Add an ingredient to the inventory
        inventoryService.addIngredient("Flour", 10);

        // Check the quantity of the existing ingredient
        inventoryService.checkIngredientQuantity("Flour");

        // Verify the system output
        String expectedOutput = "Available quantity of Flour: 10";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testCheckIngredientQuantity_WithNonexistentIngredient() throws IOException {
        // Clear the inventory before each test
        Map<String, InventoryItem> inventory = inventoryService.getInventory();
        inventory.clear();

        // Check the quantity of a nonexistent ingredient
        inventoryService.checkIngredientQuantity("Salt");

        // Verify the system output
        String expectedOutput = "Ingredient Salt not found";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);

        // Restore System.out
        System.setOut(System.out);
    }

}
