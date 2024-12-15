package com.example.fitnessmaster.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessmaster.Constants;
import com.example.fitnessmaster.models.ExerciseItem;
import com.example.fitnessmaster.repo.ExerciseDao;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {

    private final ExerciseDao exerciseDao;
    private final ExecutorService executorService;

    private final MutableLiveData<List<ExerciseItem>> allExerciseItems = new MutableLiveData<>();
    private final MutableLiveData<List<ExerciseItem>> allLikedExerciseItems = new MutableLiveData<>();
    private final MutableLiveData<String> _category = new MutableLiveData<>(Constants.SLUG_ALL);

    public HomeViewModel(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void setCategory(String category) {
        executorService.execute(() -> {
            _category.postValue(category);
            fetchExerciseItemsByBodyPart(category);
            fetchFavouriteExerciseItemsByBodyPart(category);
        });
    }

    public void insertExerciseItem(ExerciseItem newsItem) {
        executorService.execute(() -> {
            Log.d("insertData", "vm method: title: " + newsItem.getName());
            exerciseDao.insert(newsItem); // Insert the item
            fetchAllExerciseItems();
        });
    }

    public void fetchAllExerciseItems() {
        executorService.execute(() -> {
            List<ExerciseItem> exerciseItems = exerciseDao.getAll();
            allExerciseItems.postValue(exerciseItems);
            fetchAllLikedExerciseItem();
        });
    }

    void fetchExerciseItemsByBodyPart(String bodyPart) {
        if (Objects.equals(bodyPart, Constants.SLUG_ALL)) {
            fetchAllExerciseItems();
        } else {
            executorService.execute(() -> {
                List<ExerciseItem> exerciseItems = exerciseDao.getExercisesByBodyPart(bodyPart);
                allExerciseItems.postValue(exerciseItems);
                fetchAllLikedExerciseItem();
            });
        }
    }

    // Method to fetch all news items
    void fetchFavouriteExerciseItemsByBodyPart(String bodyPart) {
        if (Objects.equals(bodyPart, Constants.SLUG_ALL)) {
            fetchAllLikedExerciseItem();
        } else {
            executorService.execute(() -> {
                List<ExerciseItem> exerciseItems = exerciseDao.getFavouriteExercisesByBodyPart(bodyPart);
                allLikedExerciseItems.postValue(exerciseItems);
                fetchAllLikedExerciseItem();
            });
        }
    }

    public void fetchAllLikedExerciseItem() {
        executorService.execute(() -> {
            List<ExerciseItem> exerciseItems = exerciseDao.getSavedNews();
            allLikedExerciseItems.postValue(exerciseItems);
        });
    }

    public void likeExerciseItem(ExerciseItem item) {
        executorService.execute(() -> {
            Log.d("saveItem", "likeExerciseItem: like " + item.getIsFavourite());
            boolean isLiked = item.getIsFavourite();
            exerciseDao.updateSavedStatus(item.getId(), !isLiked);
            fetchExerciseItemsByBodyPart(_category.getValue());
        });
    }

    // Expose LiveData to observe the list
    public LiveData<List<ExerciseItem>> getAllExerciseItems() {
        return allExerciseItems;
    }

    public LiveData<List<ExerciseItem>> getAllLikedExerciseItems() {
        return allLikedExerciseItems;
    }
}