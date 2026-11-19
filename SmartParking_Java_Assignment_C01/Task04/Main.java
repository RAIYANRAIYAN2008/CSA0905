class ParkingTicket {
    private int ticketNo;
    private String regNo;
    ParkingTicket() { this(0, "UNKNOWN"); }
    ParkingTicket(int ticketNo) { this(ticketNo, "UNKNOWN"); }
    ParkingTicket(int ticketNo, String regNo) {
        this.ticketNo = ticketNo;
        this.regNo = regNo;
    }
    ParkingTicket(ParkingTicket other) { this(other.ticketNo, other.regNo); }
    void display() { System.out.println("Ticket " + ticketNo + " - " + regNo); }
}
public class Main {
    public static void main(String[] args) {
        ParkingTicket t1 = new ParkingTicket(1001, "TN01AB1111");
        ParkingTicket t2 = new ParkingTicket(t1);
        t1.display();
        t2.display();
    }
}
