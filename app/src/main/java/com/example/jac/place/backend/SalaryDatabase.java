package com.example.jac.place.backend;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jac.place.backend.dao.FirmsDao;
import com.example.jac.place.backend.dao.SettingsDao;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.Settings;

@Database(entities = {Settings.class, Employee.class, Firm.class}, version = 1, exportSchema = false)
public abstract class SalaryDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "place.gdb";
    private static volatile SalaryDatabase instance;

    public static synchronized SalaryDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    private static SalaryDatabase create(Context context) {
        return Room.databaseBuilder(
                context,
                SalaryDatabase.class,
                DATABASE_NAME).build();
    }

    public abstract SettingsDao settingsDao();
    public abstract FirmsDao firmsDao();
}