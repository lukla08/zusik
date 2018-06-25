package com.example.jac.place.backend.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "settings")
public class Settings {

    public static final long PREDEFINED_ID = 1;

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id = PREDEFINED_ID;


    @ColumnInfo(name = "working_days")
    private int workingDays;

    public long getId() {
        return PREDEFINED_ID;
    }

    public void setId(long id) {
        this.id = PREDEFINED_ID;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }
}

