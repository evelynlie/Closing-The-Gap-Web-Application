package app;
import java.net.Socket;
import java.text.DecimalFormat;

public class HealthandInc {
    public int lga_code21;
    public String status;
    public String sex;
    public String income;
    public String condition;
    public int HealthindiCount21;
    public int HealthnonCount21;
    public int IncomeindiCount21;
    public int IncomenonCount21;
    public double score21;
    public int totalIndi21;
    public int totalNon21;
    
    public HealthandInc() {
    }

    public HealthandInc(int lga_code21, String status, String sex, String income, String condition, int HealthindiCount21, int HealthnonCount21, int IncomeindiCount21, int IncomenonCount21, double score21, int totalIndi21, int totalNon21) {
        this.lga_code21 = lga_code21;
        this.status = status;
        this.sex = sex;
        this.income = income;
        this.condition = condition;
        this.HealthindiCount21 = HealthindiCount21;
        this.HealthnonCount21 = HealthnonCount21;
        this.IncomeindiCount21 = IncomeindiCount21;
        this.IncomenonCount21 = IncomenonCount21;
        this.score21 = score21;
        this.totalIndi21 = totalIndi21;
        this.totalNon21 = totalNon21;
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
    public String getCondition() {
        return condition;
    }
    public int getHealthIndi() {
        return HealthindiCount21;
    }
    public int getHealthNon() {
        return HealthnonCount21;
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
    public double getScore21() {
        return score21;
    }
}