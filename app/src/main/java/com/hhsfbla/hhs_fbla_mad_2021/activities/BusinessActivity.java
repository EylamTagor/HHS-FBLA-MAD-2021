package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;

import java.util.ArrayList;

public class BusinessActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Dialog postingDialog;
    private FloatingActionButton postButton;
    private Button CSRReportLink, edit;
    private TextView name, website, about, CSRVision, followerCount, ESGScore;
    private RecyclerView jobsOfferView;
    private ArrayList<JobsRVModel> jobOffers = new ArrayList<>();
    private JobsRVAdapter jobsRVAdapter;

    /**
     * Creates the page and initializes all page components, such as textviews, image views, buttons, and dialogs,
     *
     * @param savedInstanceState the save state of the activity or page
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        jobsOfferView = findViewById(R.id.business_job_openings);
        jobsOfferView.setLayoutManager(new NonScrollingLLM(this));
        website = findViewById(R.id.business_website);
        name = findViewById(R.id.business_name);
        about = findViewById(R.id.business_about);
        CSRVision = findViewById(R.id.business_csr_vision);
        ESGScore = findViewById(R.id.business_ESG_score);
        followerCount = findViewById(R.id.business_follower_count);
        CSRReportLink = findViewById(R.id.business_CSR_report_link);
        edit = findViewById(R.id.business_edit);

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(BusinessActivity.this, AddBusinessActivity.class);
            intent.putExtra("FROM_ACTIVITY", "BusinessActivity");
            intent.putExtra("BUSINESS_ID", getIntent().getStringExtra("BUSINESS_ID"));
            startActivity(intent);
        });

        //Set adapter to recycler view
        jobsRVAdapter = new JobsRVAdapter(jobOffers);
        jobsOfferView.setAdapter(jobsRVAdapter);

        //Will have to post
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("FROM_ACTIVITY") != null & getIntent().getStringExtra("FROM_ACTIVITY").equals("AddBusinessActivity")) {
            Intent intent = new Intent(BusinessActivity.this, HomeActivity.class);
            intent.putExtra("fragmentToLoad", "MyProfileActivity");
            startActivity(intent);
        } else
            super.onBackPressed();
    }
}
