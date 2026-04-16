import java.util.LinkedList;
import java.util.Queue;

/**
 * BookMyStayApp
 *
 * <p>Use Case 5 (UC5): Booking Request Queue</p>
 * Demonstrates how booking requests are collected using a Queue
 * to preserve arrival order before allocation occurs.
 *
 * @author YourName
 * @version 1.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize booking request queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Guests submit booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Suite Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Double Room"));

        // Display queued requests
        System.out.println("===== Booking Request Queue =====\n");
        requestQueue.displayQueue();
    }
}


/**
 * Reservation represents a guest booking request.
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/**
 * BookingRequestQueue manages incoming booking requests using FIFO ordering.
 */
class BookingRequestQueue {

    private Queue<Reservation> bookingQueue;

    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the queue.
     */
    public void addRequest(Reservation reservation) {
        bookingQueue.offer(reservation);
        System.out.println("Booking request received from " + reservation.getGuestName());
    }

    /**
     * Displays all queued booking requests in arrival order.
     */
    public void displayQueue() {

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        for (Reservation reservation : bookingQueue) {
            reservation.displayReservation();
        }
    }
}
