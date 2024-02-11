package com.datasciencestop.roomdbmvvmtodo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoApplication extends Application {
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
