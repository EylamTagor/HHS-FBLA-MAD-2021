package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BusinessActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Dialog createJobOfferDialog;
    private FloatingActionButton createJobButton;
    private Button CSRReportLink, edit, delete, follow;
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
        delete = findViewById(R.id.business_delete_button);
        follow = findViewById(R.id.business_follow);
        createJobButton = findViewById(R.id.business_create_job);
        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.toObject(User.class).getMyBusinesses().contains(getIntent().getStringExtra("BUSINESS_ID"))) {
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                follow.setVisibility(View.GONE);
            } else {
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                follow.setVisibility(View.VISIBLE);
            }
        });

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(BusinessActivity.this, AddBusinessActivity.class);
            intent.putExtra("FROM_ACTIVITY", "BusinessActivity");
            intent.putExtra("BUSINESS_ID", getIntent().getStringExtra("BUSINESS_ID"));
            startActivity(intent);
        });

        delete.setOnClickListener(v -> {
            db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).delete();
            db.collection("users").document(fuser.getUid()).update("myBusinesses", FieldValue.arrayRemove(getIntent().getStringExtra("BUSINESS_ID")));

            Intent intent = new Intent(BusinessActivity.this, HomeActivity.class);
            intent.putExtra("fragmentToLoad", "MyProfileActivity");
            startActivity(intent);
        });

        //Set adapter to recycler view
        jobsRVAdapter = new JobsRVAdapter(jobOffers);
        jobsOfferView.setAdapter(jobsRVAdapter);

        //Will have to post
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        createJobOfferDialog = new Dialog(this);

        createJobButton.setOnClickListener(v -> {
            createJobOfferDialog.setContentView(R.layout.create_job_offer_dialog);

            TextInputEditText position = createJobOfferDialog.findViewById(R.id.create_job_offer_position);
            TextInputEditText link = createJobOfferDialog.findViewById(R.id.create_job_offer_link);
            TextInputEditText description = createJobOfferDialog.findViewById(R.id.create_job_offer_description);
            Button createJobOffer = createJobOfferDialog.findViewById(R.id.create_job_offer_post);


            Long tsLong = System.currentTimeMillis() / 1000;
            createJobOffer.setOnClickListener(view -> {
                JobOffer j = new JobOffer(
                        getIntent().getStringExtra("BUSINESS_ID"), position.getText().toString(),
                        link.getText().toString(), description.getText().toString(),
                        tsLong
                );

                db.collection("jobOffers").add(j)
                        .addOnSuccessListener(documentReference -> {
                            db.collection("users").document(fuser.getUid()).update("myPosts", FieldValue.arrayUnion(documentReference.getId()));
                            Toast.makeText(this, "JobOffer added.", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(documentReference -> Toast.makeText(this, "Invalid Job Offer. If this is a mistake, report this as a bug.", Toast.LENGTH_SHORT).show());

                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                db.collection("jobOffers").document(fuser.getUid()).update("businessID", getIntent().getStringExtra("BUSINESS_ID"));
                db.collection("jobOffers").document(fuser.getUid()).update("jobTitle", position.getText().toString());
                db.collection("jobOffers").document(fuser.getUid()).update("jobDescription", description.getText().toString());
                db.collection("jobOffers").document(fuser.getUid()).update("link", link.getText().toString());
                createJobOfferDialog.dismiss();

            });
            createJobOfferDialog.show();
        });
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
