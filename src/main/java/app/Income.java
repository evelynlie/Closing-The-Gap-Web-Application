package app;
import java.text.DecimalFormat;

public class Income {
    public int lga_code21;
    public String status;
    public String income_range;
    public int income_min;
    public int income_max;
    public int count21;
    public int count21_1;
    public double score21;
    public double score16;
    public int Total_People;
    public double percentage;
    public String lga_state21;
    public String lga_type21;
    public int area_sqkm21;
    public String lga_state16;
    public String lga_type16;
    public int area_sqkm16;
    public int count16;
    public int count16_1;
    public String lga_name16;
    public String lga_name21;
    public double gapscore;
    public double scoreDiff;
    public String income;

    public Income() {
    }

    public Income(int lga_code21, String lga_name16, String lga_name21, String lga_state21, String lga_type21, String income, int area_sqkm21, String lga_state16, String lga_type16, int area_sqkm16, String status, String income_range, int income_min, int income_max, int count21, int count21_1, int count16, int count16_1, int Total_People, int gapscore) {
        this.lga_code21 = lga_code21;
        this.lga_name16 = lga_name16;
        this.lga_name21 = lga_name21;
        this.lga_state21 = lga_state21;
        this.lga_type21 = lga_type21;
        this.income = income;
        this.area_sqkm21 = area_sqkm21;
        this.lga_state16 = lga_state16;
        this.lga_type16 = lga_type16;
        this.area_sqkm16 = area_sqkm16;
        this.status = status;
        this.income_range = income_range;
        this.income_min = income_min;
        this.income_max = income_max;
        this.count21 = count21;
        this.count21_1 = count21_1;
        this.count16 = count16;
        this.count16 = count16_1;
        this.Total_People = Total_People;
        this.gapscore = gapscore;
    }

    public int getLGACode21() {
        return lga_code21;
    }
    public String getLGAState21() {
        return lga_state21;
    }
    public String getName16() {
        return lga_name16;
    }
    public String getName21() {
        return lga_name21;
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
    public String getIncomeRange() {
        return income_range;
    }
    public String getIncome() {
        return income;
    }
    public int getIncomeMin() {
        return income_min;
    }
    public int getIncomeMax() {
        return income_max;
    }
    public int getCount21() {
        return count21;
    }
    public int getCount21_1() {
        return count21_1;
    }
    public int getCount16() {
        return count16;
    }
    public int getCount16_1() {
        return count16_1;
    }
    public int getTotalPeople() {
        return Total_People;
    }
    String pattern = "##.#####";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

    public double getScore21() {
        if (count21 == 0 || count21_1 == 0){
            gapscore = 0;
        }
        else{
            gapscore = (double)count21/count21_1;
            gapscore = Double.valueOf(decimalFormat.format(gapscore));
        }
        return gapscore;
    }
    public double getScore16(){
        if (count16 == 0 || count16_1 == 0){
            gapscore = 0;
        }
        else{
            gapscore = (double)count16/count16_1;
            gapscore = Double.valueOf(decimalFormat.format(gapscore));
        }
        return gapscore;
    }
    public double getScoreDiff(){
        scoreDiff = getScore21() - getScore16();
        scoreDiff = Double.valueOf(decimalFormat.format(scoreDiff));
        return scoreDiff;
    }
    
    public double getPercentage21() {
        if (status.equals("At least 1 indigenous member")){
            percentage = ((double)count21/312716) * 100.00;
        }
        else if (status.equals("Non-indigenous household")){
            percentage = ((double)count21/8153598) * 100.00;
        }
        else{
            int total = 312716 + 8153598;
            percentage = ((double)count21/total) * 100.00;
        }
        percentage = Double.valueOf(decimalFormat.format(percentage));
        return percentage;
    }
}
