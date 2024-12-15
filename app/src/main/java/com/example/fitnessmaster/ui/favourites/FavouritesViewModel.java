package com.example.fitnessmaster.ui.favourites;

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

public class FavouritesViewModel extends ViewModel {

    private final ExerciseDao exerciseDao;
    private final ExecutorService executorService;

    private final MutableLiveData<List<ExerciseItem>> allLikedExerciseItems = new MutableLiveData<>();
    private final MutableLiveData<String> _category = new MutableLiveData<>(Constants.SLUG_ALL);

    public FavouritesViewModel(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void setCategory(String category) {
        executorService.execute(() -> {
            _category.postValue(category);
            fetchFavouriteExerciseItemsByBodyPart(category);
        });
    }

    void fetchFavouriteExerciseItemsByBodyPart(String bodyPart) {
        if (Objects.equals(bodyPart, Constants.SLUG_ALL)) {
            fetchAllLikedExerciseItem();
        } else {
            executorService.execute(() -> {
                List<ExerciseItem> exerciseItems = exerciseDao.getFavouriteExercisesByBodyPart(bodyPart);
                allLikedExerciseItems.postValue(exerciseItems);
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
            boolean isLiked = item.getIsFavourite();
            exerciseDao.updateSavedStatus(item.getId(), !isLiked);
            fetchFavouriteExerciseItemsByBodyPart(_category.getValue());
        });
    }

    public LiveData<List<ExerciseItem>> getAllLikedExerciseItems() {
        return allLikedExerciseItems;
    }
}