import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> treeMap = new TreeMap<>();
        Map<String, Integer> linkedMap = new LinkedHashMap<>();

        String[] codes = {"MED103", "MED101", "MED104", "MED102"};
        int[] stocks = {25, 40, 12, 30};

        for (int i = 0; i < codes.length; i++) {
            hashMap.put(codes[i], stocks[i]);
            treeMap.put(codes[i], stocks[i]);
            linkedMap.put(codes[i], stocks[i]);
        }

        System.out.println("Stock lookup MED102: " + hashMap.get("MED102"));
        System.out.println("\nHashMap (no guaranteed order): " + hashMap);
        System.out.println("TreeMap (medicine code order): " + treeMap);
        System.out.println("LinkedHashMap (insertion order): " + linkedMap);

        int lookups = 100000;
        long start = System.nanoTime();
        int total = 0;
        for (int i = 0; i < lookups; i++) total += hashMap.get("MED102");
        long elapsed = System.nanoTime() - start;
        System.out.printf("%n%d HashMap lookups: %.3f ms (checksum %d)%n",
                lookups, elapsed / 1_000_000.0, total);

        System.out.println("\nRecommendation:");
        System.out.println("Use HashMap for fast average lookup, TreeMap for sorted code reports,");
        System.out.println("and LinkedHashMap when insertion order must be preserved.");
    }
}
