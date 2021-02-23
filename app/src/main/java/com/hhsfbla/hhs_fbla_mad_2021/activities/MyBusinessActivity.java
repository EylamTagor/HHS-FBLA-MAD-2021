package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search.SearchRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search.SearchRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;

import java.util.ArrayList;

public class MyBusinessActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Dialog postingDialog;
    private FloatingActionButton postButton;
    private Button CSRReportLink;
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
        setContentView(R.layout.activity_my_business);
        jobsOfferView = findViewById(R.id.my_business_job_openings);
        jobsOfferView.setLayoutManager(new NonScrollingLLM(this));
        website = findViewById(R.id.my_business_website);
        name = findViewById(R.id.my_business_name);
        about = findViewById(R.id.my_business_about);
        CSRVision = findViewById(R.id.my_business_csr_vision);
        ESGScore = findViewById(R.id.my_business_ESG_score);
        followerCount = findViewById(R.id.my_business_follower_count);
        CSRReportLink = findViewById(R.id.my_business_CSR_report_link);


        //Set adapter to recycler view
        jobsRVAdapter = new JobsRVAdapter(jobOffers);
        jobsOfferView.setAdapter(jobsRVAdapter);

        //Will have to post
    }





}
