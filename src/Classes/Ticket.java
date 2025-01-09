package Classes;

public class Ticket{
    private Passenger passenger;
    private String flightClass;
    private Flight flight;

    public Ticket(Passenger passenger, String flightClass, Flight flight) {
        this.passenger = passenger;
        this.flightClass = flightClass;
        this.flight = flight;
    }



    public Flight getFlight() {
        return flight;
    }


    public String getFlightClass() {
        return flightClass;
    }
}
