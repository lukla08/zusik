package com.example.jac.place.frontend.firm.list.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.jac.place.app.tread_pool.BackgroundExecutor;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Firm;

import java.util.List;

public class FirmListViewModel extends AndroidViewModel {


    public FirmListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Firm>> getFirms() {

        return SalaryDatabase.getInstance(getApplication()).firmsDao().getFirmsLiveData();

    }
}
