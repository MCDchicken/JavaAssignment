package CustomerManagement;

public class Transaction {
    private String transactionId;
    private String customerId;
    private String bookingId; // New field for Booking ID
    private String paymentMethod;
    private double totalPrice;
    private String date;

    // Constructor to parse a transaction line
    public Transaction(String line) {
        String[] parts = line.split("\\|"); // Split the line by '|'
        if (parts.length != 6) { // Updated to include bookingId
            throw new IllegalArgumentException("Invalid transaction format: " + line);
        }

        this.transactionId = parts[0];
        this.customerId = parts[1];
        this.bookingId = parts[2]; // Parse Booking ID
        this.paymentMethod = parts[3];
        try {
            this.totalPrice = Double.parseDouble(parts[4]); // Parse total price as double
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid total price in transaction: " + line);
        }
        this.date = parts[5];
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    // Override toString for display
    @Override
    public String toString() {
        return transactionId + "|" + customerId + "|" + bookingId + "|" + paymentMethod + "|" + totalPrice + "|" + date;
    }
}