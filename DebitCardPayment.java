package CustomerManagement;

public class DebitCardPayment extends Payment {
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;
    private String cvv;

    public DebitCardPayment(int amount, String cardNumber, String expiryDate, String cardHolderName, String cvv) {
        super("Debit Card", amount);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing debit card payment...");
        System.out.println("Cardholder Name: " + cardHolderName);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("CVV: " + cvv);
        System.out.println("Amount: RM " + amount);
        System.out.println("Payment successful!");
    }
}
