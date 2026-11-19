import java.util.*;
class ParkedVehicle {
    String regNo, type;
    long entryTime;
    ParkedVehicle(String regNo, String type) {
        this.regNo = regNo; this.type = type; this.entryTime = System.currentTimeMillis();
    }
}
class ParkingSystem {
    private final String[][] slots;
    private final Map<String, ParkedVehicle> active = new HashMap<>();
    private int ticketCounter = 1000;
    ParkingSystem(int[] capacity) {
        slots = new String[capacity.length][];
        for (int i = 0; i < capacity.length; i++) slots[i] = new String[capacity[i]];
    }
    void enter(String regNo, String type) {
        if (active.containsKey(regNo)) { System.out.println("Rejected: duplicate registration"); return; }
        for (int f = 0; f < slots.length; f++) for (int s = 0; s < slots[f].length; s++) {
            if (slots[f][s] == null) {
                slots[f][s] = regNo;
                active.put(regNo, new ParkedVehicle(regNo, type));
                System.out.println("Entry accepted. Ticket: " + (++ticketCounter) + ", slot: [" + f + ", " + s + "]");
                return;
            }
        }
        System.out.println("Parking full");
    }
    void exit(String regNo, int minutes) {
        ParkedVehicle v = active.remove(regNo);
        if (v == null) { System.out.println("Rejected: vehicle not found"); return; }
        for (int f = 0; f < slots.length; f++) for (int s = 0; s < slots[f].length; s++)
            if (regNo.equals(slots[f][s])) slots[f][s] = null;
        int hours = (minutes + 59) / 60;
        double rate = v.type.equalsIgnoreCase("Car") ? 30 : 10;
        System.out.println("Exit accepted. Fee: Rs " + (hours * rate));
    }
}
public class Main {
    public static void main(String[] args) {
        ParkingSystem p = new ParkingSystem(new int[]{1, 2});
        p.enter("TN01AB1111", "Car");
        p.enter("TN02CD2222", "Bike");
        p.enter("TN01AB1111", "Car");
        p.exit("TN01AB1111", 60);
        p.exit("TN99ZZ9999", 30);
    }
}
