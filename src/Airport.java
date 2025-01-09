import Classes.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * De Airport-klasse beheert passagiers, vluchten en personeel.
 * Het biedt functionaliteiten voor het maken van passagiers, vluchten, tickets,
 * en het toewijzen van personeel aan vluchten.
 */
public class Airport {

    private ArrayList<Passenger> passengers;
    private ArrayList<Flight> flights;
    private ArrayList<Staff> staff;

    /**
     * Constructor om een lege luchthaven te initialiseren.
     */
    public Airport() {
        this.passengers = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    /**
     * Maakt een nieuwe passagier aan en voegt deze toe aan de lijst van passagiers.
     */
    public void createPassenger() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Voer de naam van de passagier in: ");
        String name = sc.nextLine();
        if (name.isEmpty()) {
            System.out.println("Naam mag niet leeg zijn!");
            return;
        }

        System.out.print("Voer de leeftijd van de passagier in: ");
        String ageInput = sc.nextLine();
        if (ageInput.isEmpty()) {
            System.out.println("Leeftijd mag niet leeg zijn!");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageInput);
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige leeftijd! Voer een geldig getal in.");
            return;
        }

        System.out.print("Voer het adres van de passagier in: ");
        String address = sc.nextLine();
        if (address.isEmpty()) {
            System.out.println("Adres mag niet leeg zijn!");
            return;
        }

        System.out.print("Voer het bagagegewicht in (kg): ");
        String baggageInput = sc.nextLine();
        if (baggageInput.isEmpty()) {
            System.out.println("Bagagegewicht mag niet leeg zijn!");
            return;
        }

        double baggageWeight;
        try {
            baggageWeight = Double.parseDouble(baggageInput);
        } catch (NumberFormatException e) {
            System.out.println("Ongeldig bagagegewicht! Voer een geldig getal in.");
            return;
        }

        if (baggageWeight > 35) {
            System.out.println("Bagagegewicht overschrijdt de toegestane limiet van 35kg. Passagier niet aangemaakt.");
            return;
        }

        Passenger passenger = new Passenger(name, age, address);
        passenger.setBaggageWeight(baggageWeight);
        passengers.add(passenger);

        System.out.println("Passagier aangemaakt met bagagegewicht: " + baggageWeight + "kg.");
    }

    /**
     * Maakt een nieuwe vlucht aan en voegt deze toe aan de lijst van vluchten.
     */
    public void createFlight() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Voer de vluchtcode in: ");
        String flightCode = sc.nextLine();

        for (Flight f : flights) {
            if (f.flightCode.equals(flightCode)) {
                System.out.println("Vluchtcode bestaat al!");
                return;
            }
        }

        System.out.print("Voer de bestemming in: ");
        String destination = sc.nextLine();
        if (destination.isEmpty()) {
            System.out.println("Bestemming mag niet leeg zijn!");
            return;
        }

        System.out.print("Voer het aantal economy seats in: ");
        int economySeats = sc.nextInt();
        System.out.print("Voer het aantal business seats in: ");
        int businessSeats = sc.nextInt();

        Flight flight = new Flight(flightCode, destination, economySeats, businessSeats);
        flights.add(flight);
        System.out.println("Vlucht aangemaakt.");
    }

    /**
     * Wijs een passagier toe aan een vlucht.
     */
    public void boardPassenger() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Voer de naam van de passagier in: ");
        String passengerName = sc.nextLine();
        System.out.print("Voer de vluchtcode in: ");
        String flightCode = sc.nextLine();

        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);
        Passenger passenger = passengers.stream().filter(p -> p.name.equals(passengerName)).findFirst().orElse(null);

        if (flight != null && passenger != null && passenger.getTicket() != null) {
            flight.addPassenger(passenger);
        } else {
            System.out.println("Instappen mislukt. Ongeldig ticket of vlucht.");
        }
    }

    /**
     * Wijs een personeelslid toe aan een vlucht.
     */
    public void assignStaff() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Voer de naam van het personeel in: ");
        String staffName = sc.nextLine();
        System.out.print("Voer de vluchtcode in: ");
        String flightCode = sc.nextLine();

        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);

        Staff staffMember = staff.stream().filter(s -> s.name.equals(staffName)).findFirst().orElse(null);

        if (flight != null && staffMember != null) {
            flight.addStaff(staffMember);
        } else {
            System.out.println("Toewijzen van personeel mislukt.");
        }
    }

    /**
     * Print informatie over een specifieke vlucht naar een bestand.
     */
    public void printVluchtInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Voer de vluchtcode in: ");
        String flightCode = sc.nextLine();

        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);
        if (flight != null) {
            try {

                File file = new File("VluchtInfo.txt");

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("Vluchtcode: " + flight.flightCode + "\n");
                bw.write("Bestemming: " + flight.destination + "\n");
                bw.write("Economy Seats: " + flight.economySeats + "\n");
                bw.write("Business Seats: " + flight.businessSeats + "\n\n");

                bw.write("Passagiers:\n");
                if (flight.passengers.isEmpty()) {
                    bw.write("Geen passagiers toegewezen aan deze vlucht.\n");
                } else {
                    for (Passenger p : flight.passengers) {
                        bw.write("- " + p.name + " (Bagage: " + p.getBaggageWeight() + "kg)\n");
                    }
                }
                bw.write("\n");

                bw.write("Personeel:\n");
                if (flight.staff.isEmpty()) {
                    bw.write("Geen personeel toegewezen aan deze vlucht.\n");
                } else {
                    for (Staff s : flight.staff) {
                        bw.write("- " + s.name + " (" + s.role + ")\n");
                    }
                }

                bw.close();

                System.out.println("Vluchtinformatie geschreven naar VluchtInfo.txt");

                openNotepad(file);
            } catch (IOException e) {
                System.out.println("Er is een fout opgetreden tijdens het schrijven naar het bestand.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Vlucht niet gevonden.");
        }
    }

    /**
     * Open een bestand in Notepad.
     * @param file Het bestand dat moet worden geopend.
     */
    private void openNotepad(File file) {
        try {
            ProcessBuilder pb = new ProcessBuilder("notepad.exe", file.getAbsolutePath());
            pb.start();
        } catch (IOException e) {
            System.out.println("Er is een fout opgetreden bij het openen van Notepad.");
            e.printStackTrace();
        }
    }

    /**
     * Creëer een nieuw personeelslid en voeg deze toe aan de lijst.
     */
    public void createStaff() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Voer de naam van het personeel in: ");
        String name = sc.nextLine();
        System.out.print("Voer de leeftijd van het personeel in: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Voer het adres van het personeel in: ");
        String address = sc.nextLine();
        System.out.print("Voer de rol van het personeel in (Piloot/Personeel): ");
        String role = sc.nextLine();

        Staff newStaff;
        if (role.equalsIgnoreCase("Piloot")) {
            newStaff = new Pilot(name, age, address);
        } else {
            newStaff = new Staff(name, age, address, role);
        }

        staff.add(newStaff);
        System.out.println("Personeelslid '" + name + "' toegevoegd als " + role + ".");
    }

    /**
     * Creëer een ticket voor een passagier op een specifieke vlucht.
     */
    public void createTicket() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Voer de naam van de passagier in: ");
        String passengerName = sc.nextLine();

        Passenger passenger = passengers.stream().filter(p -> p.name.equals(passengerName)).findFirst().orElse(null);
        if (passenger == null) {
            System.out.println("Passagier niet gevonden.");
            return;
        }

        System.out.print("Voer de vluchtcode in: ");
        String flightCode = sc.nextLine();

        Flight flight = flights.stream().filter(f -> f.flightCode.equals(flightCode)).findFirst().orElse(null);
        if (flight == null) {
            System.out.println("Vlucht niet gevonden.");
            return;
        }

        System.out.print("Voer de klasse in (economy/business): ");
        String flightClass = sc.nextLine().toLowerCase();
        if (!flightClass.equals("economy") && !flightClass.equals("business")) {
            System.out.println("Ongeldige klasse.");
            return;
        }

        Ticket ticket = new Ticket(passenger, flightClass, flight);
        passenger.setTicket(ticket);

        System.out.println("Ticket aangemaakt voor " + passenger.name + " op vlucht " + flightCode + " in " + flightClass + " klasse.");
    }

    /**
     * Start de hoofdroutine van de applicatie.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Maak Passagier");
            System.out.println("2. Maak Vlucht");
            System.out.println("3. Maak Ticket");
            System.out.println("4. Maak Personeel");
            System.out.println("5. Wijs Personeel toe aan Vlucht");
            System.out.println("6. Boarden");
            System.out.println("7. Print Vluchtinformatie");
            System.out.println("8. Exit");
            System.out.print("Kies een optie: ");
            int choice = sc.nextInt();
            sc.nextLine();

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
                    createStaff();
                    break;
                case 5:
                    assignStaff();
                    break;
                case 6:
                    boardPassenger();
                    break;
                case 7:
                    printVluchtInfo();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Ongeldige keuze.");
            }
        }
    }

    /**
     * Het startpunt van de applicatie.
     * @param args Command line argumenten.
     */
    public static void main(String[] args) {
        Airport airport = new Airport();
        airport.run();
    }
}
