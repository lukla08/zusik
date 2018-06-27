package com.example.jac.place.frontend.base;

import android.support.v7.app.AppCompatActivity;

import com.example.jac.place.app.tread_pool.BackgroundExecutor;

public class BaseAppCompatActivity extends AppCompatActivity {
    private BackgroundExecutor backgroundExecutor;
    protected synchronized BackgroundExecutor getBackgroundExecutor() {
        if (backgroundExecutor == null)
            backgroundExecutor = new BackgroundExecutor(this);
        return backgroundExecutor;
    }
}
