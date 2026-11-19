class ScannerStation extends Thread {
    private final int scripts;

    ScannerStation(String name, int scripts) {
        super(name);
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(getName() + " started");
            Thread.sleep(500 + (int)(Math.random() * 1000));
            System.out.println(getName() + " scanned " + scripts + " scripts");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(getName() + " interrupted");
        }
    }
}

public class ScannerStations {
    public static void main(String[] args) throws InterruptedException {
        ScannerStation[] stations = {
            new ScannerStation("Station-1", 10),
            new ScannerStation("Station-2", 12),
            new ScannerStation("Station-3", 8),
            new ScannerStation("Station-4", 15)
        };

        for (ScannerStation station : stations) station.start();
        for (ScannerStation station : stations) station.join();

        int total = 10 + 12 + 8 + 15;
        System.out.println("All stations finished.");
        System.out.println("Total scripts scanned: " + total);
    }
}
