import java.util.ArrayList;
import java.util.List;

public class Author {
    private int id;
    private String name;

    private List<Book> books = new ArrayList<Book>();

    public Author(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setId(int id) { this.id = id; }
}
