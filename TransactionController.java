package CustomerManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionController {

    private ArrayList<Transaction> transactions;


    public TransactionController(){
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    // Search transactions for a specific customer ID
    public static List<Transaction> searchTransactionsByCustomerId(String customerId) {
        List<Transaction> customerTransactions = new ArrayList<>();
        File file = new File("transactions.txt");

        if (!file.exists()) {
            System.out.println("[Error: Transactions file does not exist.]");
            return customerTransactions; // Return an empty list
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Transaction transaction = new Transaction(line); // Create a Transaction object from the line
                if (transaction.getCustomerId().equalsIgnoreCase(customerId)) {
                    customerTransactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("[Error reading transactions file: " + e.getMessage() + "]");
        }

        return customerTransactions;
    }       
    
    // Search and display transactions for a specific customer
    public static void displayTransactionsByCustomerId(String customerId) {
        List<Transaction> customerTransactions = searchTransactionsByCustomerId(customerId);
    
        System.out.println("\nTransaction History for Customer ID: " + customerId);
    
        if (customerTransactions.isEmpty()) {
            System.out.println("[No transactions found for Customer ID: " + customerId + "]");
        } else {
            for (Transaction t : customerTransactions) {
                System.out.println(t);
            }
        }
    }
        
}
