import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO {
    // CREATE
    public void addSubscription(Connection conn, Subscription subscription, Student student, Library library) throws SQLException {
        String sql = "INSERT INTO Subscription(subscription_type, expiration_date, student_id, library_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subscription.getType());
            stmt.setDate(2, Date.valueOf(subscription.getExpiration_date()));
            stmt.setString(3, student.getPersonalId());
            stmt.setInt(4, library.getId());
            stmt.executeUpdate();
        }
    }

    // READ
    public Subscription getSubscription(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Subscription WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");

                Subscription subscription = new Subscription(type);
                subscription.setId(id);
                return subscription;
            }
        }
        return null;
    }

    // READ ALL
    public List<Subscription> getAllSubscriptions(Connection conn) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscription";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");

                Subscription subscription = new Subscription(type);
                subscription.setId(id);
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }

    // UPDATE
    public boolean updateSubscription(Connection conn, Subscription subscription, Student student, Library library) throws SQLException {
        String sql = "UPDATE Subscription SET expiration_date = ? WHERE student_id = ?, faculty_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(subscription.getExpiration_date()));
            stmt.setString(2, student.getPersonalId());
            stmt.setInt(3, library.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteSubscription(Connection conn, Student student, Library library) throws SQLException {
        String sql = "DELETE FROM Subscription WHERE student_id = ?, library_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getPersonalId());
            stmt.setInt(2, library.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}
