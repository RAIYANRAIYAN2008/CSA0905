import java.util.*;
class Vehicle {
    String regNo, type;
    Vehicle(String regNo, String type) { this.regNo = regNo; this.type = type; }
}
class ParkingTicket {
    int ticketNo;
    Vehicle vehicle;
    ParkingTicket(int ticketNo, Vehicle vehicle) { this.ticketNo = ticketNo; this.vehicle = vehicle; }
}
public class Main {
    public static void main(String[] args) {
        Vehicle v = new Vehicle("TN01AB1111", "Car");
        ParkingTicket t = new ParkingTicket(1001, v);
        System.out.println("Vehicle: " + v.regNo);
        System.out.println("Type: " + v.type);
        System.out.println("Ticket: " + t.ticketNo);
        System.out.println("Candidate classes: Vehicle, ParkingTicket, ParkingSlot, ParkingFacility");
    }
}
