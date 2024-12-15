package com.example.fitnessmaster.repo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fitnessmaster.Constants;
import com.example.fitnessmaster.models.ExerciseItem;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM " + Constants.DB_NAME)
    List<ExerciseItem> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ExerciseItem item);

    @Delete
    void delete(ExerciseItem item);

    @Query("SELECT * FROM " + Constants.DB_NAME + " WHERE body_part = :bodyPart")
    List<ExerciseItem> getExercisesByBodyPart(String bodyPart);

    @Query("SELECT * FROM " + Constants.DB_NAME + " WHERE is_favourite = 1 AND body_part = :bodyPart")
    List<ExerciseItem> getFavouriteExercisesByBodyPart(String bodyPart);

    @Query("UPDATE " + Constants.DB_NAME + " SET is_favourite = :isSaved WHERE id = :id")
    void updateSavedStatus(String id, boolean isSaved);

    @Query("SELECT * FROM " + Constants.DB_NAME + " WHERE is_favourite = 1")
    List<ExerciseItem> getSavedNews();
}
