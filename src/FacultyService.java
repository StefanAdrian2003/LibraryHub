import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacultyService {
    private static FacultyService instance = null;
    private final FacultyDAO facultyDAO;

    private FacultyService() {
        this.facultyDAO = new FacultyDAO();
    }

    public static FacultyService getInstance() {
        if (instance == null) {
            instance = new FacultyService();
        }
        return instance;
    }

    // CREATE
    public boolean addFaculty(Faculty faculty) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            facultyDAO.addFaculty(conn, faculty);
            Audit.createAudit();
            System.out.println("Faculty added successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding faculty.");
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ by ID
    public Faculty getFaculty(int facultyId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Faculty faculty = facultyDAO.getFaculty(conn, facultyId);
            if (faculty != null) {
                Audit.readAudit();
                System.out.println("Faculty retrieved successfully.");
            }
            return faculty;
        } catch (SQLException e) {
            System.out.println("Error retrieving faculty by ID.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ by Name
    public Faculty getFacultyByName(String facultyName) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Faculty faculty = facultyDAO.getFacultyByName(conn, facultyName);
            if (faculty != null) {
                Audit.readAudit();
                System.out.println("Faculty retrieved successfully.");
            }
            return faculty;
        } catch (SQLException e) {
            System.out.println("Error retrieving faculty by name.");
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
    public List<Faculty> getAllFaculties() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Faculty> faculties = facultyDAO.getAllFaculties(conn);
            Audit.readAudit();
            System.out.println("Faculties read.");
            return faculties;
        } catch (SQLException e) {
            System.out.println("Error retrieving all faculties.");
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
    public void updateFaculty(Faculty faculty) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean updated = facultyDAO.updateFaculty(conn, faculty);
            if (updated) {
                Audit.updateAudit();
                System.out.println("Faculty updated successfully.");
            } else {
                System.out.println("No faculty found to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating faculty.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    public void deleteFaculty(Faculty faculty) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean deleted = facultyDAO.deleteFaculty(conn, faculty);
            if (deleted) {
                Audit.deleteAudit();
                System.out.println("Faculty deleted successfully.");
            } else {
                System.out.println("No faculty found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting faculty.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
