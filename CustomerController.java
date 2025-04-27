package CustomerManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerController {

    private Scanner scanner;

    //later on need to pass by other classes
    // Room availability
    private static int standardRoomAvailability = 20;
    private static int deluxeRoomAvailability = 32;
    private static int familyRoomAvailability = 5;
    private static int suiteAvailability = 15;
    private static int executiveRoomAvailability = 18;

    // Room price
    //change to array
    private static int standardRoomPrice = 150;
    private static int deluxeRoomPrice = 250;
    private static int familyRoomPrice = 350;
    private static int suitePrice = 600;
    private static int executiveRoomPrice = 350;

    //File
    private static final String CUSTOMER_FILE = "customer.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    private static Customer loggedInCustomer = null;

    private static final int DEPOSIT = 200;

    public CustomerController(){
        scanner = new Scanner(System.in);
    }

    public void custRegister() {
        System.out.println("===============================================");
        System.out.println("||             Customer Register             ||");
        System.out.println("===============================================");
    
        String username;
        do {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();
    
            if (username.contains(",")) {
                System.out.println("\n[Username cannot contain ','. Please try another.]");
                continue;
            }
            if (searchCustomer(username) != null) {
                System.out.println("\n[Username already taken. Try another one.]\n");
                continue;
            }
            break;
        } while (true);
    
        System.out.print("Enter fullname: ");
        String fullname = scanner.nextLine().trim();
    
        String password;
        while (true) {
            System.out.print("Enter password (must be more than 6 characters and contain at least one uppercase letter): ");
            password = scanner.nextLine().trim();
    
            if (password.length() > 6 && password.matches(".*[A-Z].*")) {
                break;
            } else {
                System.out.println("\n[Invalid password. Please ensure it is more than 6 characters and contains at least one uppercase letter]\n");
            }
        }
    
        String phoneNo;
        while (true) {
            System.out.print("Enter phone number (e.g. 0121234567): ");
            phoneNo = scanner.nextLine().trim();
    
            if (phoneNo.length() == 10 && phoneNo.matches("\\d+")) {
                break;
            } else {
                System.out.println("\n[Invalid phone number format. Please enter exactly 10 digits]\n");
            }
        }
    
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();
    
            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                break;
            } else {
                System.out.println("\n[Invalid email format. Please enter again]");
                System.out.println("[e.g. user@example.com]\n");
            }
        }
    
        // Generate Customer ID
        String customerId = generateCustomerId();
    
        // Save customer to file
        String customerData = customerId + "|" + username + "|" + fullname + "|" + password + "|" + phoneNo + "|" + email;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            bw.write(customerData);
            bw.newLine();
            System.out.println("\n[Registered successfully! Your Customer ID is: " + customerId + "]\n");
            System.out.println("[Please login to continue.]\n");
        } catch (IOException e) {
            System.out.println("\n[Error saving customer data: " + e.getMessage() + "]\n");
            return;
        }
        custLogin();
    }

    private String generateCustomerId(){
        File file = new File(CUSTOMER_FILE);
        int id = 1; // Start with ID 1 if the file is empty
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0 && parts[0].startsWith("CUST")) {
                        try {
                            int currentId = Integer.parseInt(parts[0].substring(4)); // Extract numeric part of ID
                            if (currentId >= id) {
                                id = currentId + 1; // Increment ID
                            }
                        } catch (NumberFormatException e) {
                            // Ignore invalid IDs
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("[Error reading customer file: " + e.getMessage() + "]");
            }
        }
        return "CUST" + id; // Prefix the ID with "CUST"
    }
 
    public void custLogin(){
        System.out.println("===============================================");
        System.out.println("||               Customer Login              ||");
        System.out.println("===============================================");
    
        while (true) {
            System.out.print("Enter username (or enter 'back' to return to Customer Menu): ");
            String username = scanner.nextLine();
    
            if (username.equalsIgnoreCase("BACK")) {
                System.out.println("\n[Returning to Customer Menu...]\n");
                displayLoginRegisterMenu();
                return;
            }
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
    
            try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
                String line;
                boolean usernameFound = false;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        String lineUsername = parts[1];
                        String linePassword = parts[3];
                        if (lineUsername.equals(username)) {
                            usernameFound = true;
                            if (linePassword.equals(password)) {
                                loggedInCustomer = new Customer(line);
                                System.out.println("\n[Login successful! Welcome, '" + loggedInCustomer.getFullName() + "']\n");
                                runCustMainMenu();
                                return;
                            } else {
                                System.out.println("\n[Invalid password. Please try again.]\n");
                                break;
                            }
                        }
                    }
                }
                if (!usernameFound) {
                    System.out.println("\n[Invalid username. Please try again.]\n");
                }
            } catch (IOException e) {
                System.out.println("\n[Error reading customer file: " + e.getMessage() + "]\n");
            }
        }
    }
    
    public void runCustMenu(){
        displayLoginRegisterMenu();
        int choice;
        do{
            while (true) {
                System.out.print("Please choose an option: ");
                if (scanner.hasNextInt()) { // Check if the input is an integer
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break; // Exit the loop if input is valid
                } else {
                    System.out.println("[Invalid option. Please enter a valid number within 0-2!]");
                    scanner.nextLine(); // Clear the invalid input
                    choice = -1;
                    continue;
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("\n[Customer Register selected]\n");
                    custRegister();
                    break;
                case 2:
                    System.out.println("\n[Customer Login selected]\n");
                    custLogin();
                    break;
                case 55:
                    displayLoginRegisterMenu();
                    break;
                case 0:
                    System.out.println("Goodbye! Thanks for using our system!");
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter your option again. \n[Note: Enter '55' for display menu again.]\n");
                    break;
            }
        }while (choice != 0);
    }

    //run customer main menu
    public void runCustMainMenu(){
        displayCustMainMenu();
        int choice;
        do{
            while (true) {
                System.out.print("Please choose an option: ");
                if (scanner.hasNextInt()) { // Check if the input is an integer
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break; // Exit the loop if input is valid
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid input
                    choice = -1;
                    continue;
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("\n[View Profile selected]\n");    ///////////////not done yet////////////////
                    viewCustProfile();
                    break;
                case 2:
                    System.out.println("\n[View Room Type selected]\n");
                    viewRoomType();
                    break;
                case 3:
                    System.out.println("\n[View Room Availability selected]\n");
                    viewRoomAvailable();
                    break;
                case 4:
                    System.out.println("\n[Book A Room selected]\n");
                    custBookRoom();
                    break;
                case 5:
                    System.out.println("\n[View My Booking selected]\n");
                    viewBooking();
                    break;
                case 6:
                    System.out.println("\n[Check-in selected]\n");   
                    custCheckIn();
                    break;
                case 7:
                    System.out.println("\n[Check-out selected]\n");   
                    custCheckOut();
                    break;
                case 8:
                    System.out.println("\n[Check Transaction History selected]\n");   
                    TransactionController.displayTransactionsByCustomerId(loggedInCustomer.getCustomerId());
                    showPostCheckoutOptions();
                    break;
                case 55:  //replay customer main menu
                    displayCustMainMenu();
                    break;
                case 0:
                    System.out.println("Goodbye! Thanks for using our system!");
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter your option again. \n[Note: Enter '55' for display menu again.]\n");
                    break;
            }
        }while (choice != 0);
    }

    //view customer profile
    public void viewCustProfile() {

        // Display the customer's profile
        System.out.println("================================================");
        System.out.println("||                Your Profile                ||");
        System.out.println("================================================");
        System.out.printf("|| %-20s: %-20s ||\n", "Customer id", loggedInCustomer.getCustomerId());
        System.out.printf("|| %-20s: %-20s ||\n", "Username", loggedInCustomer.getUsername());
        System.out.printf("|| %-20s: %-20s ||\n", "Full Name", loggedInCustomer.getFullName());
        System.out.printf("|| %-20s: %-20s ||\n", "Phone Number", loggedInCustomer.getPhoneNo());
        System.out.printf("|| %-20s: %-20s ||\n", "Email", loggedInCustomer.getEmail());
        System.out.println("================================================");
    
        // Prompt the user for an action
        int choice;
        while (true) {
            System.out.println("Select an option:");
            System.out.println("[1] Modify Profile");
            System.out.println("[0] Return to Customer Main Menu");
            System.out.print("Please choose an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (choice == 0 || choice == 1) {
                    break;
                } else {
                    System.out.println("[Invalid option. Please enter 0 or 1]");
                }
            } else {
                System.out.println("[Invalid input. Please enter a number]\n");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    
        if (choice == 0) {
            System.out.println("\n[Returning to Customer Main Menu...]\n");
            runCustMainMenu();
            return;
        }
    
        // Modify Profile
        while (true) {
            displayModifyProfileMenu();
            System.out.print("Select an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (choice >= 0 && choice <= 4) {
                    break;
                } else {
                    System.out.println("\n[Invalid option. Please enter 0-4.]\n");
                }
            } else {
                System.out.println("\n[Invalid input. Please enter a number.]\n");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    
        if (choice == 0) {
            System.out.println("\n[Modification cancelled. Returning to Customer Main Menu...]\n");
            runCustMainMenu();
            return;
        }
    
        switch (choice) {
            case 1: // Modify Username
                System.out.print("Enter new username: ");
                String newUsername = scanner.nextLine().trim();
                if (newUsername.isEmpty()) {
                    System.out.println("\n[Username cannot be empty.]\n");
                    return;
                }
                if (searchCustomer(newUsername) != null) {
                    System.out.println("\n[Username already taken. Try another one.]\n");
                    return;
                }
                loggedInCustomer.setUsername(newUsername);
                break;
            case 2: // Modify Full Name
                System.out.print("Enter new full name: ");
                String newFullName = scanner.nextLine().trim();
                if (newFullName.isEmpty()) {
                    System.out.println("\n[Full name cannot be empty.]\n");
                    return;
                }
                loggedInCustomer.setFullName(newFullName);
                break;
    
            case 3: // Modify Password
                String newPassword;
                while (true) {
                    System.out.print("Enter new password (must be more than 6 characters and contain at least one uppercase letter): ");
                    newPassword = scanner.nextLine().trim();
                    if (newPassword.length() > 6 && newPassword.matches(".*[A-Z].*")) {
                        break;
                    } else {
                        System.out.println("\n[Invalid password. Please ensure it is more than 6 characters and contains at least one uppercase letter]\n");
                    }
                }
                loggedInCustomer.setPassword(newPassword);
                break;
    
            case 4: // Modify Phone Number
                String newPhoneNo;
                while (true) {
                    System.out.print("Enter new phone number (e.g. 0121234567): ");
                    newPhoneNo = scanner.nextLine().trim();
                    if (newPhoneNo.length() == 10 && newPhoneNo.matches("\\d+")) {
                        break;
                    } else {
                        System.out.println("\n[Invalid phone number format. Please enter exactly 10 digits]\n");
                    }
                }
                loggedInCustomer.setPhoneNo(newPhoneNo);
                break;
    
            case 5: // Modify Email
                String newEmail;
                while (true) {
                    System.out.print("Enter new email: ");
                    newEmail = scanner.nextLine().trim();
                    if (newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        break;
                    } else {
                        System.out.println("\n[Invalid email format. Please enter again]\n[e.g. user@example.com]\n");
                    }
                }
                loggedInCustomer.setEmail(newEmail);
                break;
        }
    
        // Update customer.txt
        updateCustomerFile();
        System.out.println("\n[Profile updated successfully!]\n");
    
        // Redisplay profile
        viewCustProfile();
    }


    //after modify profile update to the newest version
    private void updateCustomerFile() {
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) {
            System.out.println("[Error: Customer file does not exist.]");
            return;
        }
    
        StringBuilder newFileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 1 && parts[0].equals(loggedInCustomer.getCustomerId())) {
                    // Update the line with the latest loggedInCustomer data
                    line = loggedInCustomer.toString();
                }
                newFileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("[Error reading customer file: " + e.getMessage() + "]");
            return;
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            bw.write(newFileContent.toString());
            System.out.println("[Customer file updated successfully!]");
        } catch (IOException e) {
            System.out.println("[Error writing to customer file: " + e.getMessage() + "]");
        }
    }
    
    //view room type
    public void viewRoomType(){
        int choice;
        displayRoomType();

        System.out.println("\n[1] Book a room");
        System.out.println("[2] View Room Availability");     
        System.out.println("[3] Return to Customer Main Menu");
        System.out.println("[0] Exit");

        do{
            while (true) {
                System.out.print("Please choose an option: ");
                if (scanner.hasNextInt()) { // Check if the input is an integer
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break; // Exit the loop if input is valid
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid input
                    choice = -1;
                    continue;
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("\n[Book A Room selected]\n");
                    custBookRoom();
                    break;
                case 2:
                    System.out.println("\n[View Room Availability selected]\n");
                    viewRoomAvailable();
                    break;
                case 3:
                    System.out.println("\n[Returning to Customer Main Menu...]\n");
                    runCustMainMenu();
                    break;
                case 0:
                    System.out.println("Goodbye! Thanks for using our system!");
                    System.exit(0);
                case 55:
                    displayRoomType();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter your option again. \n[Note: Enter '55' for display menu again.]\n");
                    break;
            }
        }while (choice != 0);

    }
    //view room availability
    public void viewRoomAvailable(){
        int choice;
        displayRoomAvailable();

        System.out.println("\n[1] Book a room");
        System.out.println("[2] View Room Type");     
        System.out.println("[3] Return to Customer Main Menu");
        System.out.println("[0] Exit");

        do{
            while (true) {
                System.out.print("Please choose an option: ");
                if (scanner.hasNextInt()) { // Check if the input is an integer
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break; // Exit the loop if input is valid
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid input
                    choice = -1;
                    continue;
                }
            }
    
            switch (choice) {
                case 1:
                    System.out.println("\n[Book A Room selected]\n");
                    custBookRoom();
                    break;
                case 2:
                    System.out.println("\n[View Room Type selected]\n");
                    viewRoomType();
                    break;
                case 3:
                    System.out.println("\n[Returning to Customer Main Menu...]\n");
                    runCustMainMenu();
                    break;
                case 0:
                    System.out.println("Goodbye! Thanks for using our system!");
                    System.exit(0);
                case 55:
                    displayRoomType();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter your option again. \n[Note: Enter '55' for display menu again.]\n");
                    break;
            }
        }while(choice!=0);
        
    }

    //book a room
    public void custBookRoom() {
    int choice;
    System.out.println("Available Room Types (per night):");
    System.out.println("[1] Standard Room: RM " + standardRoomPrice);
    System.out.println("[2] Deluxe Room: RM " + deluxeRoomPrice);
    System.out.println("[3] Family Room: RM " + familyRoomPrice);
    System.out.println("[4] Suite: RM " + suitePrice);
    System.out.println("[5] Executive Room: RM " + executiveRoomPrice);
    System.out.println("[6] Return to Customer Main Menu");
    System.out.println("[0] Exit");

    do {
        System.out.print("Please choose an option: ");
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            if (choice >= 0 && choice <= 6) { // Ensure valid range
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid number.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    } while (true);

    String roomType = "";
    int price = 0;
    int availability = 0;

    switch (choice) {
        case 1:
            roomType = "Standard Room";
            price = standardRoomPrice;
            availability = standardRoomAvailability;
            break;
        case 2:
            roomType = "Deluxe Room";
            price = deluxeRoomPrice;
            availability = deluxeRoomAvailability;
            break;
        case 3:
            roomType = "Family Room";
            price = familyRoomPrice;
            availability = familyRoomAvailability;
            break;
        case 4:
            roomType = "Suite";
            price = suitePrice;
            availability = suiteAvailability;
            break;
        case 5:
            roomType = "Executive Room";
            price = executiveRoomPrice;
            availability = executiveRoomAvailability;
            break;
        case 6:
            runCustMainMenu();
            return;
        case 0:
            System.out.println("Goodbye! Thanks for using our system!");
            System.exit(0);
        default:
            System.out.println("Invalid room type. Please select again.");
            return;
    }

    if (availability <= 0) {
        System.out.println(roomType + " is not available.");
        return;
    }

    // Get the check-in date
    String checkInDateStr;
    Date checkInDate = null;
    while (true) {
        System.out.print("Enter check-in date (yyyy-MM-dd): ");
        checkInDateStr = scanner.nextLine().trim();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Ensure strict date parsing
            checkInDate = sdf.parse(checkInDateStr);

            // Ensure the check-in date is not in the past
            if (checkInDate.before(new Date())) {
                System.out.println("[Invalid date. Check-in date cannot be in the past.]");
                continue;
            }
            break;
        } catch (Exception e) {
            System.out.println("[Invalid date format. Please enter in yyyy-MM-dd format.]");
        }
    }

    // Get the number of nights
    int nights;
    while (true) {
        System.out.print("Enter number of nights: ");
        if (scanner.hasNextInt()) {
            nights = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            if (nights > 0) {
                break;
            } else {
                System.out.println("[Number of nights must be positive]");
            }
        } else {
            System.out.println("[Invalid input. Please enter a valid number]");
            scanner.nextLine(); // Clear invalid input
        }
    }

    // Calculate the check-out date
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(checkInDate);
    calendar.add(Calendar.DATE, nights);
    Date checkOutDate = calendar.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String checkOutDateStr = sdf.format(checkOutDate);

    // Generate a unique booking ID
    String bookingId = generateBookingId();
    int totalPrice = (price * nights) + DEPOSIT;

    // Create a Booking object
    Booking booking = new Booking(bookingId, loggedInCustomer.getCustomerId(), roomType, nights, totalPrice, "PENDING", checkInDateStr, checkOutDateStr);

    // Invoice
    generateInvoice(bookingId, roomType, nights, totalPrice, checkInDateStr, checkOutDateStr);
    System.out.print("Confirm booking? (y/n): ");
    String confirm;
    while (true) {
        try {
            confirm = scanner.nextLine().trim().toLowerCase(); // Convert input to lowercase for easier comparison
            if (confirm.length() == 1 && (confirm.equals("y") || confirm.equals("n"))) {
                break; // Exit the loop if the input is valid
            } else {
                throw new IllegalArgumentException("[Invalid input. Please enter 'y' for yes or 'n' for no]");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Confirm booking? (y/n): ");
        }
    }

    if (confirm.equals("n")) {
        System.out.println("Booking cancelled. Returning to Customer Main Menu.");
        runCustMainMenu();
        return;
    }

    // Save booking to file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE, true))) {
        bw.write(booking.toString());
        bw.newLine();
        System.out.println("[Booking successful!]");
    } catch (IOException e) {
        System.out.println("Error saving booking: " + e.getMessage());
        return;
    }

    // Update room availability
    switch (choice) {
        case 1: standardRoomAvailability--; break;
        case 2: deluxeRoomAvailability--; break;
        case 3: familyRoomAvailability--; break;
        case 4: suiteAvailability--; break;
        case 5: executiveRoomAvailability--; break;
    }

    // Jump to payment method
    System.out.println("\nProceeding to payment...");
    runPaymentMethod(totalPrice);
}

    public void generateInvoice(String bookingId, String roomType, int nights, int totalPrice, String checkInDate, String checkOutDate) {
        System.out.println("\n================================================");
        System.out.println("||                   INVOICE                  ||");
        System.out.println("================================================");
        System.out.printf("|| %-20s: %-20s ||\n", "Booking ID", bookingId);
        System.out.printf("|| %-20s: %-20s ||\n", "Customer ID", loggedInCustomer.getCustomerId());
        System.out.printf("|| %-20s: %-20s ||\n", "Customer Name", loggedInCustomer.getFullName());
        System.out.printf("|| %-20s: %-20s ||\n", "Room Type", roomType);
        System.out.printf("|| %-20s: %-20d ||\n", "Nights", nights);
        System.out.printf("|| %-20s: %-20s ||\n", "Check-In Date", checkInDate);
        System.out.printf("|| %-20s: %-20s ||\n", "Check-Out Date", checkOutDate);
        System.out.printf("|| %-20s: %-20d ||\n", "Deposit", DEPOSIT);
        System.out.printf("|| %-20s: RM %-17d ||\n", "Total Price", totalPrice);
        System.out.println("===============================================");
        System.out.println("[Note: Please proceed to payment to confirm your booking.]");
    }    

    //generate booking id
    private String generateBookingId() {
        File file = new File(BOOKINGS_FILE);
        int maxId = 0; // Start with 0 to find the highest ID
    
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0 && parts[0].startsWith("BK")) {
                        try {
                            int currentId = Integer.parseInt(parts[0].substring(2)); // Extract numeric part of ID
                            if (currentId > maxId) {
                                maxId = currentId; // Update maxId if currentId is larger
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[Warning: Invalid booking ID format in file: " + parts[0] + "]");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("[Error reading bookings file: " + e.getMessage() + "]");
            }
        }
    
        // Increment maxId to generate a new unique ID
        int newId = maxId + 1;
        return "BK" + newId; // Prefix the ID with "BK"
    }

    //view my booking
    public void viewBooking() {
        System.out.println("Bookings for '" + loggedInCustomer.getUsername() + "':");
        boolean hasBookings = false;
    
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) {
            System.out.println("\n[No bookings found]");
            showPostCheckoutOptions();
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
    
            // Display the header once
            System.out.println("=====================================================");
            System.out.println("||                  Booking Details               ||");
            System.out.println("=====================================================");
    
            while ((line = br.readLine()) != null) {
                Booking booking = new Booking(line);
                if (booking.getCustomerId().equals(loggedInCustomer.getCustomerId())) {
                    hasBookings = true;
                    System.out.printf("|| %-16s : %-28s ||\n", "Booking ID", booking.getBookingId());
                    System.out.printf("|| %-16s : %-28s ||\n", "Room Type", booking.getRoomType());
                    System.out.printf("|| %-16s : %-28d ||\n", "Nights", booking.getNights());
                    System.out.printf("|| %-16s : RM %-25d ||\n", "Total Price", booking.getTotalPrice());
                    System.out.printf("|| %-16s : %-28s ||\n", "Status", booking.getStatus());
                    System.out.printf("|| %-16s : %-28s ||\n", "Check-In Date", booking.getCheckInDate());
                    System.out.printf("|| %-16s : %-28s ||\n", "Check-Out Date", booking.getCheckOutDate());
                    System.out.println("=====================================================");
                }
            }
        } catch (IOException e) {
            System.out.println("\n[Error reading bookings: " + e.getMessage() + "]\n");
            return;
        }
    
        if (!hasBookings) {
            System.out.println("\n[No bookings found]");
            showPostCheckoutOptions();
            return;
        }
    
        // Prompt user to return to main menu or exit
        int choice;
        do {
            System.out.println("\nPlease choose an option:");
            System.out.println("[1] Return to Customer Main Menu");
            System.out.println("[0] Exit");
            System.out.print("Please choose an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                if (choice == 1) {
                    System.out.println("\n[Returning to Main Menu...]\n");
                    runCustMainMenu();
                    return;
                } else if (choice == 0) {
                    System.out.println("Goodbye! Thanks for using our system!");
                    System.exit(0);
                } else {
                    System.out.println("[Invalid option. Please enter 1 or 0.]");
                }
            } else {
                System.out.println("[Invalid input. Please enter a number.]");
                scanner.nextLine(); // Clear invalid input
            }
        } while (true);
    }

    //check-in
    public void custCheckIn() {
        System.out.println("\nYour Pending Bookings for Check-in:");
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) {
            System.out.println("\n[No bookings found]\n");
            showPostCheckoutOptions(); // Show options to return to the main menu or exit
            return;
        }
    
        StringBuilder pendingBookings = new StringBuilder();
        int bookingCount = 0;
    
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|"); // Use "|" as the delimiter
                if (parts.length == 8) { // Ensure the line has exactly 8 parts
                    String bookingCustomerId = parts[1];
                    String roomType = parts[2];
                    String nights = parts[3];
                    String totalPrice = parts[4];
                    String status = parts[5];
    
                    if (bookingCustomerId.equals(loggedInCustomer.getCustomerId()) && status.equals("PENDING")) {
                        bookingCount++;
                        pendingBookings.append("[").append(bookingCount).append("] Room: ").append(roomType)
                                .append(", Nights: ").append(nights)
                                .append(", Total Price: RM ").append(totalPrice).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\n[Error reading bookings: " + e.getMessage() + "]\n");
            showPostCheckoutOptions(); // Show options to return to the main menu or exit
            return;
        }
    
        if (bookingCount == 0) {
            System.out.println("\n[No pending bookings available for check-in.]\n");
            showPostCheckoutOptions(); // Show options to return to the main menu or exit
            return;
        }
    
        System.out.println(pendingBookings.toString());
    
        int choice;
        if (bookingCount == 1) {
            // If there is only one booking, simplify the prompt
            System.out.print("Select a booking to check in ('0' to cancel): ");
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice == 0 || choice == 1) {
                        break;
                    } else {
                        System.out.println("\n[Invalid selection. Please choose '0' to cancel or 1 to check in.]\n");
                    }
                } else {
                    System.out.println("\n[Invalid input. Please enter a number.]\n");
                    scanner.nextLine();
                }
            }
        } else {
            // If there are multiple bookings, show the full prompt
            while (true) {
                System.out.print("Select a booking to check in (1 - " + bookingCount + ", or '0' to cancel): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= bookingCount) {
                        break;
                    } else {
                        System.out.println("\n[Invalid selection. Please choose a number between 1 and " + bookingCount + ", or '0' to cancel.]\n");
                    }
                } else {
                    System.out.println("\n[Invalid input. Please enter a number.]\n");
                    scanner.nextLine();
                }
            }
        }
    
        if (choice == 0) {
            System.out.println("\n[Check-in cancelled.]\n");
            showPostCheckoutOptions(); // Show options to return to the main menu or exit
            return;
        }
    
        // Update the selected booking to CHECKED_IN
        try {
            StringBuilder newFileContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE));
            String line;
            int currentIndex = 0;
            int targetIndex = choice - 1; // User's selection (1-based index)
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) { // Ensure the line has exactly 8 parts
                    String bookingCustomerId = parts[1];
                    String status = parts[5];
                    if (bookingCustomerId.equals(loggedInCustomer.getCustomerId()) && status.equals("PENDING")) {
                        if (currentIndex == targetIndex) {
                            parts[5] = "CHECKED_IN"; // Update status to CHECKED_IN
                            line = String.join("|", parts); // Reconstruct the line
                        }
                        currentIndex++;
                    }
                }
                newFileContent.append(line).append("\n");
            }
            br.close();
    
            BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE));
            bw.write(newFileContent.toString());
            bw.close();
    
            System.out.println("\n[Check-in successful!]\n");
        } catch (IOException e) {
            System.out.println("\n[Error updating booking status: " + e.getMessage() + "]\n");
            return; // Exit the method if an error occurs
        }
        showPostCheckoutOptions(); // Show options to return to the main menu or exit
    }

    //check-out
    public void custCheckOut() {
        if (loggedInCustomer == null) {
            System.out.println("\n[Please log in to check out.]\n");
            return;
        }
    
        System.out.println("Your Checked-in Bookings for Check-out:");
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) {
            System.out.println("\n[No bookings found]\n");
            showPostCheckoutOptions();
            return;
        }
    
        StringBuilder checkedInBookings = new StringBuilder();
        int bookingCount = 0;
    
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|"); // Use "|" as the delimiter
                if (parts.length == 6) { // Ensure the line has exactly 6 parts
                    String bookingCustomerId = parts[1];
                    String roomType = parts[2];
                    String nights = parts[3];
                    String totalPrice = parts[4];
                    String status = parts[5];
    
                    if (bookingCustomerId.equals(loggedInCustomer.getCustomerId()) && status.equals("CHECKED_IN")) {
                        bookingCount++;
                        checkedInBookings.append("[").append(bookingCount).append("] Room: ").append(roomType)
                                .append(", Nights: ").append(nights)
                                .append(", Total Price: RM ").append(totalPrice).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\n[Error reading bookings: " + e.getMessage() + "]\n");
            showPostCheckoutOptions();
            return;
        }
    
        if (bookingCount == 0) {
            System.out.println("\n[No checked-in bookings available for check-out.]");
            showPostCheckoutOptions();
            return;
        }
    
        System.out.println(checkedInBookings.toString());
        int choice;
        while (true) {
            System.out.print("Select a booking to check out ('0' to cancel): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 0 && choice <= bookingCount) {
                    break;
                } else {
                    System.out.println("\n[Invalid selection. Please choose a number between 1 and " + bookingCount + ", or '0' to cancel.]\n");
                }
            } else {
                System.out.println("\n[Invalid input. Please enter a number.]\n");
                scanner.nextLine();
            }
        }
    
        if (choice == 0) {
            System.out.println("\n[Check-out cancelled. Returning to Customer Main Menu.]\n");
            runCustMainMenu();
            return;
        }
    
        // Update the selected booking to CHECKED_OUT
        try {
            StringBuilder newFileContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE));
            String line;
            int currentIndex = 0;
            int targetIndex = choice - 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String bookingCustomerId = parts[1];
                    String status = parts[5];
                    if (bookingCustomerId.equals(loggedInCustomer.getCustomerId()) && status.equals("CHECKED_IN")) {
                        if (currentIndex == targetIndex) {
                            parts[5] = "CHECKED_OUT"; // Update status to CHECKED_OUT
                            line = String.join("|", parts);
                        }
                        currentIndex++;
                    }
                }
                newFileContent.append(line).append("\n");
            }
            br.close();
    
            BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE));
            bw.write(newFileContent.toString());
            bw.close();
    
            System.out.println("\n[Check-out successful!]\n");
        } catch (IOException e) {
            System.out.println("\n[Error updating booking status: " + e.getMessage() + "]\n");
        }
    
        showPostCheckoutOptions();
    }

    private void showPostCheckoutOptions() {
        int checkoutChoice;
        do {
            System.out.println("\nPlease choose an option:");
            System.out.println("[1] Return to Customer Main Menu");
            System.out.println("[0] Exit");
            System.out.print("Please choose an option: ");
            if (scanner.hasNextInt()) {
                checkoutChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                if (checkoutChoice == 1) {
                    System.out.println("\n[Returning to Main Menu...]\n");
                    runCustMainMenu();
                    return;
                } else if (checkoutChoice == 0) {
                    System.out.println("Goodbye! Thanks for using our system!");
                    System.exit(0);
                } else {
                    System.out.println("[Invalid option. Please enter 1 or 0.]");
                }
            } else {
                System.out.println("[Invalid input. Please enter a number.]");
                scanner.nextLine(); // Clear invalid input
            }
        } while (true);
    }

    //search customer is them in the customer file
    private Customer searchCustomer(String username) {
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) {
            System.out.println("[Error: Customer file does not exist. Creating a new file...]");
            try {
                file.createNewFile(); // Create the file if it does not exist
            } catch (IOException e) {
                System.out.println("[Error creating customer file: " + e.getMessage() + "]");
                return null;
            }
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|"); // Split using "|"
                if (parts.length >= 2) { // Ensure the array has at least 2 elements
                    String lineUsername = parts[1]; // Username is the second field
                    if (lineUsername.equals(username)) {
                        return new Customer(line); // Create a Customer object from the line
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\n[Error searching customer: " + e.getMessage() + "]\n");
        }
        return null; // Customer not found
    }

    public static void displayLoginRegisterMenu(){
        //System.out.println("===============================================");
        //System.out.println("||     WELCOME TO HOTEL MANAGEMENT SYSTEM     ||");
        System.out.println("===============================================");
        System.out.println("||               Customer Menu                ||");
        System.out.println("===============================================");
        System.out.println("|| [1] Register                               ||");
        System.out.println("|| [2] Login                                  ||");
        System.out.println("|| [0] Exit                                   ||");
        System.out.println("===============================================");

    }

    //customer main menu
    public static void displayCustMainMenu(){
        System.out.println("================================================");
        System.out.println("||             Customer Main Menu             ||");
        System.out.println("================================================");
        System.out.println("|| [1] View Profile                           || ");
        System.out.println("|| [2] View Room Type                         || ");
        System.out.println("|| [3] View Room Availability                 || ");
        System.out.println("|| [4] Book a room                            || ");
        System.out.println("|| [5] View my booking                        || ");
        System.out.println("|| [6] Check-in                               || ");
        System.out.println("|| [7] Check-out                              || ");
        System.out.println("|| [8] Check Transaction History              || ");
        System.out.println("|| [0] Exit                                   || ");
        System.out.println("================================================");
    }

    public static void displayRoomType(){
        System.out.println("┌──────────────────────┬────────────┬────────────────────────────────────────────────────────────────────────────────────┬─────────────────┐");
        System.out.printf("| %-20s | %-10s | %-82s | %-15s |\n", "Room Type", "Price", "Description", "Capacity");
        System.out.println("├──────────────────────┼────────────┼────────────────────────────────────────────────────────────────────────────────────┼─────────────────┤");
        System.out.printf("| %-20s | RM %-7d | %-82s | %-15s |\n", "Standard Room", standardRoomPrice, "A simple room with essential amenities for solo travelers or couples.", "1-2 pax");
        System.out.println("├──────────────────────┼────────────┼────────────────────────────────────────────────────────────────────────────────────┼─────────────────┤");
        System.out.printf("| %-20s | RM %-7d | %-82s | %-15s |\n", "Deluxe Room", deluxeRoomPrice, "A more spacious and upgraded room with additional amenities.", "2-3 pax");
        System.out.println("├──────────────────────┼────────────┼────────────────────────────────────────────────────────────────────────────────────┼─────────────────┤");
        System.out.printf("| %-20s | RM %-7d | %-82s | %-15s |\n", "Family Room", familyRoomPrice, "A larger room with multiple beds, ideal for families or small groups.", "3-5 pax");
        System.out.println("├──────────────────────┼────────────┼────────────────────────────────────────────────────────────────────────────────────┼─────────────────┤");
        System.out.printf("| %-20s | RM %-7d | %-82s | %-15s |\n", "Suite", suitePrice, "A luxurious room with separate living and sleeping areas.", "2-4 pax");
        System.out.println("├──────────────────────┼────────────┼────────────────────────────────────────────────────────────────────────────────────┼─────────────────┤");
        System.out.printf("| %-20s | RM %-7d | %-82s | %-15s |\n", "Executive Room", executiveRoomPrice, "Designed for business travelers, featuring a workspace and premium amenities.", "1-2 pax");
        System.out.println("└──────────────────────┴────────────┴────────────────────────────────────────────────────────────────────────────────────┴─────────────────┘");
    }

    public static void displayRoomAvailable(){
        System.out.println("\n=========== Room Availability ===========");
        System.out.printf("| %-20s | %-18s |\n", "Room Type", "Available Rooms");
        System.out.println("┌──────────────────────┼─────────────────────┐");
        System.out.printf("| %-20s | %-18d |\n", "Standard Room", standardRoomAvailability);
        System.out.println("├──────────────────────┼─────────────────────┤");
        System.out.printf("| %-20s | %-18d |\n", "Deluxe Room", deluxeRoomAvailability);
        System.out.println("├──────────────────────┼─────────────────────┤");
        System.out.printf("| %-20s | %-18d |\n", "Family Room", familyRoomAvailability);
        System.out.println("├──────────────────────┼─────────────────────┤");
        System.out.printf("| %-20s | %-18d |\n", "Suite", suiteAvailability);
        System.out.println("├──────────────────────┼─────────────────────┤");
        System.out.printf("| %-20s | %-18d |\n", "Executive Room", executiveRoomAvailability);
        System.out.println("└──────────────────────┴─────────────────────┘");
    }

    //modify profile
    public void displayModifyProfileMenu(){
        System.out.println("\n================================================");
        System.out.println("||              Modify Profile                ||");
        System.out.println("================================================");
        System.out.println("|| [1] Username                               ||");
        System.out.println("|| [2] Full Name                              ||");
        System.out.println("|| [3] Password                               ||");
        System.out.println("|| [4] Phone Number                           ||");
        System.out.println("|| [5] Email                                  ||");
        System.out.println("|| [0] Cancel                                 ||");
        System.out.println("================================================");
    }

    //payment selection
    public void displayPaymentMethod(){
        System.out.println("\n==============================================");
        System.out.println("||              Payment Method                ||");
        System.out.println("================================================");
        System.out.println("|| [1] TNG eWallet                            ||");
        System.out.println("|| [2] Credit Card                            ||");
        System.out.println("|| [3] Debit Card                             ||");
        System.out.println("|| [0] Cancel                                 ||");
        System.out.println("================================================");
        System.out.println("[Note: If wanna use cash to pay do go to counter]");
    }
    
    public void runPaymentMethod(int totalPrice) {
        displayPaymentMethod();
        int choice;
        Payment payment = null;
    
        do {
            System.out.print("Please choose a payment method: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                if (choice >= 0 && choice <= 4) {
                    break;
                } else {
                    System.out.println("[Invalid option. Please enter a valid number within 0-4!]");
                }
            } else {
                System.out.println("[Invalid input. Please enter a number.]");
                scanner.nextLine(); // Clear the invalid input
            }
        } while (true);
    
        switch (choice) {
            case 1: // TNG eWallet
                payment = new TNGPayment(totalPrice);
                break;
            case 2: // Credit Card
                System.out.print("Enter Credit Card Number: ");
                String creditCardNumber = scanner.nextLine();
                while (!creditCardNumber.matches("\\d{16}")) { // Validate card number (16 digits)
                    System.out.println("[Invalid card number. Please enter a valid 16-digit card number.]");
                    System.out.print("Enter Credit Card Number: ");
                    creditCardNumber = scanner.nextLine();
                }
    
                System.out.print("Enter Card Expiry Date (MM/YY): ");
                String creditCardExpiry = scanner.nextLine();
                while (!creditCardExpiry.matches("(0[1-9]|1[0-2])/(\\d{2})")) { // Validate expiry date format
                    System.out.println("[Invalid expiry date. Please enter in MM/YY format.]");
                    System.out.print("Enter Card Expiry Date (MM/YY): ");
                    creditCardExpiry = scanner.nextLine();
                }
    
                System.out.print("Enter Cardholder Name: ");
                String creditCardHolderName = scanner.nextLine();
    
                System.out.print("Enter CVV (3 digits): ");
                String creditCardCVV = scanner.nextLine();
                while (!creditCardCVV.matches("\\d{3}")) { // Validate CVV (3 digits)
                    System.out.println("[Invalid CVV. Please enter a valid 3-digit CVV.]");
                    System.out.print("Enter CVV (3 digits): ");
                    creditCardCVV = scanner.nextLine();
                }
    
                payment = new CreditCardPayment(totalPrice, creditCardNumber, creditCardExpiry, creditCardHolderName, creditCardCVV);
                break;
            case 3: // Debit Card
                System.out.print("Enter Debit Card Number: ");
                String debitCardNumber = scanner.nextLine();
                while (!debitCardNumber.matches("\\d{16}")) { // Validate card number (16 digits)
                    System.out.println("[Invalid card number. Please enter a valid 16-digit card number.]");
                    System.out.print("Enter Debit Card Number: ");
                    debitCardNumber = scanner.nextLine();
                }
    
                System.out.print("Enter Card Expiry Date (MM/YY): ");
                String debitCardExpiry = scanner.nextLine();
                while (!debitCardExpiry.matches("(0[1-9]|1[0-2])/(\\d{2})")) { // Validate expiry date format
                    System.out.println("[Invalid expiry date. Please enter in MM/YY format.]");
                    System.out.print("Enter Card Expiry Date (MM/YY): ");
                    debitCardExpiry = scanner.nextLine();
                }
    
                System.out.print("Enter Cardholder Name: ");
                String debitCardHolderName = scanner.nextLine();
    
                System.out.print("Enter CVV (3 digits): ");
                String debitCardCVV = scanner.nextLine();
                while (!debitCardCVV.matches("\\d{3}")) { // Validate CVV (3 digits)
                    System.out.println("[Invalid CVV. Please enter a valid 3-digit CVV.]");
                    System.out.print("Enter CVV (3 digits): ");
                    debitCardCVV = scanner.nextLine();
                }
    
                payment = new DebitCardPayment(totalPrice, debitCardNumber, debitCardExpiry, debitCardHolderName, debitCardCVV);
                break;
            case 0:
                System.out.println("Payment cancelled. Returning to Customer Main Menu.");
                return;
            default:
                System.out.println("[Invalid choice. Please try again.]");
                return;
        }
    
        // Process the payment
        if (payment != null) {
            payment.processPayment();
            payment.displayPaymentDetails();
    
            // Generate a unique transaction ID
            String transactionId = generateTransactionId();
    
            // Save the transaction details to a file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.txt", true))) {
                String transactionDetails = transactionId + "|" + loggedInCustomer.getCustomerId() + "|" + payment.getPaymentMethod() + "|" + totalPrice + "|" + new Date();
                bw.write(transactionDetails);
                bw.newLine();
                System.out.println("\n[Payment successful! Transaction ID: " + transactionId + "]");
            } catch (IOException e) {
                System.out.println("[Error saving transaction details: " + e.getMessage() + "]");
            }
    
            // Ask user to return to main menu or exit
            showPostCheckoutOptions();
        }
    }

    private String generateTransactionId() {
        File file = new File("payments.txt");
        int id = 1; // Start with ID 1 if the file is empty
    
        // Create the file if it does not exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("[Error creating payments file: " + e.getMessage() + "]");
                return "TXN" + id; // Return default ID if file creation fails
            }
        }
    
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0 && parts[0].startsWith("TXN")) {
                        try {
                            int currentId = Integer.parseInt(parts[0].substring(3)); // Extract numeric part of ID
                            if (currentId >= id) {
                                id = currentId + 1; // Increment ID
                            }
                        } catch (NumberFormatException e) {
                            // Ignore invalid IDs
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("[Error reading payments file: " + e.getMessage() + "]");
            }
        }
    
        return "TXN" + id; // Prefix the ID with "TXN"
    }
}
