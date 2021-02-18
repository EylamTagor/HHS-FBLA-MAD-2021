package com.hhsfbla.hhs_fbla_mad_2021.ui.saved;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.hhsfbla.hhs_fbla_mad_2021.SavedRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.SavedRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Notification;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;


import java.util.ArrayList;

public class SavedFragment extends Fragment {

    private SavedViewModel mViewModel;
    private RecyclerView savedView;
    private SavedRVAdapter savedRVAdapter;




    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        savedView = (RecyclerView)rootView.findViewById(R.id.saved_saved_jobs);
        savedView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ArrayList<SavedRVModel> savedJobs = new ArrayList<>();

        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "www.apple.com", "blah blah")));


        savedRVAdapter = new SavedRVAdapter(savedJobs);
        savedView.setAdapter(savedRVAdapter);
        return rootView;

    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SavedViewModel.class);
        // TODO: Use the ViewModel
    }



}