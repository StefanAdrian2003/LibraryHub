import java.time.LocalDate;

public class Subscription {
    private int id;
    final private String type;
    private LocalDate expiration_date;

    public Subscription(String type)
    {
        this.type = type;
        if(type.equals("Monthly")) {
            expiration_date = LocalDate.now().plusMonths(1);
        }
        else if(type.equals("Yearly")) {
            expiration_date = LocalDate.now().plusYears(1);
        }
    }

    public int getId() { return id; }

    public String getType() { return type; }

    public LocalDate getExpiration_date() { return expiration_date; }

    public void setId(int id) { this.id = id; }

    public void renew(Subscription subscription, Student student, Library library)
    {
        expiration_date = type.equals("Monthly")? LocalDate.now().plusMonths(1) : LocalDate.now().plusYears(1);
        SubscriptionService subscriptionService = SubscriptionService.getInstance();
        subscriptionService.updateSubscription(subscription, student, library);
    }

    public boolean isExpired()
    {
        return expiration_date.isBefore(LocalDate.now());
    }
}
