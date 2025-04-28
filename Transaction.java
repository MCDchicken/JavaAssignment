package CustomerManagement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String transactionId;
    private String customerId;
    private String roomType;
    private int nights;
    private int totalPrice;
    private String paymentMethod;
    private Date date;

    // Constructor to initialize from a string (e.g., from a file)
    public Transaction(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) { // Expecting exactly 5 parts
            throw new IllegalArgumentException("Invalid transaction data format");
        }
        try {
            this.transactionId = parts[0];
            this.customerId = parts[1];
            this.paymentMethod = parts[2];
            this.totalPrice = Integer.parseInt(parts[3]);
    
            // Parse the date string into a Date object
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.date = sdf.parse(parts[4]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid transaction data format", e);
        }
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Date getDate() {
        return date;
    }

    // Display transaction details
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        return transactionId + "|" + customerId + "|" + roomType + "|" + nights + "|" + totalPrice + "|" + paymentMethod + "|" + sdf.format(date);
    }
}