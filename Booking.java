package CustomerManagement;

public class Booking {
    private String bookingId;
    private String customerId;
    private String roomType;
    private int nights;
    private double totalPrice;
    private String status;
    private String checkInDate;
    private String checkOutDate;
    private String selectedRoomNumber; // Ensure this is a String

    // Constructor
    public Booking(String bookingId, String customerId, String roomType, int nights, double totalPrice, String status, String checkInDate, String checkOutDate, String selectedRoomNumber) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomType = roomType;
        this.nights = nights;
        this.totalPrice = totalPrice;
        this.status = status;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.selectedRoomNumber = selectedRoomNumber;
    }

    // Constructor to initialize from a line in bookings.txt
    public Booking(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 9) { // Expecting exactly 9 parts
            throw new IllegalArgumentException("Invalid booking data format");
        }
        this.bookingId = parts[0];
        this.customerId = parts[1];
        this.roomType = parts[2];
        this.nights = Integer.parseInt(parts[3]);
        this.totalPrice = Double.parseDouble(parts[4]);
        this.status = parts[5];
        this.checkInDate = parts[6];
        this.checkOutDate = parts[7];
        this.selectedRoomNumber = parts[8];
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getSelectedRoomNumber() {
        return selectedRoomNumber;
    }

    // Override toString() to save the booking in the correct format
    @Override
    public String toString() {
        return bookingId + "|" + customerId + "|" + roomType + "|" + nights + "|" + String.format("%.2f", totalPrice) + "|" + status + "|" + checkInDate + "|" + checkOutDate + "|" + selectedRoomNumber;
    }
}