package example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private Map<String, InventoryItem> inventory;
    private String inventoryFilePath;

    private Map<String, Integer> ingredients;

    public InventoryService() {
        ingredients = new HashMap<>();
    }

    public InventoryService(String inventoryFilePath) {
        inventory = new HashMap<>();
        ingredients = new HashMap<>();
        this.inventoryFilePath = inventoryFilePath;
        loadInventory(); // Load inventory from file when creating an instance
    }

    // Add an ingredient to the inventory
    public void addIngredient(String name, int quantity) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            inventory.put(name, new InventoryItem(name, quantity));
        }
        saveInventory(); // Save inventory to file after modification
    }

    // Use an ingredient from the inventory
    public void useIngredient(String name, int quantity) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            int currentQuantity = item.getQuantity();
            if (currentQuantity >= quantity) {
                item.setQuantity(currentQuantity - quantity);
            } else {
                System.out.println("Insufficient quantity of " + name);
            }
        } else {
            System.out.println("Ingredient " + name + " not found");
        }
        saveInventory(); // Save inventory to file after modification
    }

    // Check the quantity of a specific ingredient in the inventory
    public void checkIngredientQuantity(String name) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            int quantity = item.getQuantity();
            System.out.println("Available quantity of " + name + ": " + quantity);
        } else {
            System.out.println("Ingredient " + name + " not found");
        }
    }

    // Check the status of the entire inventory
    public void checkInventoryStatus() {
        System.out.println("Inventory Status:");
        inventory.forEach((name, item) -> System.out.println(name + ": " + item.getQuantity()));
    }

    // Save the inventory to a file
    private void saveInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFilePath))) {
            inventory.forEach((name, item) -> {
                try {
                    writer.write(name + "," + item.getQuantity()); // Write the name and quantity to the file
                    writer.newLine(); // Move to the next line
                } catch (IOException e) {
                    System.out.println("Failed to write inventory item: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Failed to save inventory: " + e.getMessage());
        }
    }

    // Load the inventory from a file
    private void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/minhsmair/SwathiTesting/TestingRestaurant/src/main/java/example/inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String ingredient = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
//                    System.out.println(ingredient);
                    inventory.put(ingredient, new InventoryItem(ingredient, quantity));

                    ingredients.put(ingredient, quantity);

                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load inventory: " + e.getMessage());
        }
    }


    public boolean isIngredientAvailable(String ingredient, int quantity) {
        InventoryItem item = inventory.get(ingredient);
        if (item != null && item.getQuantity() > quantity) {
            return true;
        }
        return false;
    }

    public void checkIngredientAlert() {
//        System.out.println("Ingredient Alert:");
        inventory.forEach((name, item) -> {
            if (item.getQuantity() < 5) {
                System.out.println("Low quantity of " + name + ": " + item.getQuantity());
            }
        });
    }



}
