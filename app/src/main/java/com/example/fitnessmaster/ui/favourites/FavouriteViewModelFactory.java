package com.example.fitnessmaster.ui.favourites;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessmaster.repo.ExerciseDao;
import com.example.fitnessmaster.ui.home.HomeViewModel;

public class FavouriteViewModelFactory implements ViewModelProvider.Factory {

    private final ExerciseDao exerciseDao;

    public FavouriteViewModelFactory(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavouritesViewModel.class)) {
            return (T) new FavouritesViewModel(exerciseDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
