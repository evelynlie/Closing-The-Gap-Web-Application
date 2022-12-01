package app;

public class Population {
    public String status;
    public int Total_People;

    
    public Population() {
    }

    public Population(String status, int Total_People) {
        this.status = status;
        this.Total_People = Total_People;
    }

    public String getStatus() {
        return status;
    }
    public int getTotalPeople() {
        return Total_People;
    }
    
}
