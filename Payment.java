package CustomerManagement;

public abstract class Payment {
    protected String paymentMethod;
    protected double totalPrice;

    // Constructor
    public Payment(String paymentMethod, double totalPrice) {
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    // Abstract method to process payment (payment method)
    public abstract void processPayment();

    // Common method to display payment details
    public void displayPaymentDetails() {
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Total Price: RM " + totalPrice);
    }

    // Getter for payment method
    public abstract String getPaymentMethod();
}