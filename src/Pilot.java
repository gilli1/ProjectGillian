public class Pilot extends Staff {
    public Pilot(String name, int age, String address) {
        super(name, age, address, "Pilot");
    }


    public void performFlightCheck() {
        System.out.println(name + " is performing a flight check.");
    }
}
