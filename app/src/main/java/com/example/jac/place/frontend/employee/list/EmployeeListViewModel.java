package com.example.jac.place.frontend.employee.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;

import java.util.List;

public class EmployeeListViewModel extends AndroidViewModel {


    public EmployeeListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Employee>> getEmployeesByFirm(long idFirm) {
        return SalaryDatabase.getInstance(getApplication()).employeeDao().getEmployeesByFirmLiveData(idFirm);
    }


}
