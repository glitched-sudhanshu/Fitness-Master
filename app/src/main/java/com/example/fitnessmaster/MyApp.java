package com.example.fitnessmaster;


import android.app.Application;

import com.example.fitnessmaster.models.ExerciseItem;
import com.example.fitnessmaster.repo.ExerciseDao;
import com.example.fitnessmaster.repo.ExerciseDatabase;

public class MyApp extends Application {
    private static ExerciseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = ExerciseDatabase.getDatabase(this);
    }

    public ExerciseDatabase getDatabase() {
        return database;
    }
}

