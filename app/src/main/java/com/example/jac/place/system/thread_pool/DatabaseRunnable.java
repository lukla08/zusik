package com.example.jac.place.system.thread_pool;

import com.example.jac.place.backend.SalaryDatabase;

public interface DatabaseRunnable {
    void run(SalaryDatabase database);
}
