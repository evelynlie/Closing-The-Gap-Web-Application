package app;
import java.net.Socket;
import java.text.DecimalFormat;

public class EduandIncome {
    public int lga_code21;
    public String status;
    public String sex;
    public String income;
    public String education;
    public int EduindiCount21;
    public int EdunonCount21;
    public int IncomeindiCount21;
    public int IncomenonCount21;
    public double score21;
    public double score16;
    public double scoreDiff;
    public int totalIndi21;
    public int totalNon21;
    public int totalIndi16;
    public int totalNon16;
    
    public EduandIncome() {
    }

    public EduandIncome(int lga_code21, String status, String sex, String income, String education, int EduindiCount21, int EdunonCount21, int IncomeindiCount21, int IncomenonCount21, double score21, double score16, int totalIndi21, int totalNon21, int totalIndi16, int totalNon16, double scoreDiff) {
        this.lga_code21 = lga_code21;
        this.status = status;
        this.sex = sex;
        this.income = income;
        this.education = education;
        this.EduindiCount21 = EduindiCount21;
        this.EdunonCount21 = EdunonCount21;
        this.IncomeindiCount21 = IncomeindiCount21;
        this.IncomenonCount21 = IncomenonCount21;
        this.score21 = score21;
        this.score16 = score16;
        this.scoreDiff = scoreDiff;
        this.totalIndi21 = totalIndi21;
        this.totalNon21 = totalNon21;
        this.totalIndi16 = totalIndi16;
        this.totalNon16 = totalNon16;
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
    public String getIncome() {
        return income;
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
    public int getIncIndi() {
        return IncomeindiCount21;
    }
    public int getIncNon() {
        return IncomenonCount21;
    }
    public int getTotalIndi21() {
        return totalIndi21;
    }
    public int getTotalNonIndi21() {
        return totalNon21;
    }

    public int getTotalIndi16() {
        return totalIndi16;
    }
    public int getTotalNonIndi16() {
        return totalNon16;
    }

    public double getScore21() {
        return score21;
    }

    public double getScore16() {
        return score16;
    }

    public double getScoreDiff() {
        return scoreDiff;
    }
}
