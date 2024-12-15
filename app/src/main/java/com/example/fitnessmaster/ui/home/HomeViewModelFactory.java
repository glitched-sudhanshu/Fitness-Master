package com.example.fitnessmaster.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessmaster.models.ExerciseItem;
import com.example.fitnessmaster.repo.ExerciseDao;

public class HomeViewModelFactory implements ViewModelProvider.Factory {

    private final ExerciseDao exerciseDao;

    public HomeViewModelFactory(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(exerciseDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
