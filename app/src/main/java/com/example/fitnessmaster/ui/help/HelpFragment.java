package com.example.fitnessmaster.ui.help;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.FragmentHelpBinding;

public class HelpFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHelpBinding binding = FragmentHelpBinding.inflate(inflater, container, false);
        binding.tvHelpText.setText(Html.fromHtml(getString(R.string.help_us_text), Html.FROM_HTML_MODE_COMPACT));
        return binding.getRoot();
    }
}
