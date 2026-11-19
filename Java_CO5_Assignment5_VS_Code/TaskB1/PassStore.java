import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file = file; }

    public void add(String passId, String regNo, String name, int route, double amount) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(file, java.nio.charset.StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(String.join(",", passId, regNo, name, String.valueOf(route), String.valueOf(amount)));
            w.newLine();
        }
    }

    // Assignment TODO 1 completed: buffered scan, try-with-resources, skip damaged lines.
    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file, java.nio.charset.StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 5) {
                    System.out.println("Skipping damaged line: " + line);
                    continue;
                }
                try {
                    Integer.parseInt(fields[3]);
                    Double.parseDouble(fields[4]);
                } catch (NumberFormatException ex) {
                    System.out.println("Skipping invalid numeric data: " + line);
                    continue;
                }
                if (fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Path f = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);
        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        System.out.println("Missing ID: " + Arrays.toString(store.find("P9999")));
        Files.deleteIfExists(f);
    }
}
