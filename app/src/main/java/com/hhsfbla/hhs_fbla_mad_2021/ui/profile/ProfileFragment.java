package com.hhsfbla.hhs_fbla_mad_2021.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhsfbla.hhs_fbla_mad_2021.ExperiencesRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private String name;
    private String header;
    private int followerCount;
    private int followingCount;
    private ArrayList<Experience> experiences;
    private RecyclerView recyclerView;
    private ExperiencesRVAdapter experiencesRVAdapter;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.experiences);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ArrayList<ExperiencesRVModel> experience = new ArrayList<>();
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));
        experience.add(new ExperiencesRVModel(new Experience("Data Analyst", new Business("Apple Inc", R.drawable.ic_launcher_foreground),
                "May 2020", "- present","- Designed app pages in AdobeXD w/ an emphasis on user experience through " +
                "divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to " +
                "increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%", true )));

        experiencesRVAdapter = new ExperiencesRVAdapter(experience);
        recyclerView.setAdapter(experiencesRVAdapter);
        return rootView;




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}