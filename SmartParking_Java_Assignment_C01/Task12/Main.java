import java.util.*;
interface PassHolder { boolean hasValidPass(); }
abstract class Vehicle {
    protected String regNo;
    Vehicle(String regNo) { this.regNo = regNo; }
    abstract double fee(int hours, int hourOfDay);
}
class Car extends Vehicle {
    Car(String r) { super(r); }
    double fee(int h, int time) { return h * 30.0; }
}
class EBike extends Vehicle {
    EBike(String r) { super(r); }
    double fee(int h, int time) { return 25.0; }
}
class DeliveryVan extends Vehicle {
    DeliveryVan(String r) { super(r); }
    double fee(int h, int time) { return h * 30.0 * ((time >= 9 && time < 17) ? 2 : 1); }
}
class VisitorPass implements PassHolder {
    private final boolean valid;
    VisitorPass(boolean valid) { this.valid = valid; }
    public boolean hasValidPass() { return valid; }
}
public class Main {
    public static void main(String[] args) {
        Vehicle[] vehicles = { new Car("TN01CAR"), new EBike("TN02EB"), new DeliveryVan("TN03VAN") };
        int[] hours = {2, 2, 2};
        int[] entryHour = {10, 10, 10};
        for (int i = 0; i < vehicles.length; i++)
            System.out.println(vehicles[i].getClass().getSimpleName() + " " + vehicles[i].regNo
                    + " -> Rs " + vehicles[i].fee(hours[i], entryHour[i]));
        VisitorPass pass = new VisitorPass(true);
        System.out.println("Visitor one-day pass valid: " + pass.hasValidPass());
        System.out.println("Design uses a jagged array-compatible model for unequal floor capacities.");
    }
}
