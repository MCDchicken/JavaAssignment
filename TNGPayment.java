package CustomerManagement;

public class TNGPayment extends Payment {

    public TNGPayment(double totalPrice) {
        super("TNG eWallet", totalPrice);
    }

    @Override //to Payment class
    public void processPayment() {
        String tngQR = (
        "	@@@@  @@ @@   @@@  @@@@" + "\n" +
        "	@  @    @      @   @  @" + "\n" +
        "	@@@@  @@ @  @@@@   @@@@" + "\n" +
        "	         @             " + "\n" +
        "	 @  @@@   @@     @   @@" + "\n" +
        "	  @@   @@   @ @   @@ @ " + "\n" +
        "	 @    @@      @    @@@ " + "\n" +
        "	             @         " + "\n" +
        "	@@@@  @@ @@     @  @@@@" + "\n" +
        "	@  @    @@ @  @@  @@  @" + "\n" +
        "	@@@@   @@    @@     @@@"
    );
        System.out.println("[Processing TNG eWallet payment...]");
        System.out.println(tngQR);
        System.out.println("Total Price: RM " + totalPrice);
        System.out.println("[Payment successful!]");
    }
    
    @Override
    public String getPaymentMethod() {
        return "TNG eWallet";
    }

}