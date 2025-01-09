package Classes;

import java.util.ArrayList;

public class Flight {
    public String flightCode;
    String destination;
    int economySeats;
    int businessSeats;
    ArrayList<Passenger> passengers;
    ArrayList<Staff> staff;


    public Flight(String flightCode, String destination, int economySeats, int businessSeats) {
        this.flightCode = flightCode;
        this.destination = destination;
        this.economySeats = economySeats;
        this.businessSeats = businessSeats;
        this.passengers = new ArrayList<>();
        this.staff = new ArrayList<>();
    }


    public void addPassenger(Passenger passenger) {
        if (economySeats + businessSeats > passengers.size()) {
            passengers.add(passenger);
            System.out.println(passenger.name + " Is toegevoed aan " + flightCode);
        } else {
            System.out.println("Er zijn geen stoelen beschikbaar");
        }
    }

    public void addStaff(Staff staffMember) {
        if (staff.contains(staffMember)) {
            System.out.println(staffMember.name + " Is al bij de vlucht " + flightCode);
        } else {
            staff.add(staffMember);
            System.out.println(staffMember.name + " Is toegevoegd aan " + flightCode);
        }
    }

    public boolean isReadyToDepart() {
        if (staff.size() >= 1 && staff.stream().anyMatch(s -> s instanceof Pilot)) {
            return true;
        }
        return false;
    }

    public void printFlightInfo() {
        System.out.println("Vlucht Code: " + flightCode);
        System.out.println("Bestemming: " + destination);
        System.out.println("Economy Stoelen: " + economySeats);
        System.out.println("Business Stoelen: " + businessSeats);
        System.out.println("Passagiers:");
        for (Passenger p : passengers) {
            System.out.println("- " + p.name);


        }
    }
}
