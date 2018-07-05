package com.example.jac.place.backend.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "salary_items")
public class SalaryItems {

    @PrimaryKey
    @ColumnInfo(name =  "id_employee")
    private long employeeId;

    private int editable;

    private double employee_salary;
    private double employee_salary12m;
    private boolean employee_isOwner;
    private int employee_illnessDays;

    private double calc_costsOfObtaining = 111.25; // kosztyUzyskania
    private double calc_salaray12m_netto;   // przecietne wynagrodzenie za ostatnie 12 mies bez skladek
    private double calc_ilnessDailyRate;    // dzienna stawka chorobowa
    private double calc_salaryIllnessPart;   // wynagrodzenie za czas choroby

    private double calc_normalDailyRate;    // dzienna stawka przepracowane
    private double calc_salaryNormalOnIllness; // normalne wynagrodzenie dotyczące chorobowego
    private double calc_salaryNormalPart; // wynagrodzenie za przepracowany czas
    private double calc_salaryTotal; // całkowite wynagrodzenie

    private double calc_base4SocialTaxes; // podstawa na spoleczne
    private double calc_employeeSocialPension; // wyliczona skladka pracowonika: emerytalne
    private double calc_employeeSocialRent; // wyliczona skladka pracowonika: rentowe
    private double calc_employeeSocialIllness; // wyliczona skladka pracowonika: chorobowe
    private double calc_employeeSocialTotal; // suma skladek spolecznych pracownika

    private double calc_base4healthTax ;// podstawa  na ubezpieczenie zdrowotne
    private double calc_healthTaxToTake ;// skladka zdrowotna do pobrania
    private double calc_healthTaxToDeduct ;// skladka zdrowotna do odliczenia

    private long calc_base4IncomeTax; // podstawa opodatkowania
    private double calc_advance4IncomeTaxBrutto; // zaliczka na dochodowy brutto
    private long calc_advance4IncomeTax; // zaliczka na dochodowy

    private double calc_AmountDue; // kwota do wyplaty

    private double calc_BossSocialPension; // emerytalna płatna przez pracodawce
    private double calc_BossSocialRent; // rentowa płatna przez pracodawce
    private double calc_BossSocialAccident; // wypadkowa płatna przez pracodawce
    private double calc_BossFP; // FP płatna przez pracodawce
    private double calc_BossFGSP; // FP płatna przez pracodawce

    private double calc_TotalCost; // koszt całkowity

    public double getCalc_TotalCost() {
        return calc_TotalCost;
    }

    public void setCalc_TotalCost(double calc_TotalCost) {
        this.calc_TotalCost = calc_TotalCost;
    }

    public void setCalc_costsOfObtaining(double calc_costsOfObtaining) {
        this.calc_costsOfObtaining = calc_costsOfObtaining;
    }

    public double getCalc_BossSocialPension() {
        return calc_BossSocialPension;
    }

    public void setCalc_BossSocialPension(double calc_BossSocialPension) {
        this.calc_BossSocialPension = calc_BossSocialPension;
    }

    public double getCalc_BossSocialRent() {
        return calc_BossSocialRent;
    }

    public void setCalc_BossSocialRent(double calc_BossSocialRent) {
        this.calc_BossSocialRent = calc_BossSocialRent;
    }

    public double getCalc_BossSocialAccident() {
        return calc_BossSocialAccident;
    }

    public void setCalc_BossSocialAccident(double calc_BossSocialAccident) {
        this.calc_BossSocialAccident = calc_BossSocialAccident;
    }

    public double getCalc_BossFP() {
        return calc_BossFP;
    }

    public void setCalc_BossFP(double calc_BossFP) {
        this.calc_BossFP = calc_BossFP;
    }

    public double getCalc_BossFGSP() {
        return calc_BossFGSP;
    }

    public void setCalc_BossFGSP(double calc_BossFGSP) {
        this.calc_BossFGSP = calc_BossFGSP;
    }

    public double getEmployee_salary() {
        return employee_salary;
    }

    public void setEmployee_salary(double employee_salary) {
        this.employee_salary = employee_salary;
    }

    public double getEmployee_salary12m() {
        return employee_salary12m;
    }

    public void setEmployee_salary12m(double employee_salary12m) {
        this.employee_salary12m = employee_salary12m;
    }

    public boolean isEmployee_isOwner() {
        return employee_isOwner;
    }

    public void setEmployee_isOwner(boolean employee_isOwner) {
        this.employee_isOwner = employee_isOwner;
    }

    public int getEmployee_illnessDays() {
        return employee_illnessDays;
    }

    public void setEmployee_illnessDays(int employee_illnessDays) {
        this.employee_illnessDays = employee_illnessDays;
    }

    public double getCalc_salaray12m_netto() {
        return calc_salaray12m_netto;
    }

    public void setCalc_salaray12m_netto(double calc_salaray12m_netto) {
        this.calc_salaray12m_netto = calc_salaray12m_netto;
    }

    public double getCalc_ilnessDailyRate() {
        return calc_ilnessDailyRate;
    }

    public void setCalc_ilnessDailyRate(double calc_ilnessDailyRate) {
        this.calc_ilnessDailyRate = calc_ilnessDailyRate;
    }

    public double getCalc_salaryIllnessPart() {
        return calc_salaryIllnessPart;
    }

    public void setCalc_salaryIllnessPart(double calc_salaryIllnessPart) {
        this.calc_salaryIllnessPart = calc_salaryIllnessPart;
    }

    public double getCalc_normalDailyRate() {
        return calc_normalDailyRate;
    }

    public void setCalc_normalDailyRate(double calc_normalDailyRate) {
        this.calc_normalDailyRate = calc_normalDailyRate;
    }

    public double getCalc_salaryNormalOnIllness() {
        return calc_salaryNormalOnIllness;
    }

    public void setCalc_salaryNormalOnIllness(double calc_salaryNormalOnIllness) {
        this.calc_salaryNormalOnIllness = calc_salaryNormalOnIllness;
    }

    public double getCalc_salaryNormalPart() {
        return calc_salaryNormalPart;
    }

    public void setCalc_salaryNormalPart(double calc_salaryNormalPart) {
        this.calc_salaryNormalPart = calc_salaryNormalPart;
    }

    public double getCalc_salaryTotal() {
        return calc_salaryTotal;
    }

    public void setCalc_salaryTotal(double calc_salaryTotal) {
        this.calc_salaryTotal = calc_salaryTotal;
    }

    public double getCalc_base4SocialTaxes() {
        return calc_base4SocialTaxes;
    }

    public void setCalc_base4SocialTaxes(double calc_base4SocialTaxes) {
        this.calc_base4SocialTaxes = calc_base4SocialTaxes;
    }

    public double getCalc_employeeSocialPension() {
        return calc_employeeSocialPension;
    }

    public void setCalc_employeeSocialPension(double calc_employeeSocialPension) {
        this.calc_employeeSocialPension = calc_employeeSocialPension;
    }

    public double getCalc_employeeSocialRent() {
        return calc_employeeSocialRent;
    }

    public void setCalc_employeeSocialRent(double calc_employeeSocialRent) {
        this.calc_employeeSocialRent = calc_employeeSocialRent;
    }

    public double getCalc_employeeSocialIllness() {
        return calc_employeeSocialIllness;
    }

    public void setCalc_employeeSocialIllness(double calc_employeeSocialIllness) {
        this.calc_employeeSocialIllness = calc_employeeSocialIllness;
    }

    public double getCalc_employeeSocialTotal() {
        return calc_employeeSocialTotal;
    }

    public void setCalc_employeeSocialTotal(double calc_employeeSocialTotal) {
        this.calc_employeeSocialTotal = calc_employeeSocialTotal;
    }

    public double getCalc_base4healthTax() {
        return calc_base4healthTax;
    }

    public void setCalc_base4healthTax(double calc_base4healthTax) {
        this.calc_base4healthTax = calc_base4healthTax;
    }

    public double getCalc_healthTaxToTake() {
        return calc_healthTaxToTake;
    }

    public void setCalc_healthTaxToTake(double calc_healthTaxToTake) {
        this.calc_healthTaxToTake = calc_healthTaxToTake;
    }

    public double getCalc_healthTaxToDeduct() {
        return calc_healthTaxToDeduct;
    }

    public void setCalc_healthTaxToDeduct(double calc_healthTaxToDeduct) {
        this.calc_healthTaxToDeduct = calc_healthTaxToDeduct;
    }

    public long getCalc_base4IncomeTax() {
        return calc_base4IncomeTax;
    }

    public void setCalc_base4IncomeTax(long calc_base4IncomeTax) {
        this.calc_base4IncomeTax = calc_base4IncomeTax;
    }

    public double getCalc_advance4IncomeTaxBrutto() {
        return calc_advance4IncomeTaxBrutto;
    }

    public void setCalc_advance4IncomeTaxBrutto(double calc_advance4IncomeTaxBrutto) {
        this.calc_advance4IncomeTaxBrutto = calc_advance4IncomeTaxBrutto;
    }

    public long getCalc_advance4IncomeTax() {
        return calc_advance4IncomeTax;
    }

    public void setCalc_advance4IncomeTax(long calc_advance4IncomeTax) {
        this.calc_advance4IncomeTax = calc_advance4IncomeTax;
    }

    public double getCalc_AmountDue() {
        return calc_AmountDue;
    }

    public void setCalc_AmountDue(double calc_AmountDue) {
        this.calc_AmountDue = calc_AmountDue;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public double getCalc_costsOfObtaining() {
        return calc_costsOfObtaining;
    }

    public int getEditable() {
        return editable;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }

    @Override
    public String toString() {
        return
                "\nemployee_salary :" + employee_salary + "\n"
               + "employee_salary12m : " + employee_salary12m  + "\n"
               + "employee_illnessDays : " + employee_illnessDays  + "\n"
               + "calc_salaray12m_netto : " + calc_salaray12m_netto  + "\n"
               + "calc_ilnessDailyRate : " + calc_ilnessDailyRate  + "\n"
               + "calc_salaryIllnessPart : " + calc_salaryIllnessPart  + "\n"
               + "calc_salaryNormalOnIllness : " + calc_salaryNormalOnIllness  + "\n"
               + "calc_salaryNormalPart : " + calc_salaryNormalPart  + "\n"
               + "calc_salaryTotal : " + calc_salaryTotal  + "\n"
               + "calc_base4SocialTaxes : " + calc_base4SocialTaxes  + "\n"
               + "calc_employeeSocialTotal : " + calc_employeeSocialTotal  + "\n"
               + "calc_base4healthTax : " + calc_base4healthTax  + "\n"
               + "calc_healthTaxToTake : " + calc_healthTaxToTake  + "\n"
               + "calc_healthTaxToDeduct : " + calc_healthTaxToDeduct  + "\n"
               + "calc_base4IncomeTax : " + calc_base4IncomeTax  + "\n"
               + "calc_advance4IncomeTaxBrutto : " + calc_advance4IncomeTaxBrutto  + "\n"
               + "calc_advance4IncomeTax : " + calc_advance4IncomeTax  + "\n"
               + "\n\ncalc_AmountDue : " + calc_AmountDue;
    }
}

//
//
//    private double calc_base4SocialTaxes; // podstawa na spoleczne
//    private double calc_employeeSocialPension; // wyliczona skladka pracowonika: emerytalne
//    private double calc_employeeSocialRent; // wyliczona skladka pracowonika: rentowe
//    private double calc_employeeSocialIllness; // wyliczona skladka pracowonika: chorobowe
//    private double calc_employeeSocialTotal; // suma skladek spolecznych pracownika
//
//    private double calc_base4healthTax ;// podstawa  na ubezpieczenie zdrowotne
//    private double calc_healthTaxToTake ;// skladka zdrowotna do pobrania
//    private double calc_healthTaxToDeduct ;// skladka zdrowotna do odliczenia
//
//    private long calc_base4IncomeTax; // podstawa opodatkowania
//    private double calc_advance4IncomeTaxBrutto; // zaliczka na dochodowy brutto
//    private double calc_advance4IncomeTax; // zaliczka na dochodowy
//
//    private double calc_AmountDue; // kwota do wyplaty
//
