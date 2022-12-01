package app;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Education {
    public int lga_code21;
    public String status;
    public String sex;
    public String education;
    public int count21;
    public int count21_1;
    public int count16;
    public int count16_1;
    public double score21;
    public double score16;
    public double scoreDiff;
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

    public Education() {
    }

    public Education(int lga_code21, String lga_name16, String lga_name21, String lga_state21, String lga_type21, int area_sqkm21, String lga_state16, String status, String sex, String education, int count21, int count21_1, int count16, int count16_1, int Total_People, double scoreDiff) {
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
        this.education = education;
        this.count21 = count21;
        this.count21_1 = count21_1;
        this.count16 = count16;
        this.count16_1 = count16_1;
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
    public String getEducation() {
        return education;
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

    String pattern = "##.#####";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    
    public double getScore21() {
        if (count21 == 0 || count21_1 == 0){
            score21 = 0;
        }
        else{
            score21 = (double)count21/count21_1;
            score21 = Double.valueOf(decimalFormat.format(score21));
        }
        return score21;
    }
    public double getScore16(){
        if (count16 == 0 || count16_1 == 0){
            score16 = 0;
        }
        else{
            score16 = (double)count16/count16_1;
            score16 = Double.valueOf(decimalFormat.format(score16));
        }
        return score16;
    }
    public double getScoreDiff(){
        scoreDiff = getScore21() - getScore16();
        scoreDiff = Double.valueOf(decimalFormat.format(scoreDiff));
        return scoreDiff;
    }
    public int getTotalPeople() {
        return Total_People;
    }
    

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
