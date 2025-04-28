package CustomerManagement;

import java.io.*;
import java.util.*;

public class RoomAvailability {
    ArrayList<String[]> rooms = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    
    public void readRoomFile() {
        rooms.clear(); // Clear the list to avoid duplicate entries
        try (BufferedReader reader = new BufferedReader(new FileReader("Room.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by the delimiter '|'
                String[] roomData = line.split("\\|");
    
                // Ensure that we have the expected number of columns (6 fields)
                if (roomData.length == 6) {
                    rooms.add(roomData); // Add room data to the list
                } else {
                    System.out.println("Invalid room data line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayRoomAvailabilityCust() {
        readRoomFile(); // Load the room data

        // Initialize counters for each room type
        int standardCount = 0;
        int deluxeCount = 0;
        int familyCount = 0;
        int suiteCount = 0;
        int executiveCount = 0;

        // Loop through the rooms and count available rooms by type
        for (String[] room : rooms) {
            try {
                String roomType = room[2];
                String status = room[5];

                if ("Available".equalsIgnoreCase(status)) {
                    switch (roomType) {
                        case "Standard Room":
                            standardCount++;
                            break;
                        case "Deluxe Room":
                            deluxeCount++;
                            break;
                        case "Family Room":
                            familyCount++;
                            break;
                        case "Suite":
                            suiteCount++;
                            break;
                        case "Executive Room":
                            executiveCount++;
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing room data: " + String.join("|", room));
                e.printStackTrace();
            }
        }

        // Display the room availability
        System.out.println("\n=================================================");
        System.out.println("||               ROOM AVAILABILITY             ||");
        System.out.println("=================================================");
        System.out.printf("|| %-20s || %-18s ||\n", "Room Type", "Available Rooms");
        System.out.println("=================================================");
        System.out.printf("|| %-20s || %-18d ||\n", "Standard Room", standardCount);
        System.out.println("-------------------------------------------------");
        System.out.printf("|| %-20s || %-18d ||\n", "Deluxe Room", deluxeCount);
        System.out.println("-------------------------------------------------");
        System.out.printf("|| %-20s || %-18d ||\n", "Family Room", familyCount);
        System.out.println("-------------------------------------------------");
        System.out.printf("|| %-20s || %-18d ||\n", "Suite", suiteCount);
        System.out.println("-------------------------------------------------");
        System.out.printf("|| %-20s || %-18d ||\n", "Executive Room", executiveCount);
        System.out.println("=================================================");
    }

    public void displayAvailabilityStaff(){
        readRoomFile();
                    // Loop through each room type and print available rooms
                    boolean foundStandard = false;
                    boolean foundDeluxe = false;
                    boolean foundFamily = false;
                    boolean foundSuite = false;
                    boolean foundExecutive = false;
        
                    System.out.println("-----------------------------");       
                    System.out.println("|      AVAILABLE ROOM       |");  
                    System.out.println("-----------------------------");
                    for (String[] room : rooms) {
                        try {
                            String roomType = room[2];
                            String status = room[5];
        
                            if ("Available".equals(status)) {
                                // Print room numbers for each room type
                                if ("Standard Room".equals(roomType) && !foundStandard) {
                                    System.out.println("===Standard Room===");
                                    foundStandard = true;
                                }
                                if ("Deluxe Room".equals(roomType) && !foundDeluxe) {
                                    System.out.println("\n===Deluxe Room===");
                                    foundDeluxe = true;
                                }
                                if ("Family Room".equals(roomType) && !foundFamily) {
                                    System.out.println("\n===Family Room===");
                                    foundFamily = true;
                                }
                                if ("Suite".equals(roomType) && !foundSuite) {
                                    System.out.println("\n===Suite===");
                                    foundSuite = true;
                                }
                                if ("Executive Room".equals(roomType) && !foundExecutive) {
                                    System.out.println("\n===Executive Room===");
                                    foundExecutive = true;
                                }
        
                                // Print the room number only if it matches the room type and availability
                                System.out.println(room[0]);
                            }
                        } catch (Exception e) {
                            System.out.println("Error processing room data: " + String.join("|", room));
                            e.printStackTrace();
                        }
                    }
    }

    public void writeRoomFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Room.txt"))){
            for (String[] room : rooms){
                String newLine = String.join("|",room);
                writer.write(newLine);
                writer.newLine();
            }

            System.out.println("Room status updated successfully!");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void modifyAvailability(){
        readRoomFile();

        System.out.println("-----------------------------");       
        System.out.println("|     MODIFY ROOM STATUS     |");  
        System.out.println("-----------------------------");

        boolean continueLooping=true;  
        while(continueLooping){    
                System.out.print("Enter Room Number: ");
                String inputRoomNumber = sc.nextLine();

                for (String[] room : rooms){
                    
                
                    if(room[0].equals(inputRoomNumber)){
                        System.out.println("Current Status: "+room[5]);

                        System.out.print("Change status to? (Available/Occupied): ");
                        String answer = sc.nextLine().trim();
                        if("Available".equals(answer) || "Occupied".equals(answer)){
                            room[5]=answer;
                            System.out.println("Successfully update!");
                            System.out.println("Current Status: "+room[5]);

                            System.out.println("\n\nPress 0 to EXIT, other number to CONTINUE");
                            int continueAnswer=sc.nextInt();
                            sc.nextLine();
                            if(continueAnswer==0){
                                continueLooping=false;                                
                            }

                        }
                        else{
                            System.out.println("ERROR: Please try again.");
                        }
                    }
            }
        }
        writeRoomFile();
    }
}
