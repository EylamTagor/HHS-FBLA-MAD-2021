package com.hhsfbla.hhs_fbla_mad_2021.ui.jobs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.hhsfbla.hhs_fbla_mad_2021.JobsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.JobsRVModel;

import com.hhsfbla.hhs_fbla_mad_2021.PostsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.PostsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Notification;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;


import java.util.ArrayList;

public class JobsFragment extends Fragment {

    private JobsViewModel mViewModel;
    private RecyclerView jobsView;
    private JobsRVAdapter jobsRVAdapter;
    private SearchView searchView;




    public static JobsFragment newInstance() {
        return new JobsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        jobsView = (RecyclerView)rootView.findViewById(R.id.job_jobs);
        jobsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ArrayList<JobsRVModel> jobs = new ArrayList<>();

        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));
        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));
        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));
        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));
        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));
        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));
        jobs.add(new JobsRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "- Will design app pages in AdobeXD w/ an emphasis on user experience through divergent and convergent experimentation\n - Conceptualized and implemented app features in Swift UIKit and Java Android Studio to increase user retention\n - Coordinated interviews with Autism podcasts and blogs, increased social media engagement by 4100%")));

        jobsRVAdapter = new JobsRVAdapter(jobs);
        jobsView.setAdapter(jobsRVAdapter);
        searchView = rootView.findViewById(R.id.jobs_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                jobsRVAdapter.getFilter().filter(s);
                return false;
            }
        });
        return rootView;




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        // TODO: Use the ViewModel
    }

}