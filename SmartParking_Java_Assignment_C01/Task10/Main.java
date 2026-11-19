class ParkingSlot {
    private final int slotNumber;
    private String vehicleNo;
    ParkingSlot(int slotNumber) {
        if (slotNumber <= 0) throw new IllegalArgumentException("Slot number must be positive");
        this.slotNumber = slotNumber;
    }
    public int getSlotNumber() { return slotNumber; }
    public String getVehicleNo() { return vehicleNo; }
    public void park(String vehicleNo) {
        if (vehicleNo == null || vehicleNo.isBlank()) throw new IllegalArgumentException("Invalid vehicle number");
        if (this.vehicleNo != null) throw new IllegalStateException("Slot is occupied");
        this.vehicleNo = vehicleNo;
    }
    public void release() { vehicleNo = null; }
}
public class Main {
    public static void main(String[] args) {
        ParkingSlot slot = new ParkingSlot(5);
        slot.park("TN01AB1111");
        System.out.println("Slot: " + slot.getSlotNumber());
        System.out.println("Parked vehicle: " + slot.getVehicleNo());
        slot.release();
        System.out.println("After release: " + slot.getVehicleNo());
    }
}
