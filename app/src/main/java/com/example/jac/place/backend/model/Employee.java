package com.example.jac.place.backend.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate =  true)
    @ColumnInfo(name = "id_employee")
    private long employeeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "is_disabled")
    private int disabled;

    @ColumnInfo(name = "id_firm")
    private long firmId;


    @ColumnInfo(name = "illness_days")
    private int illnessDays;

    private double salary;

    @ColumnInfo(name = "avg12m_salary")
    private double avg12MSalary;


    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getFirmId() {
        return firmId;
    }

    public void setFirmId(long firmId) {
        this.firmId = firmId;
    }


    public int getIllnessDays() {
        return illnessDays;
    }

    public void setIllnessDays(int illnessDays) {
        this.illnessDays = illnessDays;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getAvg12MSalary() {
        return avg12MSalary;
    }

    public void setAvg12MSalary(double avg12MSalary) {
        this.avg12MSalary = avg12MSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return name;
    }
}
