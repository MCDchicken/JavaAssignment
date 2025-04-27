package CustomerManagement;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;
    private String cvv;

    public CreditCardPayment(int amount, String cardNumber, String expiryDate, String cardHolderName, String cvv) {
        super("Credit Card", amount);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment...");
        System.out.println("Cardholder Name: " + cardHolderName);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("CVV: " + cvv);
        System.out.println("Amount: RM " + amount);
        System.out.println("Payment successful!");
    }
}
