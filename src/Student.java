import java.util.ArrayList;
import java.util.List;

public abstract class Student {
    private final String personal_id;
    private String lastName;
    private String firstName;
    private int enrollment_year;
    private Faculty faculty;
    private String study_type;

    private List<Subscription> subscriptions = new ArrayList<Subscription>();

    public Student(String personal_id, String lastName, String firstName, int enrollment_year, Faculty faculty, String study_type) {
        this.personal_id = personal_id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.enrollment_year = enrollment_year;
        this.faculty = faculty;
        this.study_type = study_type;
    }

    public void show_profile(){
        System.out.println("Name: " + this.firstName + " " + this.lastName +
                           ", student at " + faculty.getName() + " from " + faculty.getCity());
        System.out.println("Study type: " + study_type);
        System.out.println("Degree level: " + this.getClass().getSimpleName());
    }

    public String getPersonalId() { return personal_id; }

    public String getLastName() { return lastName; }

    public String getFirstName() { return firstName; }

    public int getEnrollmentYear() { return enrollment_year; }

    public Faculty getFaculty() { return faculty; }

    public String getStudyType() { return study_type; }

    public abstract int getDiscount(int subscriptionsCreated);
}


class BachelorStudent extends Student {
    private static final int[] discounts = {75, 60, 45};
    public BachelorStudent(String personal_id, String lastName, String firstName, int enrolment_year, Faculty faculty, String study_type) {
        super(personal_id, lastName, firstName, enrolment_year, faculty, study_type);
    }
    public int getDiscount(int subscriptionsCreated){
        if(subscriptionsCreated > 2) return 0;
        return discounts[subscriptionsCreated];
    }
}

class MasterStudent extends Student {
    private static final int[] discounts = {30, 15, 10};
    public MasterStudent(String personal_id, String lastName, String firstName, int enrolment_year, Faculty faculty, String study_type) {
        super(personal_id, lastName, firstName, enrolment_year, faculty, study_type);
    }
    public int getDiscount(int subscriptionsCreated){
        if(subscriptionsCreated > 2) return 0;
        return discounts[subscriptionsCreated];
    }
}