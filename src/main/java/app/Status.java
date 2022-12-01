package app;
import java.text.DecimalFormat;

public class Status {
    public int lga_code21;
    public String status;
    public String sex;
    public String age_range;
    public int age_min;
    public int age_max;
    public int count21;
    public int count16;
    public int Total_People;
    public double percentage;

    public String lga_state21;
    public String lga_type21;
    public int area_sqkm21;
    public String lga_state16;
    public String lga_type16;
    public int area_sqkm16;
    public String lga_name16;
    public String lga_name21;

    public Status() {
    }

    public Status(int lga_code21, String lga_name16, String lga_name21, String lga_state21, String lga_type21, int area_sqkm21, String lga_state16, String lga_type16, int area_sqkm16,String status, String sex, String age_range, int age_min, int age_max, int count21, int count16, int Total_People) {
        this.lga_code21 = lga_code21;
        this.lga_name16 = lga_name16;
        this.lga_name21 = lga_name21;
        this.lga_state21 = lga_state21;
        this.lga_type21 = lga_type21;
        this.area_sqkm21 = area_sqkm21;
        this.lga_state16 = lga_state16;
        this.lga_type16 = lga_type16;
        this.area_sqkm16 = area_sqkm16;
        this.status = status;
        this.sex = sex;
        this.age_range = age_range;
        this.age_min = age_min;
        this.age_max = age_max;
        this.count21 = count21;
        this.count16 = count16;
        this.Total_People = Total_People;
    }

    public int getLGACode21() {
        return lga_code21;
    }
    public String getName16() {
        return lga_name16;
    }
    public String getName21() {
        return lga_name21;
    }
    public String getLGAState21() {
        return lga_state21;
    }
    public String getLGAType21() {
        return lga_type21;
    }
    public int getArea21() {
        return area_sqkm21;
    }
    public String getLGAState16() {
        return lga_state16;
    }
    public String getLGAType16() {
        return lga_type16;
    }
    public int getArea16() {
        return area_sqkm16;
    }
    public String getStatus() {
        return status;
    }
    public String getSex() {
        return sex;
    }
    public String getAgeRange() {
        return age_range;
    }
    public int getAgeMin() {
        return age_min;
    }
    public int getAgeMax() {
        return age_max;
    }
    public int getCount21() {
        return count21;
    }
    public int getCount16() {
        return count16;
    }
    public int getTotalPeople() {
        return Total_People;
    }

    String pattern = "##.#####";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

    public double getPercentage21() {
        if (status.equals("indigenous")){
            percentage = ((double)count21/811896) * 100.00;
        }
        else if (status.equals("non-indigenous")){
            percentage = ((double)count21/23375619) * 100.00;
        }
        else{
            percentage = ((double)count21/1233207) * 100.00;
        }
        percentage = Double.valueOf(decimalFormat.format(percentage));
        return percentage;
    }
}
