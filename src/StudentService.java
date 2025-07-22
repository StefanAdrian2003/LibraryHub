import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private static StudentService instance = null;
    private final StudentDAO studentDAO;

    private StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    // CREATE
    public boolean addStudent(Student student) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            studentDAO.addStudent(conn, student);
            Audit.createAudit();
            System.out.println("Student added successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding student.");
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
    public Student getStudent(String personalId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Student student = studentDAO.readStudent(conn, personalId);
            if (student != null) {
                Audit.readAudit();
                System.out.println("Student retrieved successfully.");
            }
            return student;
        } catch (SQLException e) {
            System.out.println("Error reading student.");
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
    public List<Student> getAllStudents() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Student> students = studentDAO.readAllStudent(conn);
            Audit.readAudit();
            System.out.println("Students retrieved successfully.");
            return students;
        } catch (SQLException e) {
            System.out.println("Error reading students.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // GET SUBSCRIPTIONS BY STUDENT
    public List<Subscription> getSubscriptionsForStudent(Student student) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Subscription> subscriptions = studentDAO.getSubscriptionsByStudent(conn, student);
            Audit.readAudit();
            System.out.println("Subscriptions retrieved successfully.");
            return subscriptions;
        } catch (SQLException e) {
            System.out.println("Error getting subscriptions.");
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
    public void updateStudent(Student student) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = studentDAO.updateStudent(conn, student);
            if(ok){
                Audit.updateAudit();
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("No student found to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating student.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    public void deleteStudent(Student student) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = studentDAO.deleteStudent(conn, student);
            if(ok){
                Audit.deleteAudit();
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
