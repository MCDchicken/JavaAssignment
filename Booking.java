package CustomerManagement;

public class Booking {
    private String bookingId;
    private String customerId;
    private String roomType;
    private int nights;
    private int totalPrice;
    private String status;
    private String checkInDate;
    private String checkOutDate;

    // Constructor
    public Booking(String bookingId, String customerId, String roomType, int nights, int totalPrice, String status, String checkInDate, String checkOutDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomType = roomType;
        this.nights = nights;
        this.totalPrice = totalPrice;
        this.status = status;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Constructor to initialize from a line in bookings.txt
    public Booking(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 8) { // Expecting exactly 8 parts
            throw new IllegalArgumentException("Invalid booking data format");
        }
        this.bookingId = parts[0];
        this.customerId = parts[1];
        this.roomType = parts[2];
        this.nights = Integer.parseInt(parts[3]);
        this.totalPrice = Integer.parseInt(parts[4]);
        this.status = parts[5];
        this.checkInDate = parts[6];
        this.checkOutDate = parts[7];
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

    public String getCheckInDate(){
        return checkInDate;
    }

    public String getCheckOutDate(){
        return checkOutDate;
    }
    
    // Override toString() to save the booking in the correct format
    @Override
    public String toString() {
        return bookingId + "|" + customerId + "|" + roomType + "|" + nights + "|" + totalPrice + "|" + status + "|" + checkInDate + "|" + checkOutDate;
    }

}