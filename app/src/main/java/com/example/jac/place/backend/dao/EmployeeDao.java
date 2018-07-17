package com.example.jac.place.backend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("select * from employee order by name")
    LiveData<List<Employee>> getEmployeesLiveData();

    @Query("select * from employee where id_firm = :idFirm order by name")
    LiveData<List<Employee>> getEmployeesByFirmLiveData(long idFirm);

    @Query("select * from employee where id_firm = :idFirm and is_disabled = 0 order by name")
    List<Employee> getActiveEmployeesByFirm(long idFirm);

    @Query("Select count(*) from employee order by name")
    int getRecordCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Employee... employees);

    @Query("select * from employee where id_employee = :id")
    Employee getSelectedEmployee(long id);

    @Query("select * from employee where id_firm =:idFirm and name > :currentName and is_disabled = 0 order by name limit 1")
    Employee getNextActiveEmployee(long idFirm, String currentName);

    @Query("select * from employee where id_firm =:idFirm and name < :currentName and is_disabled = 0 order by name desc limit 1")
    Employee getPrevActiveEmployee(long idFirm, String currentName);

}
