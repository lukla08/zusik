package com.example.jac.place.backend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.SalaryItems;

import java.util.List;

@Dao
public interface SalaryItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(SalaryItems ... items);

    @Query("select * from salary_items where id_employee = :id_employee")
    SalaryItems getSalaryItems4Employee(long id_employee);


}

