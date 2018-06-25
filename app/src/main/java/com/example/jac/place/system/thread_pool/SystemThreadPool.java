package com.example.jac.place.system.thread_pool;

import android.content.Context;

import com.example.jac.place.backend.SalaryDatabase;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SystemThreadPool {
    private final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

    private static SystemThreadPool instance;
    public static synchronized SystemThreadPool getInstance() {
        if (instance == null)
            instance = new SystemThreadPool();



        instance = new SystemThreadPool();
        return instance;
    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    public void executeDBAction(Context context, DatabaseRunnable dbRunnable) {
        execute(() -> dbRunnable.run(SalaryDatabase.getInstance(context)));
    }
}
