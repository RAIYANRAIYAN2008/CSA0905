import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ResultProcessor {
    static class Result {
        final String file;
        final boolean success;
        final int mark;
        final String message;

        Result(String file, boolean success, int mark, String message) {
            this.file = file; this.success = success;
            this.mark = mark; this.message = message;
        }
    }

    static class ProcessFile implements Callable<Result> {
        private final File file;
        ProcessFile(File file) { this.file = file; }

        public Result call() {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                if (line == null) throw new IOException("Empty file");
                int mark = Integer.parseInt(line.trim());
                if (mark < 0 || mark > 100) throw new IllegalArgumentException("Invalid mark");
                return new Result(file.getName(), true, mark, "Processed");
            } catch (Exception e) {
                return new Result(file.getName(), false, 0, e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File folder = new File("scores");
        if (!folder.exists()) folder.mkdir();

        // Sample input files are created so the program runs immediately.
        writeSample("scores/student1.txt", "85");
        writeSample("scores/student2.txt", "92");
        writeSample("scores/student3.txt", "bad-data");
        // student4.txt is intentionally missing to demonstrate a failure.
        List<Callable<Result>> tasks = new ArrayList<>();
        for (int i = 1; i <= 4; i++)
            tasks.add(new ProcessFile(new File("scores/student" + i + ".txt")));

        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Future<Result>> futures = new ArrayList<>();
        ConcurrentLinkedQueue<Result> results = new ConcurrentLinkedQueue<>();

        try {
            for (Callable<Result> task : tasks) futures.add(pool.submit(task));
            for (Future<Result> future : futures) results.add(future.get());
        } finally {
            pool.shutdown();
        }

        int success = 0, failure = 0;
        for (Result r : results) {
            if (r.success) {
                success++;
                System.out.println(r.file + ": SUCCESS, mark = " + r.mark);
            } else {
                failure++;
                System.out.println(r.file + ": FAILED - " + r.message);
            }
        }
        System.out.println("Summary: Success = " + success + ", Failure = " + failure);
    }

    private static void writeSample(String path, String value) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(value);
        }
    }
}
