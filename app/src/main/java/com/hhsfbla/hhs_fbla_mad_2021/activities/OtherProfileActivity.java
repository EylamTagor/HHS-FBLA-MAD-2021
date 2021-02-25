package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education.EducationRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education.EducationRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.skills.SkillsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.skills.SkillsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// When someone else views your profile
public class OtherProfileActivity extends AppCompatActivity {
    private CircleImageView pfp;
    private TextView name, header, followerCount, about, vision;
    private Button followersButton, follow;
    private AppCompatButton mail;

    private RecyclerView experiences, education, skills;

    private ExperiencesRVAdapter experiencesRVAdapter;
    private List<Experience> experienceList;
    private ArrayList<ExperiencesRVModel> experienceRVModels;

    private EducationRVAdapter educationRVAdapter;
    private List<Education> educationList;
    private ArrayList<EducationRVModel> educationRVModels;

    private SkillsRVAdapter skillsRVAdapter;
    private List<String> skillList;
    private ArrayList<SkillsRVModel> skillsRVModels;

    private FirebaseFirestore db;
    private FirebaseUser fuser;

    /**
     * Sets the view to another profile
     *
     * @param savedInstanceState the save state of the activity or page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        pfp = findViewById(R.id.other_profile_pfpImage);
        name = findViewById(R.id.other_profile_name);
        header = findViewById(R.id.other_profile_header);
        followerCount = findViewById(R.id.other_profile_follower_count);
        about = findViewById(R.id.other_profile_about);
        vision = findViewById(R.id.other_profile_social_vision);
        followersButton = findViewById(R.id.other_profile_follower_icon);
        follow = findViewById(R.id.other_profile_follow);
        mail = findViewById(R.id.other_profile_mail);

        experiences = findViewById(R.id.other_profile_experiences);
        experiences.setLayoutManager(new NonScrollingLLM(this));

        education = findViewById(R.id.other_profile_education);
        education.setLayoutManager(new NonScrollingLLM(this));

        skills = findViewById(R.id.other_profile_skills);
        skills.setLayoutManager(new NonScrollingLLM(this));

        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("users").document(getIntent().getStringExtra("USER_ID")).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            name.setText(u.getName());
            header.setText(u.getJobTitle());
            about.setText(u.getDescription());
            vision.setText(u.getSocialVision());
            followerCount.setText("" + u.getFollowers().size());

            if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase(""))
                Picasso.get().load(Uri.parse(u.getPfp())).into(pfp);
        });

        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.toObject(User.class).getFollowing().contains(getIntent().getStringExtra("USER_ID"))) {
                follow.setText("Unfollow");
            } else {
                follow.setText("Follow");
            }
        });

        follow.setOnClickListener(v -> {
            db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                if (!documentSnapshot.toObject(User.class).getFollowing().contains(getIntent().getStringExtra("USER_ID"))) {
                    db.collection("users").document(getIntent().getStringExtra("USER_ID")).update("followers", FieldValue.arrayUnion(fuser.getUid()));
                    db.collection("users").document(fuser.getUid()).update("following", FieldValue.arrayUnion(getIntent().getStringExtra("USER_ID")));
                    follow.setText("Unfollow");
                } else {
                    db.collection("users").document(getIntent().getStringExtra("USER_ID")).update("followers", FieldValue.arrayRemove(fuser.getUid()));
                    db.collection("users").document(fuser.getUid()).update("following", FieldValue.arrayRemove(getIntent().getStringExtra("USER_ID")));
                    follow.setText("Follow");
                }
            });
        });

        mail.setOnClickListener(v -> {
            // TODO
        });

        // Experiences RV
        experienceList = new ArrayList<>();
        experienceRVModels = new ArrayList<>();
        experiencesRVAdapter = new ExperiencesRVAdapter(experienceRVModels);
        experiences.setAdapter(experiencesRVAdapter);
        db.collection("users").document(getIntent().getStringExtra("USER_ID")).get().addOnSuccessListener(documentSnapshot -> {
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

        // Education RV
        educationList = new ArrayList<>();
        educationRVModels = new ArrayList<>();
        educationRVAdapter = new EducationRVAdapter(educationRVModels);
        education.setAdapter(educationRVAdapter);
        db.collection("users").document(getIntent().getStringExtra("USER_ID")).get().addOnSuccessListener(documentSnapshot -> {
            final User u = documentSnapshot.toObject(User.class);

            if (u.getEducation() != null) {
                for (String id : u.getEducation())
                    db.collection("educations").document(id).get().addOnSuccessListener(documentSnapshot1 -> {
                        final Education e = documentSnapshot1.toObject(Education.class);
                        educationList.add(e);
                        educationRVModels.add(new EducationRVModel(e));
                        educationRVAdapter.setEducations(educationList);
                        educationRVAdapter.notifyDataSetChanged();
                    });
            }
        });

        // Skills RV
        skillList = new ArrayList<>();
        skillsRVModels = new ArrayList<>();
        skillsRVAdapter = new SkillsRVAdapter(skillsRVModels);
        skills.setAdapter(skillsRVAdapter);
        db.collection("users").document(getIntent().getStringExtra("USER_ID")).get().addOnSuccessListener(documentSnapshot -> {
            final User u = documentSnapshot.toObject(User.class);

            for (String skill : u.getSkills()) {
                skillList.add(skill);
                skillsRVModels.add(new SkillsRVModel(skill));
                skillsRVAdapter.setSkills(skillList);
                skillsRVAdapter.notifyDataSetChanged();
            }
        });
    }
}
