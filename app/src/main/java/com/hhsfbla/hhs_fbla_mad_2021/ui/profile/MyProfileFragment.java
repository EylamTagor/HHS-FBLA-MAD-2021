package com.hhsfbla.hhs_fbla_mad_2021.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.activities.AddBusinessActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education.EducationRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education.EducationRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.my_businesses.MyBusinessesRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.my_businesses.MyBusinessesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.skills.SkillsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.skills.SkillsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.activities.LoginActivity;
import com.hhsfbla.hhs_fbla_mad_2021.activities.OnboardingActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.util.NonScrollingLLM;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private RecyclerView myBusinessesView;


    private ExperiencesRVAdapter experiencesRVAdapter;
    private List<Experience> experienceList;
    private ArrayList<ExperiencesRVModel> experienceRVModels;

    private EducationRVAdapter educationRVAdapter;
    private List<Education> educationList;
    private ArrayList<EducationRVModel> educationRVModels;


    private SkillsRVAdapter skillsRVAdapter;
    private List<String> skillList;
    private ArrayList<SkillsRVModel> skillsRVModels;

    private MyBusinessesRVAdapter myBusinessesRVAdapter;
    private List<Business> myBusinessesList;
    private ArrayList<MyBusinessesRVModel> myBusinessesRVModels;


    private User user;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    private CircleImageView pfp;
    private TextView name, header, about, vision, followerCount;

    private Button editButton;
    private Button signOutButton;

    private ImageButton addBusinessButton;

    private Button copyrightInfoButton;
    private Button reportBugButton;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_profile_fragment, container, false);
        experiencesView = rootView.findViewById(R.id.my_profile_experiences);
        experiencesView.setLayoutManager(new NonScrollingLLM(getActivity()));
        skillsView = rootView.findViewById(R.id.my_profile_skills);
        skillsView.setLayoutManager(new NonScrollingLLM(getActivity()));
        myBusinessesView = rootView.findViewById(R.id.my_profile_my_businesses);
        myBusinessesView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        educationView = rootView.findViewById(R.id.my_profile_education);
        educationView.setLayoutManager(new NonScrollingLLM(getActivity()));

        copyrightInfoButton = rootView.findViewById(R.id.my_profile_view_copyright);
        reportBugButton = rootView.findViewById(R.id.my_profile_report_bug);

        pfp = rootView.findViewById(R.id.pfpImage);
        name = rootView.findViewById(R.id.my_profile_name);
        header = rootView.findViewById(R.id.my_profile_header);
        about = rootView.findViewById(R.id.my_profile_about);
        vision = rootView.findViewById(R.id.my_profile_social_vision);
        followerCount = rootView.findViewById(R.id.my_profile_follower_count);
        addBusinessButton = rootView.findViewById(R.id.my_profile_add_business);

        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            name.setText(u.getName());
            header.setText(u.getJobTitle());
            about.setText(u.getDescription());
            vision.setText(u.getSocialVision());
            followerCount.setText("" + u.getFollowers().size());

            if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase("")) {
                Picasso.get().load(Uri.parse(u.getPfp())).into(pfp);
            } else {
                Picasso.get().load(fbuser.getPhotoUrl()).into(pfp);
            }
        });

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
        copyrightInfoButton.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                v.getContext().startActivity(intent);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1mVytYJ3PKIuxIDnuQAg4PDeZgaZE5rkHz4NipMyAZAU/edit?usp=sharing")), null);

        });
        reportBugButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                v.getContext().startActivity(intent);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/NbedNqwVFTSXSrRQ7")), null);
        });

        //Add business button
        addBusinessButton.setOnClickListener(v -> {
            db.collection("businesses").add(new Business()).addOnSuccessListener(documentReference -> {
                db.collection("users").document(fbuser.getUid()).update("myBusinesses", FieldValue.arrayUnion(documentReference.getId()));
                Intent intent = new Intent(rootView.getContext(), AddBusinessActivity.class);
                intent.putExtra("FROM_ACTIVITY", "HomeActivity");
                intent.putExtra("IS_NEW_BUSINESS", "TRUE");
                intent.putExtra("BUSINESS_ID", documentReference.getId());
                startActivity(intent);
            });
        });

        // Experiences RV
        experienceList = new ArrayList<>();
        experienceRVModels = new ArrayList<>();
        experiencesRVAdapter = new ExperiencesRVAdapter(experienceRVModels);
        experiencesView.setAdapter(experiencesRVAdapter);
        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
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
        educationView.setAdapter(educationRVAdapter);
        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            final User u = documentSnapshot.toObject(User.class);

            if(u.getEducation() != null) {
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
        skillsView.setAdapter(skillsRVAdapter);
        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            final User u = documentSnapshot.toObject(User.class);

            for (String skill : u.getSkills()) {
                skillList.add(skill);
                skillsRVModels.add(new SkillsRVModel(skill));
                skillsRVAdapter.setSkills(skillList);
                skillsRVAdapter.notifyDataSetChanged();
            }
        });

        //my Businesses RV
        myBusinessesList = new ArrayList<>();
        myBusinessesRVModels = new ArrayList<>();
        myBusinessesRVAdapter = new MyBusinessesRVAdapter(myBusinessesRVModels);
        myBusinessesView.setAdapter(myBusinessesRVAdapter);
        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            final User u = documentSnapshot.toObject(User.class);
            for (String id : u.getMyBusinesses()) {
                db.collection("businesses").document(id).get().addOnSuccessListener(documentSnapshot1 -> {
                    final Business b = documentSnapshot1.toObject(Business.class);
                    myBusinessesList.add(b);
                    myBusinessesRVModels.add(new MyBusinessesRVModel(b));
                    myBusinessesRVAdapter.setbusinesses(myBusinessesList);
                    myBusinessesRVAdapter.notifyDataSetChanged();
                });
            }

        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
        // TODO: Use the ViewModel
    }
}