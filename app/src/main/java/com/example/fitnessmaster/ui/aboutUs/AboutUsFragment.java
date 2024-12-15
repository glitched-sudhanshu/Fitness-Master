package com.example.fitnessmaster.ui.aboutUs;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutUsBinding binding = FragmentAboutUsBinding.inflate(inflater, container, false);
        binding.tvAboutUsText.setText(Html.fromHtml(getString(R.string.about_us_content), Html.FROM_HTML_MODE_COMPACT));
        return binding.getRoot();
    }
}
