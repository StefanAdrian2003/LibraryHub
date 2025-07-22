import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE
    public void addStudent(Connection conn, Student student) throws SQLException {
        String sql = "INSERT INTO Student (personal_id, last_name, first_name, enrollment_year, faculty_id, study_type, degree_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getPersonalId());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getFirstName());
            stmt.setInt(4, student.getEnrollmentYear());
            stmt.setInt(5, student.getFaculty().getId());
            stmt.setString(6, student.getStudyType());
            stmt.setString(7, student.getClass().getSimpleName());

            stmt.executeUpdate();
        }
    }

    // READ
    public Student readStudent(Connection conn, String studentId) throws SQLException {
        String sql = "SELECT * FROM Student WHERE personal_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");
                    int enrollmentYear = rs.getInt("enrollment_year");
                    int facultyId = rs.getInt("faculty_id");
                    String studyType = rs.getString("study_type");
                    String degreeLevel = rs.getString("degree_level");

                    Faculty faculty = new FacultyDAO().getFaculty(conn, facultyId);

                    Student student = degreeLevel.equals("BachelorStudent") ?
                            new BachelorStudent(studentId, lastName, firstName, enrollmentYear, faculty, studyType) :
                            new MasterStudent(studentId, lastName, firstName, enrollmentYear, faculty, studyType);

                    return student;
                }
            }
        }
        return null;
    }

    // READ ALL
    public List<Student> readAllStudent(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Student";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String studentId = rs.getString("personal_id");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                int enrollmentYear = rs.getInt("enrollment_year");
                int facultyId = rs.getInt("faculty_id");
                String studyType = rs.getString("study_type");
                String degreeLevel = rs.getString("degree_level");

                Faculty faculty = new FacultyDAO().getFaculty(conn, facultyId);

                Student student = degreeLevel.equals("BachelorStudent") ?
                        new BachelorStudent(studentId, lastName, firstName, enrollmentYear, faculty, studyType) :
                        new MasterStudent(studentId, lastName, firstName, enrollmentYear, faculty, studyType);

                students.add(student);
            }
        }
        return students;
    }

    // GET SUBSCRIPTIONS BY STUDENT
    public List<Subscription> getSubscriptionsByStudent(Connection conn, Student student) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT id, subscription_type, expiration_date FROM Subscription WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getPersonalId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String type = rs.getString("subscription_type");
                    LocalDate expiration = rs.getDate("expiration_date").toLocalDate();

                    Subscription subscription = new Subscription(type);
                    subscription.setId(id);

                    subscriptions.add(subscription);
                }
            }
        }
        return subscriptions;
    }

    // UPDATE
    public boolean updateStudent(Connection conn, Student student) throws SQLException {
        String sql = "UPDATE Student SET last_name = ?, first_name = ?, enrollment_year = ?, faculty_id = ?, study_type = ?, degree_level = ? WHERE personal_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getLastName());
            stmt.setString(2, student.getFirstName());
            stmt.setInt(3, student.getEnrollmentYear());
            stmt.setInt(4, student.getFaculty().getId());
            stmt.setString(5, student.getStudyType());
            stmt.setString(6, student.getClass().getSimpleName());
            stmt.setString(7, student.getPersonalId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteStudent(Connection conn, Student student) throws SQLException {
        String sql = "DELETE FROM Student WHERE personal_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getPersonalId());
            return stmt.executeUpdate() > 0;
        }
    }
}
