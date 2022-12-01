package app;
import java.net.Socket;
import java.text.DecimalFormat;

public class EduandHealth {
    public int lga_code21;
    public String status;
    public String sex;
    public String condition;
    public String education;
    public int EduindiCount21;
    public int EdunonCount21;
    public int HealthindiCount21;
    public int HealthnonCount21;
    public double score;
    public int totalIndi;
    public int totalNon;
    
    public EduandHealth() {
    }

    public EduandHealth(int lga_code21, String status, String sex, String condition, String education, int EduindiCount21, int EdunonCount21, int HealthindiCount21, int HealthnonCount21, double score, int totalIndi, int totalNon) {
        this.lga_code21 = lga_code21;
        this.status = status;
        this.sex = sex;
        this.condition = condition;
        this.education = education;
        this.EduindiCount21 = EduindiCount21;
        this.EdunonCount21 = EdunonCount21;
        this.HealthindiCount21 = HealthindiCount21;
        this.HealthnonCount21 = HealthnonCount21;
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
    public String getSex() {
        return sex;
    }
    public String getCondition() {
        return condition;
    }
    public String getEducation() {
        return education;
    }
    public int getEduIndi() {
        return EduindiCount21;
    }
    public int getEduNon() {
        return EdunonCount21;
    }
    public int getHealthIndi() {
        return HealthindiCount21;
    }
    public int getHealthNon() {
        return HealthnonCount21;
    }
    public int getTotalIndi() {
        return totalIndi;
    }
    public int getTotalNonIndi() {
        return totalNon;
    }

    String pattern = "##.#####";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

    public double getScore() {
        return score;
    }
}
