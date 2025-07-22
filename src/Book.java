import java.util.List;

public class Book {
    private int id;
    final private String name;
    final private String publisher;
    final private Author author;
    private Library library;

    public Book(String name, String publisher, Author author, Library library) {
        this.name = name;
        this.publisher = publisher;
        this.author = author;
        this.library = library;
    }

    public int getId() { return id; }

    public Author getAuthor() {
        return author;
    }

    public String getName() { return name; }

    public String getPublisher() { return publisher; }

    public Library getLibrary() {
        return library;
    }

    public void setId(int id) { this.id = id; }
}
