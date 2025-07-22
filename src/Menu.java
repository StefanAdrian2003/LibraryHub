import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Menu instance = null;

    private Menu() {}

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public static int current_year = Year.now().getValue();

    AuthorService authorService = AuthorService.getInstance();
    BookService bookService = BookService.getInstance();
    FacultyService facultyService = FacultyService.getInstance();
    LibraryService libraryService = LibraryService.getInstance();
    StudentService studentService = StudentService.getInstance();
    SubscriptionService subscriptionService = SubscriptionService.getInstance();

    private final Scanner scanner = new Scanner(System.in);

    public void display() {
        boolean running = true;
        System.out.println("Welcome to my Java project!");
        while (running) {
            System.out.println("\n=== Library Management Menu ===");
            System.out.println("1. Add new student");
            System.out.println("2. Show student details");
            System.out.println("3. Show all books for a library");
            System.out.println("4. Create subscription");
            System.out.println("5. Delete subscription");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleAddStudent();
                    break;
                case "2":
                    handleStudentDetails();
                    break;
                case "3":
                    handleShowBooks();
                    break;
                case "4":
                    handleCreateSubscription();
                    break;
                case "5":
                    handleDeleteSubscription();
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handleAddStudent() {
        try {
            System.out.print("Enter personal ID (exactly 6 numbers): ");
            String p_id = scanner.nextLine();
            if(p_id.length() != 6) { throw new IncorrectIdException("Personal Id must be exactly 6 numbers and different than others.\n"); }

            System.out.print("Enter last name: ");
            String l_Name = scanner.nextLine();
            if(l_Name == null || l_Name.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }

            System.out.print("Enter first name: ");
            String f_Name = scanner.nextLine();
            if(f_Name == null || f_Name.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }

            System.out.print("Enter enrollment year: ");
            int y = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter faculty name: ");
            String fac_name = scanner.nextLine();
            if(fac_name == null || fac_name.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }

            System.out.print("Enter study type (IF, IFR, ID): ");
            String stud_st = scanner.nextLine();
            if(stud_st == null || stud_st.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }
            if(!stud_st.equals("IF") && !stud_st.equals("IFR") && !stud_st.equals("ID")) { throw new NullPointerException("Please insert valid inputs.\n"); }

            System.out.print("Enter degree level (Bachelor, Master): ");
            String stud_dl = scanner.nextLine();
            if(stud_dl == null || stud_dl.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }
            if(!stud_dl.equals("Bachelor") && !stud_dl.equals("Master")) { throw new NullPointerException("Please insert valid inputs.\n"); }


            if (stud_dl.equals("Bachelor") && (current_year - y < 0 || current_year - y > 4))
            {
                throw new OutOfRangeException("Invalid Year. (Current - enrollment year <= 4)\n");
            }
            else if (stud_dl.equals("Master") && (current_year - y < 0 || current_year - y > 2))
            {
                throw new OutOfRangeException("Invalid Year. (Current - enrollment year <= 2)\n");
            }

            Student student = stud_dl.equals("Bachelor") ?
                    new BachelorStudent(p_id, l_Name, f_Name, y, facultyService.getFacultyByName(fac_name), stud_st)
                    : new MasterStudent(p_id, l_Name, f_Name, y, facultyService.getFacultyByName(fac_name), stud_st);

            System.out.println("Student added: " + f_Name + " " + l_Name);
            boolean ok = studentService.addStudent(student);
            if(!ok) { return; }

        } catch (IncorrectIdException | OutOfRangeException | NullPointerException e) {
            System.out.println("Error while adding student. " + e.getMessage());
        }
    }


    private void handleStudentDetails() {
        try {
            System.out.print("Enter students personalId (exactly 6 numbers): ");
            String p_id = scanner.nextLine();
            if(p_id.length() != 6) { throw new NullPointerException("Please insert valid inputs. Invalid personal ID.\n"); }

            Student student = studentService.getStudent(p_id);

            if (student != null) {
                student.show_profile();
            } else {
                throw new NullPointerException("No student found with this ID.\n");
            }
        } catch(NullPointerException e) {
            System.out.println("Invalid data. " + e.getMessage());
        }

    }

    private void handleShowBooks() {
        try {
            System.out.print("Enter one of the following libraries: ");
            for(Library lib : libraryService.getAllLibraries()) {
                System.out.println(lib.getName());
            }
            String name = scanner.nextLine();
            if(name == null || name.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }

            Library library = libraryService.getLibrary(name);
            if (library == null) {
                System.out.println("No library found with this name.\n");
                return;
            }

            List<Book> books = libraryService.getBooksByLibrary(library);
            if(books.isEmpty()) {
                System.out.println("No books found for this library.\n");
                return;
            }

            for (Book book : books) {
                System.out.println(book.getName() + book.getAuthor().getName());
            }
        } catch(NullPointerException e) {
            System.out.println("Invalid data. " + e.getMessage());
        }

    }

    private void handleCreateSubscription() {
        try {
            System.out.print("Choose one library to create subscription for: ");
            for(Library lib : libraryService.getAllLibraries()) {
                System.out.println(lib.getName());
            }
            String libraryName = scanner.nextLine();
            if(libraryName == null || libraryName.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }
            Library library = libraryService.getLibrary(libraryName);
            if (library == null) {
                System.out.println("No library found with this name.\n");
                return;
            }

            System.out.print("Enter student ID: ");
            String studentName = scanner.nextLine();
            if(studentName == null || studentName.isBlank()) { throw new NullPointerException("Please insert valid inputs.\n"); }
            Student student = studentService.getStudent(studentName);
            if (student == null) {
                System.out.println("No student found with this ID.\n");
                return;
            }

            System.out.print("Choose Monthly or Yearly for the subscription: ");
            String type = scanner.nextLine();
            if(!type.equals("Monthly") && !type.equals("Yearly")) { throw new NullPointerException("Please insert valid subscription type.\n"); }

            boolean ok = library.createSubscription(student, library, type);
            if(!ok) { return; }

            StudentService studentService = StudentService.getInstance();
            List<Subscription> studentSubscriptions = studentService.getSubscriptionsForStudent(student);
            int nrOfSubscriptions = studentSubscriptions.size();
            System.out.println("Subscription created with a discount of " + student.getDiscount(nrOfSubscriptions) + "% !.\n");

        } catch(NullPointerException e) {
            System.out.println("Invalid data. " + e.getMessage());
        }
    }

    public void handleDeleteSubscription(){

    }
}




