package com.example.jac.place.backend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.Settings;

import java.util.List;

@Dao
public interface FirmsDao {
    @Query("select * from firm")
    LiveData<List<Firm>> getFirmsLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Firm ... firms);

    @Query("select * from firm where firm_id = :id")
    Firm getSelectedFirm(int id);
}
