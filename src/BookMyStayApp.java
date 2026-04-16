import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp
 *
 * <p>Use Case 3 (UC3): Centralized Room Inventory Management</p>
 * Demonstrates how HashMap can be used to manage room availability
 * from a single centralized inventory component.
 *
 * @author YourName
 * @version 1.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        System.out.println("===== Current Room Inventory =====");
        inventory.displayInventory();

        // Example update to availability
        System.out.println("\nUpdating availability...\n");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        System.out.println("===== Updated Room Inventory =====");
        inventory.displayInventory();
    }
}

/**
 * RoomInventory manages room availability using a centralized HashMap.
 * It acts as the single source of truth for room counts.
 */
class RoomInventory {

    private HashMap<String, Integer> roomAvailability;

    /**
     * Constructor initializes the room inventory.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();

        // Register initial room availability
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    /**
     * Retrieves availability of a specific room type.
     */
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    /**
     * Updates availability for a specific room type.
     */
    public void updateAvailability(String roomType, int newCount) {
        if (roomAvailability.containsKey(roomType)) {
            roomAvailability.put(roomType, newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    /**
     * Displays all room availability in the inventory.
     */
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}
