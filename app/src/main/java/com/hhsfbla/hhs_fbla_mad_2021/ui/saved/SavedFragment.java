package com.hhsfbla.hhs_fbla_mad_2021.ui.saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved.SavedRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved.SavedRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;


import java.util.ArrayList;

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