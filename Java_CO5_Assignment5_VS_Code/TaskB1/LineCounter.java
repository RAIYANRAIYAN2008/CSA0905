import java.io.*;
import java.nio.file.*;

public class LineCounter {
    static long countUnbuffered(Path p) throws IOException {
        long lines = 0;
        try (InputStream in = new FileInputStream(p.toFile())) {
            int b;
            while ((b = in.read()) != -1) if (b == '\n') lines++;
        }
        return lines;
    }
    static long countBuffered(Path p) throws IOException {
        long lines = 0;
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(p), 8192)) {
            byte[] buffer = new byte[8192];
            int n;
            while ((n = in.read(buffer)) != -1)
                for (int i = 0; i < n; i++) if (buffer[i] == '\n') lines++;
        }
        return lines;
    }
    public static void main(String[] args) throws IOException {
        Path p = Files.createTempFile("attendance", ".txt");
        try (BufferedWriter w = Files.newBufferedWriter(p)) {
            for (int i = 0; i < 100000; i++) w.write("Student" + i + ",Present\n");
        }
        long t1 = System.nanoTime(); long a = countUnbuffered(p); long t2 = System.nanoTime();
        long b = countBuffered(p); long t3 = System.nanoTime();
        System.out.println("Lines (unbuffered): " + a + " | time ms: " + (t2-t1)/1_000_000.0);
        System.out.println("Lines (buffered):   " + b + " | time ms: " + (t3-t2)/1_000_000.0);
        System.out.println("Note: run with 1 MB, 10 MB and 50 MB files for assignment measurements.");
        Files.deleteIfExists(p);
    }
}
