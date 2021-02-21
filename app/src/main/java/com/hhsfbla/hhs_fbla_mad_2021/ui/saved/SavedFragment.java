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

import kotlinx.coroutines.Job;

public class SavedFragment extends Fragment {

    private SavedViewModel mViewModel;
    private RecyclerView savedView;
    private SavedRVAdapter savedRVAdapter;
    ArrayList<SavedRVModel> savedJobs = new ArrayList<>();





    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        //Pull down firebase savedJobs and set = to SavedJob field. Should then display.
        /*
        for(int i = 0; i <savedJobs.size(); i++){
            this.savedJobs.add(new SavedRVModel(savedJobs.get(i)));
        }*/

        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        savedView = (RecyclerView)rootView.findViewById(R.id.saved_saved_jobs);
        savedView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


      /*  savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));

*/
        savedRVAdapter = new SavedRVAdapter(this.savedJobs);
        savedView.setAdapter(savedRVAdapter);
        return rootView;

    }

    public void addSavedJob(JobOffer jobOffer){
        savedJobs.add(new SavedRVModel(jobOffer));
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