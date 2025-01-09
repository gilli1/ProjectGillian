package Classes;

public class Staff extends Person {
    public String role;



    public Staff(String name, int age, String address, String role) {
        super(name, age, address);
        this.role = role;
    }
}

