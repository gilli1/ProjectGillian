public class Staff extends Person {
    protected String role;



    public Staff(String name, int age, String address, String role) {
        super(name, age, address);
        this.role = role;
    }
}

