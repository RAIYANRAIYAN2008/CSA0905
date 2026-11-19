
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    static long timeGet(List<Integer> list, int n) {
        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            int value = list.get(i % list.size());
        }

        return System.nanoTime() - start;
    }

    static long timeIterate(List<Integer> list) {
        long start = System.nanoTime();
        long sum = 0;

        for (int value : list) {
            sum += value;
        }

        return System.nanoTime() - start;
    }

    static long timeAddEnd(List<Integer> list, int n) {
        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        return System.nanoTime() - start;
    }

    static long timeFrontInsert(List<Integer> list, int n) {
        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.add(0, i);
        }

        return System.nanoTime() - start;
    }

    static long timeMiddleInsert(List<Integer> list, int n) {
        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.add(list.size() / 2, i);
        }

        return System.nanoTime() - start;
    }

    static long timeArrayDeque(int n) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < n; i++) {
            deque.removeFirst();
        }

        return System.nanoTime() - start;
    }

    static void printTime(String operation, long time) {
        System.out.printf("%-35s %.3f ms%n",
                operation, time / 1_000_000.0);
    }

    public static void main(String[] args) {

        int n = 1000;

        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        System.out.println("Playlist size: " + n);
        System.out.println();

        System.out.println("GET OPERATION");
        printTime("ArrayList get k-th:",
                timeGet(arrayList, n));
        printTime("LinkedList get k-th:",
                timeGet(linkedList, n));

        System.out.println();

        System.out.println("ITERATION OPERATION");
        printTime("ArrayList iteration:",
                timeIterate(arrayList));
        printTime("LinkedList iteration:",
                timeIterate(linkedList));

        System.out.println();

        System.out.println("ADD AT END");
        printTime("ArrayList add at end:",
                timeAddEnd(new ArrayList<Integer>(), n));
        printTime("LinkedList add at end:",
                timeAddEnd(new LinkedList<Integer>(), n));

        System.out.println();

        System.out.println("INSERT AT FRONT");
        printTime("ArrayList front insertion:",
                timeFrontInsert(new ArrayList<Integer>(), n));
        printTime("LinkedList front insertion:",
                timeFrontInsert(new LinkedList<Integer>(), n));

        System.out.println();

        System.out.println("INSERT AT MIDDLE");
        printTime("ArrayList middle insertion:",
                timeMiddleInsert(new ArrayList<Integer>(), n));
        printTime("LinkedList middle insertion:",
                timeMiddleInsert(new LinkedList<Integer>(), n));

        System.out.println();

        System.out.println("DOUBLE ENDED QUEUE");
        printTime("ArrayDeque addLast/removeFirst:",
                timeArrayDeque(n));

        System.out.println();
        System.out.println("Task 2 completed successfully.");
    }
}
