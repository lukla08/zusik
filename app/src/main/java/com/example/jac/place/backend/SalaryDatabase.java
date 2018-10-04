package com.example.jac.place.backend;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jac.place.backend.dao.EmployeeDao;
import com.example.jac.place.backend.dao.FirmsDao;
import com.example.jac.place.backend.dao.SalaryItemsDao;
import com.example.jac.place.backend.dao.SettingsDao;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.SalaryItems;
import com.example.jac.place.backend.model.Settings;

@Database(entities = {Settings.class, Employee.class, Firm.class, SalaryItems.class},
        version = 2, exportSchema = false)
public abstract class SalaryDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "place13.gdb";
    private static volatile SalaryDatabase instance;

    public static synchronized SalaryDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    private static SalaryDatabase create(Context context) {
        return Room.databaseBuilder(
                context,
                SalaryDatabase.class,DATABASE_NAME)
                .addMigrations(Migrations.MIGRATION_1_2)
                .build();
    }

    public abstract SettingsDao settingsDao();
    public abstract FirmsDao firmsDao();
    public abstract EmployeeDao employeeDao();
    public abstract SalaryItemsDao salaryItemsDao();
}


