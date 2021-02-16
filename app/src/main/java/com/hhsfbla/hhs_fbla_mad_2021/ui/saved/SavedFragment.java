package com.hhsfbla.hhs_fbla_mad_2021.ui.saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hhsfbla.hhs_fbla_mad_2021.R;

public class SavedFragment extends Fragment {

    private SavedViewModel savedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedViewModel =
                new ViewModelProvider(this).get(SavedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved, container, false);
        final TextView textView = root.findViewById(R.id.text_saved);
        savedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}