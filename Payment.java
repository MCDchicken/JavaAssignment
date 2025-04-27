package CustomerManagement;

public abstract class Payment {
    protected String paymentMethod;
    protected int amount;

    // Constructor
    public Payment(String paymentMethod, int amount) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    // Abstract method to process payment
    public abstract void processPayment();

    // Common method to display payment details
    public void displayPaymentDetails() {
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Amount: RM " + amount);
    }

    // Getter for payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }
}