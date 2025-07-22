public class Faculty {
    private int id;
    private String name;
    private String city;

    public Faculty(String name, String city) {
        this.name = name;
        this.city = city;
    }
    public int getId() { return id; }

    public String getName(){
        return name;
    }

    public String getCity(){
        return city;
    }

    public void setId(int id) { this.id = id; }
}
