package com.hhsfbla.hhs_fbla_mad_2021.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.EducationRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.EducationRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.ExperiencesRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.SkillsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.SkillsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.activities.LoginActivity;
import com.hhsfbla.hhs_fbla_mad_2021.activities.OnboardingActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends Fragment {

    private MyProfileViewModel mViewModel;
    //private String name;
    //private String header;
    //private int followerCount;
    //private int followingCount;
    //private ArrayList<Education> educations;
    private ArrayList<String> skills;
    private ArrayList<String> achievements;
    private RecyclerView experiencesView;
    private RecyclerView educationView;
    private RecyclerView skillsView;


    private ExperiencesRVAdapter experiencesRVAdapter;
    private EducationRVAdapter educationRVAdapter;
    private SkillsRVAdapter skillsRVAdapter;

    private User user;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    private CircleImageView pfp;
    private TextView name;

    private Button editButton;
    private Button signOutButton;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_profile_fragment, container, false);
        experiencesView = (RecyclerView)rootView.findViewById(R.id.my_profile_experiences);
        experiencesView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        skillsView = (RecyclerView)rootView.findViewById(R.id.my_profile_skills);
        skillsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        educationView = (RecyclerView)rootView.findViewById(R.id.my_profile_education);
        educationView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        pfp = rootView.findViewById(R.id.pfpImage);
        name = rootView.findViewById(R.id.my_profile_name);

        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            name.setText(u.getName());

            if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase("")) {
                Picasso.get().load(Uri.parse(u.getPfp())).into(pfp);
            } else {
                Picasso.get().load(fbuser.getPhotoUrl()).into(pfp);
            }
        });


        /*ArrayList<Experience> experiencesObj = new ArrayList<Experience>();

        for(int i = 0; i< user.getExperiences().size(); i++){
            db.collection("experiences").document(user.getExperiences().get(i)).get().addOnSuccessListener(documentSnapshot -> {
                experiencesObj.add(documentSnapshot.toObject(Experience.class));
            });
        }*/

        ArrayList<ExperiencesRVModel> experiences = new ArrayList<>();
        ArrayList<EducationRVModel> educations = new ArrayList<>();
        ArrayList<SkillsRVModel> skills = new ArrayList<>();

/*
        for(int i = 0;i<experiencesObj.size(); i++){
            experience.add(new ExperiencesRVModel(experiencesObj.get(i)));
        }

 */

        editButton = rootView.findViewById(R.id.my_profile_edit);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(rootView.getContext(), OnboardingActivity.class);
            intent.putExtra("FROM_FRAGMENT", "MyProfileFragment");
            getContext().startActivity(intent);
        });

        signOutButton = rootView.findViewById(R.id.my_profile_sign_out);
        signOutButton.setOnClickListener(v -> {
            AuthUI.getInstance().signOut(getContext()).addOnCompleteListener(task -> {
                LoginManager.getInstance().logOut();
                fbuser = null;
                startActivity(new Intent(rootView.getContext(), LoginActivity.class));
            });
        });

        skills.add(new SkillsRVModel("Python"));
        skills.add(new SkillsRVModel("Python"));
        skills.add(new SkillsRVModel("Python"));
        skills.add(new SkillsRVModel("Python"));

        educations.add(new EducationRVModel(new Education("Harvard", "June 2018", "July 2022", "Computer Science BSE", true)));
        educations.add(new EducationRVModel(new Education("Homestead High School", "June 2014", "May 2018", "General", false)));

        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experiences.add(new ExperiencesRVModel(new Experience("Data Analyst", "Apple Inc",
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));


        experiencesRVAdapter = new ExperiencesRVAdapter(experiences);
        experiencesView.setAdapter(experiencesRVAdapter);

        educationRVAdapter = new EducationRVAdapter(educations);
        educationView.setAdapter(educationRVAdapter);

        skillsRVAdapter = new SkillsRVAdapter(skills);
        skillsView.setAdapter(skillsRVAdapter);

        return rootView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
        // TODO: Use the ViewModel
    }
}