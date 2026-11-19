import java.sql.*;
import java.util.Scanner;

public class LoginDemo {
    static boolean unsafeLogin(String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        try (Connection con = DBConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) { return rs.next(); }
    }
    static boolean safeLogin(String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id); ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
    public static void main(String[] args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("User ID: "); String id = sc.nextLine();
            System.out.print("Password: "); String pwd = sc.nextLine();
            System.out.println("Unsafe result: " + unsafeLogin(id, pwd));
            System.out.println("PreparedStatement result: " + safeLogin(id, pwd));
        }
    }
}
