package com.example.jac.place.backend.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey
    @ColumnInfo(name = "id_employee")
    private long employeeId;

    @ColumnInfo(name = "id_firm")
    private long firmId;

    @ColumnInfo(name = "is_owner")
    private boolean isOwner;

    @ColumnInfo(name = "working_days")
    private int workingDays;

    @ColumnInfo(name = "holiday_days")
    private int holidayDays;

    @ColumnInfo(name = "illness_days")
    private int illnessDays;

    private double salary;

    @ColumnInfo(name = "avg6m_salary")
    private double avg6MSalary;


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

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public int getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(int holidayDays) {
        this.holidayDays = holidayDays;
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

    public double getAvg6MSalary() {
        return avg6MSalary;
    }

    public void setAvg6MSalary(double avg6MSalary) {
        this.avg6MSalary = avg6MSalary;
    }
}
