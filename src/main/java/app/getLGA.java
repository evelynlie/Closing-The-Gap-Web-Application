package app;

public class getLGA {
    public int lga_code21;
    public String name;
    public String state;
    public String type;
    public String area;
    
    public getLGA() {
    }

    public getLGA(int lga_code21, String name, String state, String type, String area) {
        this.lga_code21 = lga_code21;
        this.name = name;
        this.state = state;
        this.type = type;
        this.area = area;
    }

    public int getLGACode21() {
        return lga_code21;
    }
    public String getName() {
        return name;
    }
    public String getStates() {
        return state;
    }
    public String getType() {
        return type;
    }
    public String getArea() {
        return area;
    }
}
