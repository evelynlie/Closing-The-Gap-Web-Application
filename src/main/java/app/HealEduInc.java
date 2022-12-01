package app;
import java.net.Socket;
import java.text.DecimalFormat;

public class HealEduInc {
    public int lga_code21;
    public String status;
    public String income;
    public String condition;
    public String education;
    public double score;
    public int totalIndi;
    public int totalNon;
    
    public HealEduInc() {
    }

    public HealEduInc(int lga_code21, String status, String income, String education, String condition, double score, int totalIndi, int totalNon) {
        this.lga_code21 = lga_code21;
        this.status = status;
        this.income = income;
        this.condition = condition;
        this.education = education;
        this.score = score;
        this.totalIndi = totalIndi;
        this.totalNon = totalNon;
    }

    public int getLGACode21() {
        return lga_code21;
    }
    public String getStatus() {
        return status;
    }
    public String getIncome() {
        return income;
    }
    public String getCondition() {
        return condition;
    }
    public String getEducation() {
        return education;
    }
    public int getTotalIndi() {
        return totalIndi;
    }
    public int getTotalNonIndi() {
        return totalNon;
    }
    public double getScore21() {
        return score;
    }
}