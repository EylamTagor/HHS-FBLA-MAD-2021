package com.hhsfbla.hhs_fbla_mad_2021.ui.jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVModel;

import java.util.ArrayList;

public class JobsFragment extends Fragment {


    //Fields
    private JobsViewModel mViewModel;
    private RecyclerView jobsView;
    private JobsRVAdapter jobsRVAdapter;
    private SearchView searchView;
    private User user;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    /**
     * Returns a new Instance of the JobsFragment
     *
     * @return a new Instance of the JobsFragment
     */
    public static JobsFragment newInstance() {
        return new JobsFragment();
    }


    /**
     * Initializations when the View is first created. connects UI to backend.
     *
     * @param inflater           The LayoutInflater
     * @param container          The ViewGroup
     * @param savedInstanceState The Bundle passed into this fragment
     * @return Returns the View
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        jobsView = (RecyclerView) rootView.findViewById(R.id.job_jobs);
        jobsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ArrayList<JobsRVModel> jobs = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("jobOffers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                jobs.add(new JobsRVModel(document.toObject(JobOffer.class)));
                            }
                            jobsRVAdapter = new JobsRVAdapter(jobs);
                            jobsView.setAdapter(jobsRVAdapter);
                        } else {

                        }
                    }
                });

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

    /**
     * Runs when the activity is created
     *
     * @param savedInstanceState The Bundle passed into this fragment
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        // TODO: Use the ViewModel
    }

}