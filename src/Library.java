import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Library {
    private int id;
    private String name;
    private String city;

    private List<Book> books = new ArrayList<>();
    private Map<Student, Subscription> Subscriptions = new HashMap<Student, Subscription>();

    public int budget = 0;

    public Library(String name, String city)
    {
        this.name = name;
        this.city = city;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getCity() { return city; }



    public void setId(int id) { this.id = id; }

    public List<Book> getBooks()
    {
        return books;
    }

    public abstract boolean createSubscription(Student student, Library library, String type);
    public abstract void cancelSubscription(Student student, Library library);
}

class LocalLibrary extends Library {
    final private static int price = 100;
    public LocalLibrary(String name, String city) {
        super(name, city);
    }

    public boolean createSubscription(Student student, Library library, String type)
    {
        Subscription subscription = new Subscription(type);
        SubscriptionService subscriptionService = SubscriptionService.getInstance();
        budget += price;
        return subscriptionService.addSubscription(subscription, student, library);
    }

    public void cancelSubscription(Student student, Library library)
    {
        SubscriptionService subscriptionService = SubscriptionService.getInstance();
        subscriptionService.deleteSubscription(student, library);
        budget -= price;
    }
}

class NationalLibrary extends Library {
    private final static int price = 200;
    public NationalLibrary(String name, String city) {
        super(name, city);
    }

    public boolean createSubscription(Student student, Library library, String type)
    {
        Subscription subscription = new Subscription(type);
        SubscriptionService subscriptionService = SubscriptionService.getInstance();
        budget += price;
        return subscriptionService.addSubscription(subscription, student, library);
    }

    public void cancelSubscription(Student student, Library library)
    {
        SubscriptionService subscriptionService = SubscriptionService.getInstance();
        subscriptionService.deleteSubscription(student, library);
        budget -= price;
    }
}
