package CustomerManagement;

import java.util.Scanner;

public class HotelMainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        displayHotelMenu(); // Display the menu
        CustomerController customerController = new CustomerController();

        do {
            System.out.print("Please choose an option: ");
            if (sc.hasNextInt()) { // Validate if the input is an integer
                choice = sc.nextInt(); // Read user input
                sc.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        CustomerController.clearScreen();
                        System.out.println("\n[Customer Login selected]\n");
                        customerController.runCustMenu();
                        break;
                    case 2:
                        CustomerController.clearScreen();
                        System.out.println("\n[Staff Login selected]\n");
                        // Call the staff login method here
                        break;
                    case 55:
                        CustomerController.clearScreen();
                        displayHotelMenu();
                        break;
                    case 0:
                        System.out.println("Goodbye! Thanks for using our system!");
                        sc.close(); // Close the scanner before exiting
                        System.exit(0); // Exit the program
                        break;
                    default:
                        System.out.println("\n[Invalid choice. Please enter your option again] \n[Note: Enter '55' for display menu again.]\n");
                        break;
                }
            } else {
                System.out.println("\n[Invalid input. Please enter a valid number]\n");
                sc.nextLine(); // Clear the invalid input
            }
        } while (true); // Loop until the user exits
    }

    public static void displayHotelMenu() {
        System.out.println(
            "___  ___ _____ _____  _    _  ___  _____   ___ _____ _____ _____ _   _  \n" +
            "|  \\/  ||  ___|  _  || |  | |/ _ \\/  __ \\/ _ \\_   _|_   _|  _  | \\ | | \n" +
            "| .  . || |__ | | | || |  | / /_\\ \\ /  \\/ /_\\ \\| |   | | | | | |  \\| | \n" +
            "| |\\/| ||  __|| | | || |/\\| |  _  | |    |  _  | |   | | | | | | . ` | \n" +
            "| |  | || |___\\ \\_/ /\\  /\\  / | | | \\__/\\| | | | |  _| |_\\ \\_/ / |\\  | \n" +
            "\\_|  |_\\/____/ \\___/  \\/  \\/\\_| |_\\/____/\\_| |_\\/_/ \\___/ \\___/\\_| \\_/ \n");
    
        System.out.println("================================================");
        System.out.println("||        WELCOME TO MEOWACATION HOTEL        ||");
        System.out.println("================================================");
        System.out.println("||                 MAIN MENU                  ||");
        System.out.println("================================================");
        System.out.println("|| [1] Customer Login                         ||");
        System.out.println("|| [2] Staff Login                            ||");
        System.out.println("|| [0] Exit                                   ||");
        System.out.println("================================================");
    }
    
    
    
}
