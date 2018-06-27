package com.example.jac.place.app.tread_pool;

import android.content.Context;

import com.example.jac.place.app.tread_pool.helper.DatabaseRunnable;
import com.example.jac.place.backend.SalaryDatabase;

public class BackgroundExecutor {
    private final Context context;

    public BackgroundExecutor(Context context) {
        this.context = context;
    }

    public void execute(Runnable runnable) {
        AppThreadPool.getInstance().execute(runnable);
    }

    public void execute(DatabaseRunnable dbRunnable) {
        Runnable runnable = () -> dbRunnable.run(SalaryDatabase.getInstance(context));
        execute(runnable);
    }
}
