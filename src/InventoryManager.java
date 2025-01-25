import java.io.*;
import java.util.*;

public class InventoryManager {
   // Ensure the file exists
    private static final String FILE_NAME = "/workspaces/npci-advcoding-playground-challenge-3-PRIYANKARODDA0/src/inventory.txt";
    
    public static void main(String[] args) {
          initializeFile(); 
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nInventory Manager");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Item");
            System.out.println("3. Update Existing Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addItem(scanner);
                    break;
                case 3:
                    updateItem(scanner);
                    break;
                 case 4:
                    readInventory();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to view the inventory
    public static void viewInventory() {
        Map<String, Integer> inventory = readInventory();
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            inventory.forEach((itemName, itemCount) -> 
                System.out.println(itemName + ": " + itemCount));
        }
    }

    // Method to add a new item to the inventory
    public static void addItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine().trim();
        System.out.print("Enter item count: ");
        int itemCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Map<String, Integer> inventory = readInventory();
        if (inventory.containsKey(itemName)) {
            System.out.println("Item already exists. Use the update option instead.");
        } else {
            inventory.put(itemName, itemCount);
            writeInventory(inventory);
            System.out.println("Item added successfully.");
        }
    }

    // Method to update an existing item in the inventory
    public static void updateItem(Scanner scanner) {
        System.out.print("Enter item name to update: ");
        String itemName = scanner.nextLine().trim();
        System.out.print("Enter new item count: ");
        int newCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Map<String, Integer> inventory = readInventory();
        if (inventory.containsKey(itemName)) {
            inventory.put(itemName, newCount);
            writeInventory(inventory);
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item not found in inventory. Use the add option instead.");
        }
    }

    // Reads the inventory from the file into a map
    public static Map<String, Integer> readInventory() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                 
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    int itemCount = Integer.parseInt(parts[1].trim());
                    inventory.put(itemName, itemCount);
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, return empty inventory
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }
        return inventory;
    }

    // Writes the inventory map to the file
   public static void writeInventory(Map<String, Integer> inventory) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error writing to inventory file: " + e.getMessage());
    }
}

public static void initializeFile() {
    File file = new File(FILE_NAME);
    try {
        if (file.exists()) {
            System.out.println("Inventory file found.");
        } else {
            if (file.createNewFile()) {
                System.out.println("Inventory file not found. Creating a new one...");
            }
        }
    } catch (IOException e) {
        System.out.println("Error creating inventory file: " + e.getMessage());
    }
}


}