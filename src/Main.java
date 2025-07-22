import java.sql.Connection;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorService authorService = AuthorService.getInstance();
        BookService bookService = BookService.getInstance();
        FacultyService facultyService = FacultyService.getInstance();
        LibraryService libraryService = LibraryService.getInstance();
        StudentService studentService = StudentService.getInstance();
        SubscriptionService subscriptionService = SubscriptionService.getInstance();


        List<Faculty> faculties = new ArrayList<>();

        String[] faculty_names = {"UniBuc", "Politehnica", "UniBuc", "ASE"};
        String[] faculty_cities = {"Bucharest", "Bucharest", "Bucharest", "Bucharest"};

        for (int i = 0; i < faculty_names.length; i++) {
            Faculty faculty = new Faculty(faculty_names[i], faculty_cities[i]);

            boolean ok = facultyService.addFaculty(faculty);

            if(ok) {
                System.out.println("Faculty added: " + faculty_names[i] + " " + faculty_cities[i] + "\n");
                faculties.add(faculty);
            } else {
                System.out.println("Faculty not added: " + faculty_names[i] + " " + faculty_cities[i] + "\n");
            }
        }


        List<Student> students = new ArrayList<Student>();

        String[] personal_ids = {"123456", "234567", "345678", "456789"};
        String[] lastNames = {"Pop", "Popescu", "Ionescu", "Georgescu"};
        String[] firstNames = {"Stefan", "Alex", "Maria", "Elena"};
        String[] student_study_type = {"IF", "ID", "IF", "IFR"};
        String[] student_degree_level = {"BachelorStudent", "MasterStudent", "BachelorStudent", "MasterStudent"};
        int[] enrolment_years = {2022, 2024, 2023, 2025};

        for (int i = 0; i < personal_ids.length; i++) {
            String p_id = personal_ids[i];
            String l_Name = lastNames[i];
            String f_Name = firstNames[i];
            String stud_st = student_study_type[i];
            String stud_dl = student_degree_level[i];
            int y = enrolment_years[i];

            Student student = stud_dl.equals("BachelorStudent") ?
                    new BachelorStudent(p_id, l_Name, f_Name, y, facultyService.getFacultyByName(faculty_names[i]), stud_st)
                    : new MasterStudent(p_id, l_Name, f_Name, y, facultyService.getFacultyByName(faculty_names[i]), stud_st);

            boolean ok = studentService.addStudent(student);

            if(ok) {
                System.out.println("Student added: " + f_Name + " " + l_Name + "\n");
                students.add(student);
            } else {
                System.out.println("Student not added: " + f_Name + " " + l_Name + "\n");
            }
        }


        List<Library> libraries = new ArrayList<>();

        String[] libraryNames = {"National Library of Bucharest", "Ion Creanga Library", "Carturesti Library"};
        String[] libraryCities = {"Bucharest", "Ilfov", "Bucharest"};
        String[] libraryTypes = {"NationalLibrary", "LocalLibrary", "LocalLibrary"};

        for (int i = 0; i < libraryNames.length; i++) {
            String libraryName = libraryNames[i];
            String libraryCity = libraryCities[i];
            String libraryType = libraryTypes[i];

            Library library = libraryType.equals("NationalLibrary")?
                                new NationalLibrary(libraryName, libraryCity) :
                                new LocalLibrary(libraryName, libraryCity);

            boolean ok = libraryService.addLibrary(library);

            if(ok) {
                System.out.println("Library added: " + libraryName + " " + libraryCity + "\n");
                libraries.add(library);
            } else {
                System.out.println("Library not added: " + libraryName + " " + libraryCity + "\n");
            }
        }


        List<Subscription> subscriptions = new ArrayList<>();


        List<Author> authors = new ArrayList<>();

        String[] authorNames = {"Ion Luca Caragiale", "Mircea Cărtărescu", "J.K. Rowling",
                                "Gabriel García Márquez", "Fyodor Dostoevsky"};

        for (String authorName : authorNames) {
            Author author = new Author(authorName);

            boolean ok = authorService.addAuthor(author);
            if(ok) {
                System.out.println("Author added: " + authorName + "\n");
                authors.add(author);
            } else {
                System.out.println("Author not added: " + authorName + "\n");
            }
        }


        List<Book> books = new ArrayList<>();

        String[] bookTitles = {
                // Mircea Cărtărescu
                "Orbitor: Aripa stângă",
                "Orbitor: Corpul",
                "Orbitor: Aripa dreaptă",

                // Ion Luca Caragiale
                "Momente și schițe",
                "O scrisoare pierdută",
                "Năpasta",

                // J.K. Rowling
                "Harry Potter and the Philosopher's Stone",
                "Harry Potter and the Chamber of Secrets",
                "Harry Potter and the Prisoner of Azkaban",

                // Gabriel García Márquez
                "One Hundred Years of Solitude",
                "Love in the Time of Cholera",
                "Chronicle of a Death Foretold",

                // Fyodor Dostoevsky
                "Crime and Punishment",
                "The Brothers Karamazov",
                "The Idiot"
        };
        String[] bookPublishers = {
                // For Mircea Cărtărescu
                "Humanitas",                      // Orbitor: Aripa stângă
                "Humanitas",                      // Orbitor: Corpul
                "Humanitas",                      // Orbitor: Aripa dreaptă

                // For Ion Luca Caragiale
                "Editura Litera",                 // Momente și schițe
                "Editura Minerva",                // O scrisoare pierdută
                "Editura Polirom",                // Năpasta

                // For J.K. Rowling
                "Bloomsbury Publishing",          // Harry Potter and the Philosopher's Stone
                "Bloomsbury Publishing",          // Harry Potter and the Chamber of Secrets
                "Bloomsbury Publishing",          // Harry Potter and the Prisoner of Azkaban

                // For Gabriel García Márquez
                "Harper & Row",                   // One Hundred Years of Solitude
                "Penguin Books",                  // Love in the Time of Cholera
                "Vintage Español",                // Chronicle of a Death Foretold

                // For Fyodor Dostoevsky
                "Penguin Classics",               // Crime and Punishment
                "Everyman's Library",             // The Brothers Karamazov
                "Wordsworth Editions"             // The Idiot
        };


        for (int i = 0; i < 6; i++) {
            String bookTitle = bookTitles[i];
            String bookPublisher = bookPublishers[i];

            Author author = authorService.getAuthor(authorNames[i/3]);
            Library library = libraryService.getLibrary(libraryNames[0]);
            Book book = new Book(bookTitle, bookPublisher, author, library);

            boolean ok = bookService.addBook(book, library);
            if(ok) {
                books.add(book);
                System.out.println("Book added: " + bookTitle + " " + author.getName() + "Library: " + library.getName() + "\n");
            } else {
                System.out.println("Book not added: " + bookTitle + " " + author.getName() + "Library: " + library.getName() + "\n");
            }
        }

        for (int i = 6; i < 12; i++) {
            String bookTitle = bookTitles[i];
            String bookPublisher = bookPublishers[i];

            Author author = authorService.getAuthor(authorNames[i/3]);
            Library library = libraryService.getLibrary(libraryNames[1]);
            Book book = new Book(bookTitle, bookPublisher, author, library);

            boolean ok = bookService.addBook(book, library);
            if(ok) {
                books.add(book);
                System.out.println("Book added: " + bookTitle + " " + author.getName() + "Library: " + library.getName() + "\n");
            } else {
                System.out.println("Book not added: " + bookTitle + " " + author.getName() + "Library: " + library.getName() + "\n");
            }
        }

        for (int i = 12; i < 15; i++) {
            String bookTitle = bookTitles[i];
            String bookPublisher = bookPublishers[i];

            Author author = authorService.getAuthor(authorNames[i/3]);
            Library library = libraryService.getLibrary(libraryNames[2]);
            Book book = new Book(bookTitle, bookPublisher, author, library);

            boolean ok = bookService.addBook(book, library);
            if(ok) {
                books.add(book);
                System.out.println("Book added: " + bookTitle + " " + author.getName() + "Library: " + library.getName() + "\n");
            } else {
                System.out.println("Book not added: " + bookTitle + " " + author.getName() + "Library: " + library.getName() + "\n");
            }
        }

        Menu.getInstance().display();
    }
}
