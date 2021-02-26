package com.hhsfbla.hhs_fbla_mad_2021.ui.saved;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved.SavedRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved.SavedRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class SavedFragment extends Fragment {

    private SavedViewModel mViewModel;
    private RecyclerView savedView;
    private SavedRVAdapter savedRVAdapter;
    ArrayList<SavedRVModel> savedJobs = new ArrayList<>();

    private User user;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;


    /**
     * Returns a new Instance of the SavedFragment
     * @return a new Instance of the SavedFragment
     */
    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    /**
     * Initializations when the View is first created. connects UI to backend.
     * @param inflater The LayoutInflater
     * @param container The ViewGroup
     * @param savedInstanceState The Bundle passed into this fragment
     * @return Returns the View
     */
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


        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            ArrayList<String> savedOffers = u.getJobOffers();
            db.collection("jobOffers")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    for(String ID: savedOffers){
                                        if(document.getId().equals(ID)){
                                            savedJobs.add(new SavedRVModel(document.toObject(JobOffer.class)));
                                            Log.d("WORKS", "TEST");
                                            Log.d("Array", savedJobs.toString());
                                        }
                                    }

                                }
                                savedRVAdapter = new SavedRVAdapter(savedJobs);
                                savedView.setAdapter(savedRVAdapter);
                            } else {

                            }
                        }
                    });

        });
        Log.d("Array2", savedJobs.toString());
/*
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
        savedJobs.add(new SavedRVModel(new JobOffer("112343211dds", "Backend Developer", "https://jobs.apple.com/en-us/details/200200942/ios-macos-developer", "blah blah")));
*/


        return rootView;

    }

    /**
     * Adds a jobOffer
     * @param jobOffer the job offer to be added
     */
    public void addSavedJob(JobOffer jobOffer){
        savedJobs.add(new SavedRVModel(jobOffer));
    }

    /**
     * Runs when the activity is created
     * @param savedInstanceState The Bundle passed into this fragment
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SavedViewModel.class);
        // TODO: Use the ViewModel
    }



}