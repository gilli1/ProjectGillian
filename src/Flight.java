import java.util.ArrayList;

public class Flight {
    public String flightCode;
    String destination;
    int economySeats;
    int businessSeats;
    ArrayList<Passenger> passengers;
    private ArrayList<Staff> staff;


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
            System.out.println(passenger.name + " added to flight " + flightCode);
        } else {
            System.out.println("No available seats.");
        }
    }

    public void addStaff(Staff staffMember) {
        if (staff.contains(staffMember)) {
            System.out.println(staffMember.name + " is already assigned to flight " + flightCode);
        } else {
            staff.add(staffMember);
            System.out.println(staffMember.name + " added to flight " + flightCode);
        }
    }

    public boolean isReadyToDepart() {
        if (staff.size() >= 1 && staff.stream().anyMatch(s -> s instanceof Pilot)) {
            return true;
        }
        return false;
    }

    public void printFlightInfo() {
        System.out.println("Flight Code: " + flightCode);
        System.out.println("Destination: " + destination);
        System.out.println("Economy Seats: " + economySeats);
        System.out.println("Business Seats: " + businessSeats);
        System.out.println("Passengers:");
        for (Passenger p : passengers) {
            System.out.println("- " + p.name);


        }
    }
}
