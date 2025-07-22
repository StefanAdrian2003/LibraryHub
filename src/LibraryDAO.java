import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Student Data Acces Objects
public class LibraryDAO {

    //CREATE
    public void addLibrary(Connection conn, Library library) throws SQLException {
        String sql = "INSERT INTO Library (name, city, library_type) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, library.getName());
            stmt.setString(2, library.getCity());
            stmt.setString(3, library.getClass().getSimpleName());
            stmt.executeUpdate();
        }
    }


    // READ
    public Library getLibrary(Connection conn, String libraryName) throws SQLException {
        String sql = "SELECT * FROM Library WHERE name = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libraryName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String city = rs.getString("city").trim();
                String type = rs.getString("library_type").trim();

                Library library = type.equals("LocalLibrary") ? new LocalLibrary(libraryName, city) : new NationalLibrary(libraryName, city);

                library.setId(id);
                System.out.println("Library read.\n");

                Audit.readAudit();
                return library;
            }

        } catch (SQLException e) {
            System.out.println("Error at reading library. Verify your data.\n");
        }

        return null;
    }


    // READ ALL LIBRARIES
    public List<Library> getAllLibraries(Connection conn) throws SQLException {
        List<Library> libraries = new ArrayList<>();
        String sql = "SELECT * FROM Library";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name").trim();
                String city = rs.getString("city").trim();
                String type = rs.getString("library_type").trim();

                Library library = type.equals("LocalLibrary") ? new LocalLibrary(name, city) : new NationalLibrary(name, city);

                library.setId(id);
                libraries.add(library);
            }
            System.out.println("Libraries read.\n");
            Audit.readAudit();

        }
        return libraries;
    }


    // READ ALL BOOKS FOR LIBRARY
    public List<Book> getBooksByLibrary(Connection conn, Library library) throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = """
            SELECT b.id AS book_id,
                   b.title,
                   b.publisher,
                   a.id AS author_id,
                   a.name AS author_name
            FROM Book b
            JOIN b_l bl ON b.id = bl.book_id
            JOIN Library l ON bl.library_id = l.id
            JOIN Author a ON b.author_id = a.id
            WHERE l.id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, library.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title").trim();
                String publisher = rs.getString("publisher").trim();

                int authorId = rs.getInt("author_id");
                String authorName = rs.getString("author_name").trim();

                Author author = new Author(authorName);
                author.setId(authorId);

                Book book = new Book(title, publisher, author, library);
                book.setId(bookId);

                books.add(book);
            }
        }

        return books;
    }


    // READ ALL SUBSCRIPTIONS FOR LIBRARY
    public Map<Student, Subscription> getSubscriptionsByLibrary(Connection conn, Library library) throws SQLException {

        Map<Student, Subscription> subscriptions = new HashMap<>();

        String sql = """
                SELECT st.personal_id,
                       st.last_name,
                       st.first_name,
                       st.enrollment_year,
                       st.study_type,
                       st.degree_level,
                       f.id AS faculty_id,
                       f.name,
                       f.city,
                       sub.subscription_type,
                       sub.id AS sub_id
                FROM Student st
                JOIN Faculty f on f.id = st.faculty_id
                JOIN Subscription sub ON st.personal_id = sub.student_id
                JOIN Library l ON l.id = sub.library_id
                WHERE l.id = ?
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, library.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("personal_id").trim();
                String lastName = rs.getString("last_name").trim();
                String firstName = rs.getString("first_name").trim();
                int enrollmentYear = rs.getInt("enrollment_year");
                String studyType = rs.getString("study_type").trim();
                String degreeLevel = rs.getString("degree_level").trim();

                int facultyId = rs.getInt("faculty_id");
                String facultyName = rs.getString("name").trim();
                String facultyCity = rs.getString("city").trim();

                String subType = rs.getString("subscription_type").trim();
                int subId = rs.getInt("sub_id");

                Faculty faculty = new Faculty(facultyName, facultyCity);
                faculty.setId(facultyId);

                Student student = degreeLevel.equals("BachelorStudent")?
                        new BachelorStudent(studentId, lastName, firstName, enrollmentYear, faculty, studyType) :
                        new MasterStudent(studentId, lastName, firstName, enrollmentYear, faculty, studyType);

                Subscription subscription = new Subscription(subType);
                subscription.setId(subId);

                subscriptions.put(student, subscription);

            }


        }
        return subscriptions;
    }


    // UPDATE
    public boolean updateLibrary(Connection conn, Library library) throws SQLException {
        String sql = "UPDATE Library SET name = ?, city = ?, library_type = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, library.getName());
            stmt.setString(2, library.getCity());
            stmt.setString(3, library.getClass().getSimpleName());
            stmt.setInt(4, library.getId());
            return stmt.executeUpdate() > 0;
        }
    }


    // DELETE
    public boolean deleteLibrary(Connection conn, Library library) throws SQLException {
        String sql = "DELETE FROM Library WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, library.getId());
            return stmt.executeUpdate() > 0;
        }
    }

}
