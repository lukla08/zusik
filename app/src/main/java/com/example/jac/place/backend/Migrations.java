package com.example.jac.place.backend;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class Migrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE salary_items "
                    + " ADD COLUMN employee_careDays INTEGER not null default 0");
        }
    };

}


