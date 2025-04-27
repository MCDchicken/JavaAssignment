package CustomerManagement;

public class TNGPayment extends Payment {

    public TNGPayment(int amount) {
        super("TNG eWallet", amount);
    }

    @Override
    public void processPayment() {
        System.out.println("Processing TNG eWallet payment...");
        System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[QRCODE]]]]]]]]]]");
        System.out.println("Amount: RM " + amount);
        System.out.println("Payment successful!");
    }
}