import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AuthorService {
    private static AuthorService instance = null;
    private final AuthorDAO authorDAO;

    private AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    public static AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    // CREATE
    public boolean addAuthor(Author author) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            authorDAO.addAuthor(conn, author);
            Audit.createAudit();
            System.out.println("Author added successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding author.");
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
    public Author getAuthor(String authorName) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Author author = authorDAO.getAuthor(conn, authorName);
            if (author != null) {
                Audit.readAudit();
                System.out.println("Author retrieved successfully.");
            }
            return author;
        } catch (SQLException e) {
            System.out.println("Error reading author.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Author> getAllAuthors() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Author> authors = authorDAO.getAllAuthors(conn);
            Audit.readAudit();
            System.out.println("Authors retrieved successfully.");
            return authors;
        } catch (SQLException e) {
            System.out.println("Error reading authors.");
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> getAllBooksByAuthor(Author author) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            List<Book> books = authorDAO.getAllBooksByAuthor(conn, author);
            Audit.readAudit();
            System.out.println("Authors retrieved successfully.");
            return books;
        } catch (SQLException e) {
            System.out.println("Error reading authors.");
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
    public void updateAuthor(Author author) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = authorDAO.updateAuthor(conn, author);
            if(ok){
                Audit.updateAudit();
                System.out.println("Author updated successfully.");
            } else {
                System.out.println("No author found to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating author.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    public void deleteAuthor(Author author) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            boolean ok = authorDAO.deleteAuthor(conn, author);
            if(ok){
                Audit.deleteAudit();
                System.out.println("Author deleted successfully.");
            } else {
                System.out.println("No author found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting author.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
