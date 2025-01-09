public class Passenger extends Person {
    private Ticket ticket;
    private double baggageWeight;

    public Passenger(String name, int age, String address) {
        super(name, age, address);
    }


    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setBaggageWeight(double weight) {
        this.baggageWeight = weight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public double getBaggageWeight() {
        return baggageWeight;
    }
}
