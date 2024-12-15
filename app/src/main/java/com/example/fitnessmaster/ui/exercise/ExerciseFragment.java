package com.example.fitnessmaster.ui.exercise;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.FragmentExerciseBinding;

public class ExerciseFragment extends Fragment {
    private FragmentExerciseBinding binding;
    private String name;
    private String bodyPart;
    private String target;
    private String secondaryMuscle;
    private String equipment;
    private int imageUrl;
    private String instruction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        equipment = getArguments().getString("equipment");
        bodyPart = getArguments().getString("bodyPart");
        imageUrl = getArguments().getInt("imageUrl");
        instruction = getArguments().getString("info");
        target = getArguments().getString("target");
        secondaryMuscle = getArguments().getString("secondary_muscle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        binding.tvName.setText(name);
        binding.tvBodyPart.setText(bodyPart);
        binding.tvTarget.setText(target);
        binding.tvEquipment.setText(equipment);
        binding.tvSecondaryMuscle.setText(secondaryMuscle);
        binding.tvInstructions.setText(instruction);
        Glide.with(requireActivity())
                .asGif()
                .load(imageUrl)
                .placeholder(R.drawable.ic_gym)
                .centerCrop()
                .into(binding.ivExercise);
        return binding.getRoot();
    }
}
