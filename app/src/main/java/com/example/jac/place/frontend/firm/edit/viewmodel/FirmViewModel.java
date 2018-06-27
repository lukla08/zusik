package com.example.jac.place.frontend.firm.edit.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jac.place.backend.model.Firm;

public class FirmViewModel extends AndroidViewModel {
    private final MutableLiveData<Firm> selected = new MutableLiveData<Firm>();

    public FirmViewModel(@NonNull Application application) {
        super(application);
    }

    public void selectFirm(Firm firm) {
        selected.setValue(firm);
    }

    public MutableLiveData<Firm> getSelected() {
        return selected;
    }
}
