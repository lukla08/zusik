package com.example.jac.place.backend.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jac.place.backend.model.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Settings settings);

    @Query("select * from settings")
    List<Settings> getAllSettings();

}
