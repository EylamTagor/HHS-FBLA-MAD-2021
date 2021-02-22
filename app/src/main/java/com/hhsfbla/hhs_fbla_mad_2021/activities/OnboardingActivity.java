package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hhsfbla.hhs_fbla_mad_2021.ExperiencesRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.util.ImageRotator;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnboardingActivity extends AppCompatActivity {
    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private CircleImageView pfp;
    private Button doneButton;
    private TextInputEditText name, job, about, vision;
    private ImageButton addExperience, addEducation, addSkill;
    private ProgressDialog progressDialog;

    private RecyclerView experiences, education, skills;
    private ExperiencesRVAdapter experiencesRVAdapter;
    private List<Experience> experienceList;
    private ArrayList<ExperiencesRVModel> experienceRVModels;

    private static final int RESULT_LOAD_IMAGE = 1;
    private Bitmap bitmap;
    private Uri imageUri;
    private boolean hasImageChanged;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private ImageRotator rotator;

    private Dialog experienceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        experienceDialog = new Dialog(this);

        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        // pfp stuff
        storageReference = FirebaseStorage.getInstance().getReference("images").child("pfps");
        hasImageChanged = false;
        rotator = new ImageRotator(this);

        // pairing objects with their views
        pfp = findViewById(R.id.ob_pfp);
        doneButton = findViewById(R.id.ob_done);
        name = findViewById(R.id.ob_name);
        job = findViewById(R.id.ob_job);
        about = findViewById(R.id.ob_about);
        vision = findViewById(R.id.ob_vision);
        addExperience = findViewById(R.id.ob_add_experience);
        addEducation = findViewById(R.id.ob_add_education);
        addSkill = findViewById(R.id.ob_add_skill);

        experiences = findViewById(R.id.ob_experiences);
        education = findViewById(R.id.ob_education);
        skills = findViewById(R.id.ob_skills);

        // autofilling name & pfp
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);

            name.setText(u.getName());
            job.setText(u.getJobTitle());
            about.setText(u.getDescription());
            vision.setText(u.getSocialVision());
            Picasso.get().load(fuser.getPhotoUrl()).into(pfp);
        });

        pfp.setOnClickListener(v -> openFileChooser());

        // dialogs
        addExperience.setOnClickListener(v -> {
            experienceDialog.setContentView(R.layout.add_experience_dialog);

            TextInputEditText title = experienceDialog.findViewById(R.id.exp_title);
            TextInputEditText workplace = experienceDialog.findViewById(R.id.exp_workplace);
            TextInputEditText start = experienceDialog.findViewById(R.id.exp_start);
            TextInputEditText end = experienceDialog.findViewById(R.id.exp_end);
            TextInputEditText desc = experienceDialog.findViewById(R.id.exp_desc);
            SwitchMaterial currentlyWorking = experienceDialog.findViewById(R.id.exp_currentlyWorking);
            Button done = experienceDialog.findViewById(R.id.exp_done);

            currentlyWorking.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (currentlyWorking.isChecked()) {
                    experienceDialog.findViewById(R.id.exp_end_layout).setVisibility(View.GONE);
                    end.setText("");
                } else {
                    experienceDialog.findViewById(R.id.exp_end_layout).setVisibility(View.VISIBLE);
                }
            });

            done.setOnClickListener(view -> {
                Experience e = new Experience(
                        title.getText().toString(),
                        workplace.getText().toString(),
                        start.getText().toString(),
                        end.getText().toString(),
                        desc.getText().toString(),
                        currentlyWorking.isChecked());

                db.collection("experiences").add(e)
                        .addOnSuccessListener(documentReference -> {
                            db.collection("users").document(fuser.getUid()).update("experiences", FieldValue.arrayUnion(documentReference.getId()));
                            Toast.makeText(this, "Experience added.", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(documentReference -> Toast.makeText(this, "Invalid experience. If this is a mistake, report this as a bug.", Toast.LENGTH_SHORT).show());

                experienceDialog.dismiss();
                finish();
                startActivity(getIntent());
            });

            experienceDialog.show();
        });

        doneButton.setOnClickListener(v -> {
            if (uploadTask != null && uploadTask.isInProgress())
                Toast.makeText(OnboardingActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            else
                uploadFile(fuser.getUid());
        });

        // recyclerview stuff
        experienceList = new ArrayList<>();
        experienceRVModels = new ArrayList<>();
        experiences.setLayoutManager(new NonScrollingLLM(this));
        experiencesRVAdapter = new ExperiencesRVAdapter(experienceRVModels);
        experiences.setAdapter(experiencesRVAdapter);
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            final User u = documentSnapshot.toObject(User.class);

            for (String id : u.getExperiences())
                db.collection("experiences").document(id).get().addOnSuccessListener(documentSnapshot1 -> {
                    final Experience e = documentSnapshot1.toObject(Experience.class);
                    experienceList.add(e);
                    experienceRVModels.add(new ExperiencesRVModel(e));
                    experiencesRVAdapter.setExperiences(experienceList);
                    experiencesRVAdapter.notifyDataSetChanged();
                });
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    /**
     * This method gets called after an action to get data from the user
     * Sets image to newly selected image
     *
     * @param requestCode the request code of the request
     * @param resultCode  a code representing the state of the result of the action
     * @param data        the data gained from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            bitmap = rotator.getImageBitmap(imageUri);
            pfp.setImageBitmap(bitmap);
            hasImageChanged = true;
        }
    }

    /**
     * Uploads the event image to cloud storage with the file name as id
     *
     * @param id the name of the file
     */
    public void uploadFile(String id) {
        if (name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        }

        if (hasImageChanged && bitmap != null) {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            final StorageReference fileRef = storageReference.child(id);
            byte[] file = rotator.getBytesFromBitmap(bitmap);
            uploadTask = fileRef.putBytes(file)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(this::editUser)).addOnFailureListener(exception -> {
                    }).addOnProgressListener(taskSnapshot -> {
                    }); // leave this just in case
        } else {
            editUser(null);
        }
    }

    private void editUser(Uri uri) {
        // update user's fields
        db.collection("users").document(fuser.getUid()).update("name", name.getText().toString());
        db.collection("users").document(fuser.getUid()).update("jobTitle", job.getText().toString());
        db.collection("users").document(fuser.getUid()).update("description", about.getText().toString());
        db.collection("users").document(fuser.getUid()).update("socialVision", vision.getText().toString());

        if (uri != null)
            db.collection("users").document(fuser.getUid()).update("pfp", uri.toString()).addOnSuccessListener(aVoid -> {
                Intent intent = new Intent(OnboardingActivity.this, HomeActivity.class);

                if (getIntent().getStringExtra("FROM_FRAGMENT") != null && getIntent().getStringExtra("FROM_FRAGMENT").equals("MyProfileFragment"))
                    intent.putExtra("fragmentToLoad", "MyProfileFragment");

                startActivity(intent);
            });
        else {
            Intent intent = new Intent(OnboardingActivity.this, HomeActivity.class);

            if (getIntent().getStringExtra("FROM_FRAGMENT") != null && getIntent().getStringExtra("FROM_FRAGMENT").equals("MyProfileFragment"))
                intent.putExtra("fragmentToLoad", "MyProfileFragment");

            startActivity(intent);
        }
    }
}