import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";  // modifică aici dacă ai alt port/bază
    private static final String USER = "postgres";   // înlocuiește cu utilizatorul tău
    private static final String PASSWORD = "Stefan2003"; // înlocuiește cu parola ta

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database.");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
