package com.example.jac.place.backend.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "firm")
public class Firm {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "firm_id")
    private long firmId;

    @ColumnInfo(name = "firm_name")
    private String firmName;

    @ColumnInfo(name = "is_disabled")
    private int disabled;

    @ColumnInfo(name = "accidentRate")
    private double accidentRate;

    public long getFirmId() {
        return firmId;
    }

    public void setFirmId(long firmId) {
        this.firmId = firmId;
    }


    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public double getAccidentRate() {
        return accidentRate;
    }

    public void setAccidentRate(double accidentRate) {
        this.accidentRate = accidentRate;
    }

    @Override
    public String toString() {
        return firmName;
    }
}
