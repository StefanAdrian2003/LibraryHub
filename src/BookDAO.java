import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // CREATE
    public void addBook(Connection conn, Book book, Library library) throws SQLException {
        String insertBookSQL = "INSERT INTO Book(title, publisher, author_id) VALUES (?, ?, ?)";
        String linkSQL = "INSERT INTO B_L(library_id, book_id) VALUES (?, ?)";

        int generatedBookId;

        try (PreparedStatement stmt = conn.prepareStatement(insertBookSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getPublisher());
            stmt.setInt(3, book.getAuthor().getId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedBookId = rs.getInt(1);
            } else {
                throw new SQLException("Book ID generation failed.");
            }
        }

        try (PreparedStatement stmt2 = conn.prepareStatement(linkSQL)) {
            stmt2.setInt(1, library.getId());
            stmt2.setInt(2, generatedBookId);
            stmt2.executeUpdate();
        }
    }

    // READ
    public Book getBook(Connection conn, String bookTitle) throws SQLException {
        String sql = """
            SELECT b.id, 
                   b.title, 
                   b.publisher,
                   l.id AS library_id, 
                   l.name AS library_name,
                   l.city, 
                   l.library_type,
                   a.id AS author_id,
                   a.name AS author_name
            FROM Book b
            JOIN B_L bl ON bl.book_id = b.id
            JOIN Library l ON l.id = bl.library_id
            JOIN Author a ON a.id = b.author_id
            WHERE b.title = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookTitle);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                int libraryId = rs.getInt("library_id");
                String libraryName = rs.getString("library_name");
                String city = rs.getString("city");
                String libraryType = rs.getString("library_type");
                int authorId = rs.getInt("author_id");
                String authorName = rs.getString("author_name");

                Author author = new Author(authorName);
                author.setId(authorId);

                Library library = libraryType.equals("LocalLibrary") ? new LocalLibrary(libraryName, city) : new NationalLibrary(libraryName, city);
                library.setId(libraryId);

                Book book = new Book(title, publisher, author, library);
                book.setId(bookId);

                return book;
            }
        }
        return null;
    }

    // READ ALL
    public List<Book> getAllBooksForLibrary(Connection conn, Library library) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = """
            SELECT b.id, 
                   b.title, 
                   b.publisher,
                   l.id AS library_id, 
                   l.name AS library_name,
                   l.city, 
                   l.library_type,
                   a.id AS author_id,
                   a.name AS author_name
            FROM Book b
            JOIN B_L bl ON bl.book_id = b.id
            JOIN Library l ON l.id = bl.library_id
            JOIN Author a ON a.id = b.author_id
            WHERE l.id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, library.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                int libraryId = rs.getInt("library_id");
                String libraryName = rs.getString("library_name");
                String city = rs.getString("city");
                String libraryType = rs.getString("library_type");
                int authorId = rs.getInt("author_id");
                String authorName = rs.getString("author_name");

                Author author = new Author(authorName);
                author.setId(authorId);

                Book book = new Book(title, publisher, author, library);
                book.setId(bookId);

                books.add(book);
            }
        }
        return books;
    }

    // UPDATE
    public boolean updateBook(Connection conn, Book book) throws SQLException {
        String sql = "UPDATE Book SET title = ?, publisher = ?, author_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getPublisher());
            stmt.setInt(3, book.getAuthor().getId());
            stmt.setInt(4, book.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteBook(Connection conn, Book book) throws SQLException {
        String sql = "DELETE FROM Book WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}
