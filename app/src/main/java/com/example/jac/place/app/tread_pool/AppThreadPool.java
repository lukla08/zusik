package com.example.jac.place.app.tread_pool;

import android.content.Context;

import com.example.jac.place.app.tread_pool.helper.DatabaseRunnable;
import com.example.jac.place.backend.SalaryDatabase;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppThreadPool {
    private final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

    private static AppThreadPool instance;
    public static synchronized AppThreadPool getInstance() {
        if (instance == null)
            instance = new AppThreadPool();


        instance = new AppThreadPool();
        return instance;
    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

}
