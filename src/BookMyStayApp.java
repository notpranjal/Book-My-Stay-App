import java.util.*;

/**
 * BookMyStayApp
 *
 * <p>Use Case 6 (UC6): Reservation Confirmation & Room Allocation</p>
 * Demonstrates how booking requests are processed safely,
 * ensuring unique room assignments and consistent inventory updates.
 *
 * @author YourName
 * @version 1.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        InventoryService inventory = new InventoryService();

        // Initialize booking request queue
        Queue<Reservation> requestQueue = new LinkedList<>();

        // Sample booking requests
        requestQueue.offer(new Reservation("Alice", "Single Room"));
        requestQueue.offer(new Reservation("Bob", "Suite Room"));
        requestQueue.offer(new Reservation("Charlie", "Single Room"));

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Process booking requests in FIFO order
        System.out.println("===== Processing Booking Requests =====\n");

        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            bookingService.processReservation(request);
        }

        // Display final inventory
        System.out.println("\n===== Updated Inventory =====");
        inventory.displayInventory();
    }
}


/**
 * Reservation represents a booking request from a guest.
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/**
 * InventoryService manages centralized room availability.
 */
class InventoryService {

    private Map<String, Integer> availability;

    public InventoryService() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }

    public void displayInventory() {
        for (String type : availability.keySet()) {
            System.out.println(type + " -> Available: " + availability.get(type));
        }
    }
}


/**
 * BookingService processes reservations and performs room allocation.
 */
class BookingService {

    private InventoryService inventory;

    // Tracks all allocated room IDs to prevent duplicates
    private Set<String> allocatedRoomIds;

    // Maps room type to assigned room IDs
    private Map<String, Set<String>> roomAllocation;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocation = new HashMap<>();
    }

    /**
     * Processes a reservation request.
     */
    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();
        String guest = reservation.getGuestName();

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Reservation failed for " + guest +
                    " (No available " + roomType + ")");
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Ensure uniqueness
        while (allocatedRoomIds.contains(roomId)) {
            roomId = generateRoomId(roomType);
        }

        // Record allocation
        allocatedRoomIds.add(roomId);

        roomAllocation
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory immediately
        inventory.decrementRoom(roomType);

        // Confirm reservation
        System.out.println("Reservation confirmed for " + guest +
                " | Room Type: " + roomType +
                " | Assigned Room ID: " + roomId);
    }

    /**
     * Generates a unique room ID.
     */
    private String generateRoomId(String roomType) {
        String prefix = roomType.substring(0, 2).toUpperCase();
        int randomNumber = (int) (Math.random() * 900 + 100);
        return prefix + "-" + randomNumber;
    }
}
