import java.sql.*;
public class DBConfig {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/co5_assignment5", "root", "CHANGE_ME");
    }
}
