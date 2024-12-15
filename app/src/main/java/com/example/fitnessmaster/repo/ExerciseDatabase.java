package com.example.fitnessmaster.repo;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.fitnessmaster.models.ExerciseItem;

@Database(entities = {ExerciseItem.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class ExerciseDatabase extends RoomDatabase {

    // Abstract method to provide DAO
    public abstract ExerciseDao exerciseDao();

    // Singleton instance
    private static volatile ExerciseDatabase INSTANCE;

    // Method to get the database instance
    public static ExerciseDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExerciseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ExerciseDatabase.class,
                            "memo_diary_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
