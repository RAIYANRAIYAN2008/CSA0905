import java.sql.*;
public class Benchmark {
    public static void main(String[] args) throws Exception {
        try (Connection con = DBConfig.getConnection()) {
            String[] methods = {"Statement", "PreparedStatement", "Batch"};
            for (String method : methods) {
                con.createStatement().executeUpdate("DELETE FROM bus_pass");
                long start = System.nanoTime();
                if (method.equals("Statement")) {
                    try (Statement st = con.createStatement()) {
                        for (int i=1;i<=1000;i++)
                            st.executeUpdate("INSERT INTO bus_pass VALUES ('S"+i+"','R"+i+"','Name"+i+"',5,4500)");
                    }
                } else if (method.equals("PreparedStatement")) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO bus_pass VALUES (?,?,?,?,?)")) {
                        for (int i=1;i<=1000;i++) {
                            ps.setString(1,"P"+i); ps.setString(2,"R"+i); ps.setString(3,"Name"+i);
                            ps.setInt(4,5); ps.setDouble(5,4500); ps.executeUpdate();
                        }
                    }
                } else {
                    java.util.List<String[]> data = new java.util.ArrayList<>();
                    for (int i=1;i<=1000;i++) data.add(new String[]{"B"+i,"R"+i,"Name"+i,"5","4500"});
                    PassLoader.loadAll(con,data);
                }
                System.out.println(method + " 1000 rows ms: " + (System.nanoTime()-start)/1_000_000.0);
            }
        }
    }
}
