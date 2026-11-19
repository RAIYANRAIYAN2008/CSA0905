interface Chargeable {
    double chargingFee(int units);
    default void showChargingMessage() { System.out.println("Charging service is available"); }
    static boolean validUnits(int units) { return units >= 0; }
}
abstract class Vehicle {
    private final String regNo;
    Vehicle(String regNo) { this.regNo = regNo; }
    String getRegNo() { return regNo; }
    abstract double calculateFee(int hours);
}
class ElectricCar extends Vehicle implements Chargeable {
    ElectricCar(String regNo) { super(regNo); }
    @Override double calculateFee(int hours) { return hours * 30.0; }
    @Override public double chargingFee(int units) { return units * 12.0; }
}
public class Main {
    public static void main(String[] args) {
        ElectricCar car = new ElectricCar("TN03C3");
        car.showChargingMessage();
        System.out.println("Parking fee: Rs " + car.calculateFee(2));
        System.out.println("Charging fee: Rs " + car.chargingFee(5));
        System.out.println("Valid units: " + Chargeable.validUnits(5));
    }
}
