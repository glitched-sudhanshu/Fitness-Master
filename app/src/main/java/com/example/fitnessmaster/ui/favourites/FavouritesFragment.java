package com.example.fitnessmaster.ui.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessmaster.MyApp;
import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.FragmentFavouritesBinding;
import com.example.fitnessmaster.models.ExerciseItem;
import com.example.fitnessmaster.repo.ExerciseDao;
import com.example.fitnessmaster.ui.CategoryAdapter;
import com.example.fitnessmaster.ui.ExerciseAdapter;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;
    private FavouritesViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExerciseDao newsDao = ((MyApp) requireActivity().getApplication()).getDatabase().exerciseDao();
        homeViewModel = new ViewModelProvider(this, new FavouriteViewModelFactory(newsDao))
                .get(FavouritesViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.categoryRv;
        homeViewModel.fetchAllLikedExerciseItem();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter((item, position) -> {
            homeViewModel.setCategory(item.getSlug());
        });
        binding.exerciseRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(requireActivity(), new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExerciseItem item, int position) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                Bundle bundle = new Bundle();
                bundle.putString("name", item.getName());
                bundle.putString("equipment", item.getEquipment());
                bundle.putString("bodyPart", item.getBodyPart());
                bundle.putInt("imageUrl", item.getGifUrl());
                bundle.putString("info", item.getInstructions().toString());
                bundle.putString("target", item.getTarget());
                bundle.putString("secondary_muscle", item.getSecondaryMuscles().toString());
                navController.navigate(R.id.action_navigation_notifications_to_exerciseFragment, bundle);
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
        homeViewModel.getAllLikedExerciseItems().observe(getViewLifecycleOwner(), exerciseItems -> {
            exerciseAdapter.setExerciseItems(exerciseItems);
            if (exerciseItems.isEmpty()) {
                binding.emptyLottie.setVisibility(View.VISIBLE);
                binding.exerciseRv.setVisibility(View.GONE);
            } else {
                binding.emptyLottie.setVisibility(View.GONE);
                binding.exerciseRv.setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setAdapter(categoryAdapter);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}