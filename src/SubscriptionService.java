import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionService {
    private static SubscriptionService instance = null;
    private final SubscriptionDAO subscriptionDAO;

    private SubscriptionService() {
        this.subscriptionDAO = new SubscriptionDAO();
    }

    public static SubscriptionService getInstance() {
        if (instance == null) {
            instance = new SubscriptionService();
        }
        return instance;
    }

    // CREATE
    public boolean addSubscription(Subscription subscription, Student student, Library library) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            subscriptionDAO.addSubscription(conn, subscription, student, library);
            Audit.createAudit();
            System.out.println("Subscription added successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding subscription.");
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ
    public Subscription getSubscription(int personalId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Subscription subscription = subscriptionDAO.getSubscription(conn, personalId);
            if (subscription != null) {
                Audit.readAudit();
                System.out.println("Subscription retrieved successfully.");
            }
            return subscription;
        } catch (SQLException e) {
            System.out.println("Error reading subscription.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ ALL
    public List<Subscription> getAllSubscriptions() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Subscription> subscriptions = subscriptionDAO.getAllSubscriptions(conn);
            if (subscriptions != null) {
                Audit.readAudit();
                System.out.println("Subscriptions retrieved successfully.");
            }
            return subscriptions;
        } catch (SQLException e) {
            System.out.println("Error reading subscriptions.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // UPDATE
    public void updateSubscription(Subscription subscription, Student student, Library library) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = subscriptionDAO.updateSubscription(conn, subscription, student, library);
            if(ok){
                Audit.updateAudit();
                System.out.println("Subscription updated successfully.");
            } else {
                System.out.println("No subscription found to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating subscription.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    public void deleteSubscription(Student student, Library library) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = subscriptionDAO.deleteSubscription(conn, student, library);
            if(ok){
                Audit.deleteAudit();
                System.out.println("Subscription deleted successfully.");
            } else {
                System.out.println("No subscription found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting subscription.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
