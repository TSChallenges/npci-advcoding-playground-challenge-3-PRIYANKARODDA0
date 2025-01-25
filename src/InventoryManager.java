import java.io.*;
import java.util.*;

public class InventoryManager {
    public static void main(String[] args) {
        String fileName = "inventory.txt";
        
        // Example usage
        readInventory(fileName);
        addItem(fileName, "Apples", 10);
        updateItem(fileName, "Apples", 20);
        readInventory(fileName);
    }

    public static void readInventory(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            try {
                new File(fileName).createNewFile();
            } catch (IOException ioException) {
                System.out.println("Error creating inventory file.");
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }
    }

    public static void addItem(String fileName, String itemName, int itemCount) {
        Map<String, Integer> inventory = loadInventory(fileName);
        if (inventory.containsKey(itemName)) {
            System.out.println("Item already exists.");
            return;
        }
        inventory.put(itemName, itemCount);
        saveInventory(fileName, inventory);
        System.out.println("Item added: " + itemName + " - " + itemCount);
    }

    public static void updateItem(String fileName, String itemName, int itemCount) {
        Map<String, Integer> inventory = loadInventory(fileName);
        if (!inventory.containsKey(itemName)) {
            return;
        }
        inventory.put(itemName, itemCount);
        saveInventory(fileName, inventory);
        System.out.println("Item updated: " + itemName + " - " + itemCount);
    }

    private static Map<String, Integer> loadInventory(String fileName) {
        Map<String, Integer> inventory = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    inventory.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory.");
        }
        return inventory;
    }

    private static void saveInventory(String fileName, Map<String, Integer> inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }
}