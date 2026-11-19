class Vehicle {
    private final String regNo;
    Vehicle(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    public double calculateFee(int minutes) { return ((minutes + 59) / 60) * ratePerHour(); }
    protected double ratePerHour() { return 0; }
    @Override public String toString() { return getClass().getSimpleName() + " " + regNo; }
    @Override public boolean equals(Object o) {
        return o instanceof Vehicle && regNo.equals(((Vehicle)o).regNo);
    }
    @Override public int hashCode() { return regNo.hashCode(); }
}
class Car extends Vehicle {
    Car(String regNo) { super(regNo); }
    @Override protected double ratePerHour() { return 30; }
}
public class Main {
    public static void main(String[] args) {
        Vehicle a = new Car("TN02B2");
        Vehicle b = new Car("TN02B2");
        System.out.println(a);
        System.out.println("Fee for 70 minutes: Rs " + a.calculateFee(70));
        System.out.println("Vehicles equal: " + a.equals(b));
        System.out.println("Hash codes equal: " + (a.hashCode() == b.hashCode()));
    }
}
