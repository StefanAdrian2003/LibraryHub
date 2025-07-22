import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LibraryService {
    private static LibraryService instance = null;
    private final LibraryDAO libraryDAO;

    private LibraryService() {
        this.libraryDAO = new LibraryDAO();
    }

    public static LibraryService getInstance() {
        if (instance == null) {
            instance = new LibraryService();
        }
        return instance;
    }

    public boolean addLibrary(Library lib) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            libraryDAO.addLibrary(conn, lib);
            Audit.createAudit();
            System.out.println("Library added successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding library.");
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Library getLibrary(String libraryName) {

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Library library = libraryDAO.getLibrary(conn, libraryName);
            if (library != null) {
                Audit.readAudit();
                System.out.println("Library retrieved successfully.");
            }
            return library;
        } catch (SQLException e) {
            System.out.println("Error reading library.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Library> getAllLibraries() {

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Library> libraries = libraryDAO.getAllLibraries(conn);
            Audit.readAudit();
            System.out.println("Libraries retrieved successfully.");
            return libraries;
        } catch (SQLException e) {
            System.out.println("Error reading libraries.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> getBooksByLibrary(Library library) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Book> books = libraryDAO.getBooksByLibrary(conn, library);
            Audit.readAudit();
            System.out.println("Books retrieved for library.");
            return books;
        } catch (SQLException e) {
            System.out.println("Error retrieving books.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<Student, Subscription> getSubscriptionsByLibrary(Library library){
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Map<Student, Subscription> subscriptions = libraryDAO.getSubscriptionsByLibrary(conn, library);
            Audit.readAudit();
            System.out.println("Subscriptions retrieved for library.");
            return subscriptions;
        } catch (SQLException e) {
            System.out.println("Error retrieving subscriptions.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLibrary(Library lib) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = libraryDAO.updateLibrary(conn, lib);
            if(ok){
                Audit.updateAudit();
                System.out.println("Library updated successfully.");
            } else {
                System.out.println("No library found to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating library.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteLibrary(Library lib) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = libraryDAO.deleteLibrary(conn, lib);
            if(ok){
                Audit.deleteAudit();
                System.out.println("Library deleted successfully.");
            } else {
                System.out.println("No library found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting library.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
