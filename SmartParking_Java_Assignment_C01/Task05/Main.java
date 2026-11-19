class Ticket {
    private static int nextNumber = 1000;
    private final int ticketNumber;
    Ticket() { ticketNumber = ++nextNumber; }
    static int getTicketsIssued() { return nextNumber - 1000; }
    void display() { System.out.println("Ticket number: " + ticketNumber); }
}
public class Main {
    public static void main(String[] args) {
        Ticket a = new Ticket();
        Ticket b = new Ticket();
        a.display();
        b.display();
        System.out.println("Total tickets: " + Ticket.getTicketsIssued());
    }
}
