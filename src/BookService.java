import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static BookService instance = null;
    private final BookDAO bookDAO;

    private BookService() {
        this.bookDAO = new BookDAO();
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    // CREATE
    public boolean addBook(Book book, Library library) {
        boolean result;
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            bookDAO.addBook(conn, book, library);

            conn.commit();
            Audit.createAudit();
            Audit.createAudit();
            System.out.println("Book added and linked to library successfully.");
            result = true;
        } catch (SQLException e) {
            System.out.println("Transaction failed.");
            result = false;
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed.");
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    // READ
    public Book getBook(String bookTitle) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Book book = bookDAO.getBook(conn, bookTitle);
            if (book != null) {
                Audit.readAudit();
                System.out.println("Book read successfully.");
            }
            return book;
        } catch (SQLException e) {
            System.out.println("Error reading book.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    // READ ALL
    public List<Book> getAllBooksForLibrary(Library library) {
        Connection conn = null;
        List<Book> books = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            books = bookDAO.getAllBooksForLibrary(conn, library);
            Audit.readAudit();
            System.out.println("Books read successfully.");
        } catch (SQLException e) {
            System.out.println("Error reading books.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return books;
    }

    // UPDATE
    public void updateBook(Book book) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (bookDAO.updateBook(conn, book)) {
                System.out.println("Book updated successfully.");
                Audit.updateAudit();
            } else {
                System.out.println("No book found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating book.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // DELETE
    public void deleteBook(Book book) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (bookDAO.deleteBook(conn, book)) {
                System.out.println("Book deleted successfully.");
                Audit.deleteAudit();
            } else {
                System.out.println("No book found to delete.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
