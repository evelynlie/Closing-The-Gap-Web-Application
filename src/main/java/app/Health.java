package app;
import java.text.DecimalFormat;
public class Health {
    public int lga_code21;
    public String lga_name21;
    public String lga_state21;
    public String status;
    public String sex;
    public String condition;
    public int count21;
    public int count21_1;
    public double score;
    public int Total_People;
    public double percentage;

    
    public Health() {
    }

    public Health(int lga_code21, String lga_name21, String lga_state21, String status, String status_1, String sex, String condition, int count21, int count21_1, int Total_People) {
        this.lga_code21 = lga_code21;
        this.lga_name21 = lga_name21;
        this.lga_state21 = lga_state21;
        this.status = status;
        this.sex = sex;
        this.condition = condition;
        this.count21 = count21;
        this.count21_1 = count21_1;
        this.Total_People = Total_People;
    }

    public int getLGACode21() {
        return lga_code21;
    }
    public String getName21() {
        return lga_name21;
    }
    public String getLGAState21() {
        return lga_state21;
    }
    public String getStatus() {
        return status;
    }
    public String getSex() {
        return sex;
    }
    public String getCondition() {
        return condition;
    }
    public int getCount21() {
        return count21;
    }
    public int getCount21_1() {
        return count21_1;
    }
    public int getTotalPeople() {
        return Total_People;
    }

    String pattern = "##.#####";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    public double getScore() {
        if (count21 == 0 || count21_1 == 0){
            score = 0;
        }
        else{
            score = (double)count21/count21_1;
            score = Double.valueOf(decimalFormat.format(score));
        }
        return score;
    }

    public double getPercentage21() {
        if (status.equals("indigenous")){
            percentage = ((double)count21/464176) * 100.00;
        }
        else if (status.equals("non-indigenous")){
            percentage = ((double)count21/11966806) * 100.00;
        }
        else{
            percentage = ((double)count21/84613) * 100.00;
        }
        percentage = Double.valueOf(decimalFormat.format(percentage));
        return percentage;
    }
}
