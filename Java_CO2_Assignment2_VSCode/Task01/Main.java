import java.util.*;

public class Main {
    static volatile int sink;

    static String buildWithString(int fields) {
        String ticket = "";
        for (int i = 1; i <= fields; i++) ticket = ticket + "F" + i + "=" + i + ";";
        return ticket;
    }

    static String buildWithBuilder(int fields) {
        StringBuilder ticket = new StringBuilder();
        for (int i = 1; i <= fields; i++) ticket.append("F").append(i).append("=").append(i).append(";");
        return ticket.toString();
    }

    static String buildWithBuffer(int fields) {
        StringBuffer ticket = new StringBuffer();
        for (int i = 1; i <= fields; i++) ticket.append("F").append(i).append("=").append(i).append(";");
        return ticket.toString();
    }

    static long measure(int mode, int tickets, int fields) {
        long start = System.nanoTime();
        int total = 0;
        for (int t = 0; t < tickets; t++) {
            String result;
            if (mode == 1) result = buildWithString(fields);
            else if (mode == 2) result = buildWithBuilder(fields);
            else result = buildWithBuffer(fields);
            total += result.length();
        }
        sink = total;
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        int[][] workloads = {{1, 6}, {100, 20}, {1000, 50}};
        String[] names = {"Short ticket", "Medium workload", "Heavy workload"};
        for (int w = 0; w < workloads.length; w++) {
            int tickets = workloads[w][0], fields = workloads[w][1];
            System.out.println("\n" + names[w] + " (" + tickets + " tickets, " + fields + " fields)");
            for (int mode = 1; mode <= 3; mode++) {
                long total = 0;
                for (int repeat = 0; repeat < 3; repeat++) total += measure(mode, tickets, fields);
                String label = mode == 1 ? "String +" : mode == 2 ? "StringBuilder" : "StringBuffer";
                System.out.printf("%-14s average: %.3f ms%n", label, total / 3.0 / 1_000_000.0);
            }
        }
        System.out.println("\nNote: timings vary by computer and JVM; run several times for evidence.");
        System.out.println("Recommendation: use StringBuilder for a ticket and StringBuffer only when shared synchronized mutation is required.");
    }
}
