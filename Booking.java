package CustomerManagement;

public class Booking {
    private String bookingId;
    private String customerId;
    private String roomType;
    private int nights;
    private int totalPrice;
    private String status; //check is check-in or check-out  //not necessary i think

    // Constructor to initialize booking details from a string
    public Booking(String line) {
        String[] parts = line.split("\\|"); // Split using "|"
        if (parts.length == 6) { // Ensure the line has exactly 6 parts
            this.bookingId = parts[0];
            this.customerId = parts[1];
            this.roomType = parts[2];
            this.nights = Integer.parseInt(parts[3]);
            this.totalPrice = Integer.parseInt(parts[4]);
            this.status = parts[5];
        } else {
            throw new IllegalArgumentException("Invalid booking data format");
        }
    }

    // Constructor to initialize booking details manually
    public Booking(String bookingId, String customerId, String roomType, int nights, int totalPrice, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomType = roomType;
        this.nights = nights;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Display booking details
    public String toString() {
        return bookingId + "|" + customerId + "|" + roomType + "|" + nights + "|" + totalPrice + "|" + status;
    }
}
