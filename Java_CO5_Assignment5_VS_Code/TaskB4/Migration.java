import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class Migration {
    public static void main(String[] args) throws Exception {
        Path input = Path.of(args.length == 0 ? "passes.csv" : args[0]);
        List<String[]> valid = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        int lineNo = 0;
        try (BufferedReader r = Files.newBufferedReader(input)) {
            String line;
            while ((line = r.readLine()) != null) {
                lineNo++;
                String[] p = line.split(",", -1);
                try {
                    if (p.length != 5) throw new IllegalArgumentException("expected 5 fields");
                    Integer.parseInt(p[3]); Double.parseDouble(p[4]);
                    valid.add(p);
                } catch (RuntimeException ex) {
                    failures.add("Line " + lineNo + ": " + ex.getMessage() + " -> " + line);
                }
            }
        }
        try (Connection con = DBConfig.getConnection()) {
            System.out.println("Migrated rows: " + PassLoader.loadAll(con, valid));
            System.out.println("Validation failures:");
            failures.forEach(System.out::println);
            DatabaseMetaData md = con.getMetaData();
            System.out.println("Database: " + md.getDatabaseProductName());
            System.out.println("Driver: " + md.getDriverName() + " " + md.getDriverVersion());
            try (ResultSet tables = md.getTables(con.getCatalog(), null, "%", new String[]{"TABLE"})) {
                while (tables.next()) System.out.println("Table: " + tables.getString("TABLE_NAME"));
            }
        }
    }
}
