package com.example.fitnessmaster.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fitnessmaster.MyApp;
import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.FragmentSearchBinding;
import com.example.fitnessmaster.models.ExerciseItem;
import com.example.fitnessmaster.repo.ExerciseDao;
import com.example.fitnessmaster.ui.ExerciseAdapter;
import com.example.fitnessmaster.ui.home.HomeViewModel;
import com.example.fitnessmaster.ui.home.HomeViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentSearchBinding binding;
    private List<ExerciseItem> list = new ArrayList<>();
    private ExerciseAdapter exerciseAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExerciseDao newsDao = ((MyApp) requireActivity().getApplication()).getDatabase().exerciseDao();
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(newsDao))
                .get(HomeViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        binding.searchView.clearFocus();

        homeViewModel.fetchAllExerciseItems();
        binding.exerciseRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        exerciseAdapter = new ExerciseAdapter(requireActivity(), new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExerciseItem item, int position) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                Bundle bundle = new Bundle();
                bundle.putString("name", item.getName());
                bundle.putString("equipment", item.getEquipment());
                bundle.putString("bodyPart", item.getBodyPart());
                bundle.putInt("imageUrl", item.getGifUrl());
                bundle.putString("info", item.getInstructions().toString().replace("[", "").replace("]", ""));
                bundle.putString("target", item.getTarget());
                bundle.putString("secondary_muscle", item.getSecondaryMuscles().toString().replace("[", "").replace("]", ""));
                navController.navigate(R.id.action_navigation_dashboard_to_exerciseFragment, bundle);
            }

            @Override
            public void onFavouriteClick(ExerciseItem item, int position) {
                homeViewModel.likeExerciseItem(item);
            }

            @Override
            public void onShareItem(ExerciseItem item, int position) {
                String description = item.getInstructions().toString().replace("[", "").replace("]", "");
                if (description.length() > 50) {
                    description = description.substring(0, 50) + "...";
                }
                String shareText = "Check out this exercise:\n\n"
                        + "Name: " + item.getName() + "\n\n"
                        + description + "\n\n"
                        + "Watch this: " + item.getGifUrl();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, "Share Exercise via"));
            }
        });
        binding.exerciseRv.setAdapter(exerciseAdapter);
        homeViewModel.getAllExerciseItems().observe(getViewLifecycleOwner(), exerciseItems -> {
            list = exerciseItems;
            exerciseAdapter.setExerciseItems(exerciseItems);
            if (exerciseItems.isEmpty()) {
                binding.emptyLottie.setVisibility(View.VISIBLE);
                binding.exerciseRv.setVisibility(View.GONE);
            } else {
                binding.emptyLottie.setVisibility(View.GONE);
                binding.exerciseRv.setVisibility(View.VISIBLE);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterExercise(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    private void filterExercise(String query) {
        ArrayList<ExerciseItem> filteredList = new ArrayList<>();
        for (ExerciseItem item : list) {
            if (item.getName().toLowerCase().contains(query)) {
                filteredList.add(item);
            }
        }
        exerciseAdapter.setExerciseItems(filteredList);
        if (filteredList.isEmpty()) {
            binding.emptyLottie.setVisibility(View.VISIBLE);
            binding.exerciseRv.setVisibility(View.GONE);
        } else {
            binding.emptyLottie.setVisibility(View.GONE);
            binding.exerciseRv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}