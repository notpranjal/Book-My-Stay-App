import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp
 *
 * <p>Use Case 4 (UC4): Room Search & Availability Check</p>
 * Demonstrates how guests can view available rooms without
 * modifying system state.
 *
 * @author YourName
 * @version 1.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize room inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room catalog (domain objects)
        Map<String, Room> roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom());
        roomCatalog.put("Double Room", new DoubleRoom());
        roomCatalog.put("Suite Room", new SuiteRoom());

        // Create search service
        RoomSearchService searchService = new RoomSearchService(inventory, roomCatalog);

        // Guest searches available rooms
        System.out.println("===== Available Rooms =====\n");
        searchService.displayAvailableRooms();
    }
}


/**
 * RoomInventory holds centralized availability state.
 */
class RoomInventory {

    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();

        availability.put("Single Room", 5);
        availability.put("Double Room", 0); // Example: unavailable
        availability.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return availability;
    }
}


/**
 * RoomSearchService performs read-only search operations.
 */
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    /**
     * Displays only available rooms.
     */
    public void displayAvailableRooms() {

        for (String roomType : roomCatalog.keySet()) {

            int availableCount = inventory.getAvailability(roomType);

            // Defensive validation: show only available rooms
            if (availableCount > 0) {

                Room room = roomCatalog.get(roomType);

                room.displayDetails();
                System.out.println("Available Rooms: " + availableCount);
                System.out.println();
            }
        }
    }
}


/**
 * Abstract Room domain model.
 */
abstract class Room {

    private String type;
    private double price;
    private int beds;

    public Room(String type, double price, int beds) {
        this.type = type;
        this.price = price;
        this.beds = beds;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: $" + price);
    }
}


/**
 * Single Room implementation
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 100.0, 1);
    }
}


/**
 * Double Room implementation
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 180.0, 2);
    }
}


/**
 * Suite Room implementation
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 350.0, 3);
    }
}
