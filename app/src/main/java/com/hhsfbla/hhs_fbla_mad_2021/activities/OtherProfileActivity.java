package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hhsfbla.hhs_fbla_mad_2021.R;

// When someone else views your profile
public class OtherProfileActivity extends AppCompatActivity {
    /**
     * Sets the view to another profile
     * @param savedInstanceState the save state of the activity or page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
    }
}
