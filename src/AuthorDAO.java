import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    // CREATE
    public void addAuthor(Connection conn, Author author) throws SQLException {
        String sql = "INSERT INTO Author(name) VALUES (?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, author.getName());
            stmt.executeUpdate();
        }
    }


    // READ
    public Author getAuthor(Connection conn, String authorName) throws SQLException {
        String sql = "SELECT * FROM Author WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name").trim();

                Author author = new Author(name);
                author.setId(id);
                System.out.println("Author read.\n");
                Audit.readAudit();
                return author;
            }
        }
        return null;
    }


    // READ ALL AUTHORS
    public List<Author> getAllAuthors(Connection conn) throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Author";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Author author = new Author(name);
                author.setId(id);

                authors.add(author);
            }
        }
        return authors;
    }


    // READ ALL BOOKS FOR AUTHOR
    public List<Book> getAllBooksByAuthor(Connection conn, Author author) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = """
            SELECT b.id,
                   b.title,
                   b.publisher,
                   l.id AS library_id,
                   l.name AS library_name,
                   l.city,
                   l.library_type
            FROM Book b
            JOIN B_L bl ON bl.book_id = b.id
            JOIN Library l ON l.id = bl.library_id
            WHERE b.author_id = ?
        """;

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, author.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title").trim();
                String publisher = rs.getString("publisher").trim();

                int libId = rs.getInt("library_id");
                String libraryName = rs.getString("library_name").trim();
                String city = rs.getString("city").trim();
                String libraryType = rs.getString("library_type").trim();

                Library library = libraryType.equals("LocalLibrary")? new LocalLibrary(libraryName, city) : new NationalLibrary(libraryName, city);
                library.setId(libId);

                Book book = new Book(title, publisher, author, library);
                book.setId(id);

                books.add(book);
            }
        }
        return books;
    }


    // UPDATE
    public boolean updateAuthor(Connection conn, Author author) throws SQLException {
        String sql = "UPDATE Author SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, author.getName());
            stmt.setInt(2, author.getId());
            return stmt.executeUpdate() > 0;
        }
    }


    // DELETE
    public boolean deleteAuthor(Connection conn, Author author) throws SQLException {
        String sql = "DELETE FROM Author WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, author.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}
