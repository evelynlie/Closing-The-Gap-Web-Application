package app;

public class Target {
    public int id;
    public String title;
    public String description;
    
    public Target() {
    }

    public Target(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}
