import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Airport {
    private ArrayList<Passenger> passengers;
    private ArrayList<Flight> flights;
    private ArrayList<Staff> staff;

    public Airport() {
        this.passengers = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    public void createPassenger() {
        Scanner sc = new Scanner(System.in);

        // Vraag de naam van de passagier
        System.out.print("Enter passenger name: ");
        String name = sc.nextLine();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return; // Stop de methode als de naam leeg is
        }

        // Vraag de leeftijd van de passagier
        System.out.print("Enter passenger age: ");
        String ageInput = sc.nextLine();
        if (ageInput.isEmpty()) {
            System.out.println("Age cannot be empty!");
            return; // Stop de methode als de leeftijd leeg is
        }

        int age;
        try {
            age = Integer.parseInt(ageInput); // Probeer de leeftijd om te zetten naar een int
        } catch (NumberFormatException e) {
            System.out.println("Invalid age! Please enter a valid number.");
            return; // Stop de methode als de leeftijd geen geldig getal is
        }

        // Vraag het adres van de passagier
        System.out.print("Enter passenger address: ");
        String address = sc.nextLine();
        if (address.isEmpty()) {
            System.out.println("Address cannot be empty!");
            return; // Stop de methode als het adres leeg is
        }

        // Als alles geldig is, maak een nieuwe passagier aan
        Passenger passenger = new Passenger(name, age, address);
        passengers.add(passenger);
        System.out.println("Passenger created.");
    }


    public void createFlight() {
        Scanner sc = new Scanner(System.in);

        // Vraag de vluchtcode
        System.out.print("Enter flight code: ");
        String flightCode = sc.nextLine();

        // Controleer of de vluchtcode al bestaat
        for (Flight f : flights) {
            if (f.flightCode.equals(flightCode)) {
                System.out.println("Flight code already exists!");
                return; // Stop de methode als de vluchtcode al bestaat
            }
        }

        // Vraag de bestemming
        System.out.print("Enter destination: ");
        String destination = sc.nextLine();
        if (destination.isEmpty()) {
            System.out.println("Destination cannot be empty!");
            return; // Stop de methode als de bestemming leeg is
        }

        // Vraag het aantal economy en business seats
        System.out.print("Enter number of economy seats: ");
        int economySeats = sc.nextInt();
        System.out.print("Enter number of business seats: ");
        int businessSeats = sc.nextInt();

        // Maak een nieuwe vlucht en voeg deze toe
        Flight flight = new Flight(flightCode, destination, economySeats, businessSeats);
        flights.add(flight);
        System.out.println("Flight created.");
    }



    public void boardPassenger() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter passenger name: ");
        String passengerName = sc.nextLine();
        System.out.print("Enter flight code: ");
        String flightCode = sc.nextLine();

        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);
        Passenger passenger = passengers.stream().filter(p -> p.name.equals(passengerName)).findFirst().orElse(null);

        if (flight != null && passenger != null && passenger.getTicket() != null) {
            flight.addPassenger(passenger);
        } else {
            System.out.println("Boarding failed. Invalid ticket or flight.");
        }
    }

    public void assignStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter staff name: ");
        String staffName = sc.nextLine();
        System.out.print("Enter flight code: ");
        String flightCode = sc.nextLine();

        // Zoek de vlucht op basis van de vluchtcode
        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);

        // Zoek het personeel op basis van de naam
        Staff staffMember = staff.stream().filter(s -> s.name.equals(staffName)).findFirst().orElse(null);

        // Als we de vlucht en het personeel niet kunnen vinden, geven we een foutmelding
        if (flight != null && staffMember != null) {
            flight.addStaff(staffMember);
        } else {
            System.out.println("Staff assignment failed.");
        }
    }


    public void printFlightInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter flight code: ");
        String flightCode = sc.nextLine();

        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);
        if (flight != null) {
            try {
                // Bestand naar de huidige map (vervang dit pad naar wens)
                File file = new File("flightInfo.txt");

                // Schrijf naar het bestand
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                // Schrijf vluchtinformatie naar bestand
                bw.write("Flight Code: " + flight.flightCode + "\n");
                bw.write("Destination: " + flight.destination + "\n");
                bw.write("Economy Seats: " + flight.economySeats + "\n");
                bw.write("Business Seats: " + flight.businessSeats + "\n");
                bw.write("Passengers:\n");

                for (Passenger p : flight.passengers) {
                    bw.write("- " + p.name + "\n");
                }

                bw.close(); // Sluit BufferedWriter
                System.out.println("Flight information written to flightInfo.txt");

                // Open het bestand in Notepad
                openNotepad(file);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Flight not found.");
        }
    }

    private void openNotepad(File file) {
    }

    public void createTicket() {
        Scanner sc = new Scanner(System.in);

        // Vraag de naam van de passagier
        System.out.print("Enter passenger name: ");
        String passengerName = sc.nextLine();

        // Zoek de passagier op
        Passenger passenger = passengers.stream().filter(p -> p.name.equals(passengerName)).findFirst().orElse(null);
        if (passenger == null) {
            System.out.println("Passenger not found.");
            return;
        }

        // Vraag de vluchtcode
        System.out.print("Enter flight code: ");
        String flightCode = sc.nextLine();

        // Zoek de vlucht op
        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);
        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        // Vraag de klasse (economy of business)
        System.out.print("Enter class (economy/business): ");
        String flightClass = sc.nextLine().toLowerCase();
        if (!flightClass.equals("economy") && !flightClass.equals("business")) {
            System.out.println("Invalid class.");
            return;
        }

        // Maak een nieuw ticket en koppel het aan de passagier en vlucht
        Ticket ticket = new Ticket(passenger, flightClass, flight);
        passenger.setTicket(ticket);

        System.out.println("Ticket created for " + passenger.name + " on flight " + flightCode + " in " + flightClass + " class.");
    }


    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Create Passenger");
            System.out.println("2. Create Flight");
            System.out.println("3. Create Ticket");
            System.out.println("4. Boarding");
            System.out.println("5. Assign Staff");
            System.out.println("6. Print Flight Info");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    createPassenger();
                    break;
                case 2:
                    createFlight();
                    break;
                case 3:
                    createTicket();
                    break;
                case 4:
                    boardPassenger();
                    break;
                case 5:
                    assignStaff();
                    break;
                case 6:
                    printFlightInfo();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) {
        Airport airport = new Airport();
        airport.run();
    }
}
