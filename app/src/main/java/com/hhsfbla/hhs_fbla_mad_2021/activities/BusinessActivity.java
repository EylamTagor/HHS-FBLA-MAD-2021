package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Dialog createJobOfferDialog;
    private CircleImageView logo;
    private FloatingActionButton createJobButton;
    private Button edit, delete;
    private TextView name, website, about, CSRVision, CSRReportLink, followerCount, ESGScore;
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
        CSRReportLink = findViewById(R.id.business_CSR_report_link);
        edit = findViewById(R.id.business_edit);
        delete = findViewById(R.id.business_delete_button);
        createJobButton = findViewById(R.id.business_create_job);
        logo = findViewById(R.id.business_logo);
        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).get().addOnSuccessListener(documentSnapshot1 -> {
                Business biz = documentSnapshot1.toObject(Business.class);
                website.setText(biz.getWebsite());
                name.setText(biz.getName());
                about.setText(biz.getAbout());
                CSRVision.setText(biz.getCSRVision());

                if (biz.getESGScore() == -1.0)
                    ESGScore.setText("N/A");
                else
                    ESGScore.setText("" + biz.getESGScore());


                if (biz != null) {
                    if (biz.getLogo() != null && !biz.getLogo().equalsIgnoreCase("")) {
                        Picasso.get().load(Uri.parse(biz.getLogo())).into(logo);
                    }
                    else {
                        logo.setImageResource(R.drawable.ic_no_business_logo);
                    }
                }

                if (biz.getCSRLink().equals("")) {
                    CSRReportLink.setText("CSR Report Unavailable");
                } else {
                    CSRReportLink.setText(Html.fromHtml("<a href='" + biz.getCSRLink() + "'>CSR Report Link</androidx.constraintlayout.widget.ConstraintLayout</a>"));
                    CSRReportLink.setMovementMethod(LinkMovementMethod.getInstance());
                }

                db.collection("jobOffers").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult())
                            if (document.toObject(JobOffer.class).getBusinessID().equals(getIntent().getStringExtra("BUSINESS_ID")))
                                jobOffers.add(new JobsRVModel(document.toObject(JobOffer.class)));
                        jobsRVAdapter = new JobsRVAdapter(jobOffers);
                        jobsOfferView.setAdapter(jobsRVAdapter);
                    }
                });

            });
        });

        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.toObject(User.class).getMyBusinesses().contains(getIntent().getStringExtra("BUSINESS_ID"))) {
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            } else {
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
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

                db.collection("jobOffers").add(j).addOnSuccessListener(documentReference -> {
                    db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("jobOffers", FieldValue.arrayUnion(documentReference.getId()));
                    Toast.makeText(this, "JobOffer added.", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(documentReference -> Toast.makeText(this, "Invalid Job Offer. If this is a mistake, report this as a bug.", Toast.LENGTH_SHORT).show());

                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                createJobOfferDialog.dismiss();
                finish();
                startActivity(getIntent());
            });
            createJobOfferDialog.show();
        });
    }

    /**
     * Runs when the back button is pressed and redirects the user to myProfile
     */
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
