import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    // CREATE
    public void addFaculty(Connection conn, Faculty faculty) throws SQLException {
        String sql = "INSERT INTO Faculty (name, city) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, faculty.getName());
            stmt.setString(2, faculty.getCity());
            stmt.executeUpdate();
        }
    }

    // READ by ID
    public Faculty getFaculty(Connection conn, int facultyId) throws SQLException {
        String sql = "SELECT * FROM Faculty WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, facultyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name").trim();
                    String city = rs.getString("city").trim();
                    Faculty faculty = new Faculty(name, city);
                    faculty.setId(id);
                    return faculty;
                }
            }
        }
        return null;
    }

    // READ by Name
    public Faculty getFacultyByName(Connection conn, String facultyName) throws SQLException {
        String sql = "SELECT * FROM Faculty WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, facultyName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name").trim();
                    String city = rs.getString("city").trim();
                    Faculty faculty = new Faculty(name, city);
                    faculty.setId(id);
                    return faculty;
                }
            }
        }
        return null;
    }

    // READ ALL FACULTIES
    public List<Faculty> getAllFaculties(Connection conn) throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        String sql = "SELECT * FROM Faculty";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name").trim();
                String city = rs.getString("city").trim();
                Faculty faculty = new Faculty(name, city);
                faculty.setId(id);
                faculties.add(faculty);
            }
        }
        return faculties;
    }

    // UPDATE
    public boolean updateFaculty(Connection conn, Faculty faculty) throws SQLException {
        String sql = "UPDATE Faculty SET name = ?, city = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, faculty.getName());
            stmt.setString(2, faculty.getCity());
            stmt.setInt(3, faculty.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteFaculty(Connection conn, Faculty faculty) throws SQLException {
        String sql = "DELETE FROM Faculty WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, faculty.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}
