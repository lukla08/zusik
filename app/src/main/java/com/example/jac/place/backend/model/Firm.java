package com.example.jac.place.backend.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "firm")
public class Firm {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "firm_id")
    private long firmId;

    @ColumnInfo(name = "firm_code")
    private String firmCode;

    @ColumnInfo(name = "firm_name")
    private String firmName;

    public long getFirmId() {
        return firmId;
    }

    public void setFirmId(long firmId) {
        this.firmId = firmId;
    }

    public String getFirmCode() {
        return firmCode;
    }

    public void setFirmCode(String firmCode) {
        this.firmCode = firmCode;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }
}
