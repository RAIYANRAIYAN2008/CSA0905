import java.sql.*;
import java.util.*;

public class PassLoader {
    // Assignment TODOs 1-3 completed: bind values, batch each 1000, commit/rollback.
    public static int loadAll(Connection con, List<String[]> passes) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) VALUES (?, ?, ?, ?, ?)";
        boolean oldAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int n = 0;
            for (String[] p : passes) {
                ps.setString(1, p[0]);
                ps.setString(2, p[1]);
                ps.setString(3, p[2]);
                ps.setInt(4, Integer.parseInt(p[3]));
                ps.setDouble(5, Double.parseDouble(p[4]));
                ps.addBatch();
                n++;
                if (n % 1000 == 0) ps.executeBatch();
            }
            ps.executeBatch();
            con.commit();
            return n;
        } catch (SQLException | RuntimeException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }
    public static void main(String[] args) throws Exception {
        List<String[]> rows = new ArrayList<>();
        for (int i = 1; i <= 1000; i++)
            rows.add(new String[]{"P"+i, "REG"+i, "Student"+i, "5", "4500.0"});
        try (Connection con = DBConfig.getConnection()) {
            System.out.println("Rows loaded: " + loadAll(con, rows));
        }
    }
}
