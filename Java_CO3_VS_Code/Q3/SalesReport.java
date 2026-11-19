import java.io.*;
import java.util.*;

public class SalesReport {
    // try-with-resources automatically closes every opened file.
    public static void main(String[] args) {
        List<String> inputFiles = Arrays.asList("store1.txt", "store2.txt");
        double total = 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter("summary.txt"))) {
            writer.println("DAILY SALES REPORT");
            for (String file : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        double sale = Double.parseDouble(line.trim());
                        total += sale;
                    }
                    System.out.println("Processed: " + file);
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Failed to process " + file + ": " + e.getMessage());
                }
            }
            writer.println("Total sales: " + total);
            System.out.println("Report created. Total sales: " + total);
        } catch (IOException e) {
            System.out.println("Could not write summary: " + e.getMessage());
        }
    }
}
