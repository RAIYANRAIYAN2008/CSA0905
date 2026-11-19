public class Main {
    public static void main(String[] args) {
        int floor = 1, slot = 3, minutes = 125;
        double hourlyRate = 30.0;
        int billableHours = (minutes + 59) / 60;
        double fee = billableHours * hourlyRate;
        int[][] slotsPerFloor = {{0, 1, 2}, {0, 1, 2, 3}};
        System.out.println("Floor: " + floor);
        System.out.println("Slot: " + slot);
        System.out.println("Total slots: " + (slotsPerFloor[0].length + slotsPerFloor[1].length));
        System.out.println("Billable hours: " + billableHours);
        System.out.println("Parking fee: Rs " + fee);
        System.out.println("Integer division risk: 125 / 60 = " + (125 / 60) + " (incorrect)");
    }
}
