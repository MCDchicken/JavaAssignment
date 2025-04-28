package CustomerManagement;

import java.io.*;
import java.util.*;

public class TransactionController {

    private ArrayList<Transaction> transactions;
    private static final String TRANSACTION_FILE = "transactions.txt";

    public TransactionController(){
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    // Search transactions for a specific customer ID
    public static List<Transaction> searchTransactionsByCustomerId(String customerId) {
        List<Transaction> customerTransactions = new ArrayList<>();
        File file = new File(TRANSACTION_FILE);
    
        if (!file.exists()) {
            System.out.println("[Error: Transactions file does not exist.]");
            return customerTransactions; // Return an empty list
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Transaction transaction = new Transaction(line); // Create a Transaction object from the line
                    if (transaction.getCustomerId().equalsIgnoreCase(customerId)) {
                        customerTransactions.add(transaction);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("[Warning] Skipping invalid transaction: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("[Error reading transactions file: " + e.getMessage() + "]");
        }
    
        return customerTransactions;
    }

    // Search and display transactions for a specific customer
    public static void displayTransactionsByCustomerId(String customerId) {
        List<Transaction> customerTransactions = new ArrayList<>();
        File file = new File("transactions.txt");
    
        if (!file.exists()) {
            System.out.println("[No transactions found for Customer ID: " + customerId + "]");
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Transaction transaction = new Transaction(line);
                    if (transaction.getCustomerId().equals(customerId)) {
                        customerTransactions.add(transaction);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("[Warning] Skipping invalid transaction: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("[Error reading transactions file: " + e.getMessage() + "]");
            return;
        }
    
        if (customerTransactions.isEmpty()) {
            System.out.println("[No transactions found for Customer ID: " + customerId + "]");
            return;
        }
    
        System.out.println("\n================================================");
        System.out.println("||             Transaction History            ||");
        System.out.println("================================================");
        for (Transaction transaction : customerTransactions) {
            System.out.printf("|| %-20s: %-20s ||\n", "Transaction ID", transaction.getTransactionId());
            System.out.printf("|| %-20s: %-20s ||\n", "Booking ID", transaction.getBookingId());
            System.out.printf("|| %-20s: %-20s ||\n", "Payment Method", transaction.getPaymentMethod());
            System.out.printf("|| %-20s: RM %-17.2f ||\n", "Total Price", transaction.getTotalPrice());
            System.out.printf("|| %-20s: %-20s ||\n", "Date & Time", transaction.getDate());
            System.out.println("================================================");
        }
    }
    
}