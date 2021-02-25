package com.hhsfbla.hhs_fbla_mad_2021.ui.people;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.activities.BusinessActivity;
import com.hhsfbla.hhs_fbla_mad_2021.activities.HomeActivity;
import com.hhsfbla.hhs_fbla_mad_2021.activities.OtherProfileActivity;
import com.hhsfbla.hhs_fbla_mad_2021.activities.SearchActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people.PeopleRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people.PeopleRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search.SearchRVModel;


import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment implements PeopleRVAdapter.OnItemClickListener {

    private PeopleViewModel mViewModel;
    private RecyclerView followingPeopleView;
    private RecyclerView followersPeopleView;
    private PeopleRVAdapter followingRVAdapter;
    private PeopleRVAdapter followersRVAdapter;
    private Button followersButton;
    private Button followingButton;
    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private View followersSelected;
    private View followingSelected;
    private ArrayList<PeopleRVModel> followingRVModels;
    private List<User> followingPeopleList;
    private ArrayList<PeopleRVModel> followersRVModels;
    private List<User> followersPeopleList;

    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_people, container, false);

        //Linking UI XML to fields
        followersPeopleView = rootView.findViewById(R.id.people_followers_recyclerview);
        followersPeopleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        followersButton = rootView.findViewById(R.id.people_followers);
        followersSelected = rootView.findViewById(R.id.people_followers_selected);

        followingPeopleView = rootView.findViewById(R.id.people_following_recyclerview);
        followingPeopleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        followingButton = rootView.findViewById(R.id.people_following);
        followingSelected = rootView.findViewById(R.id.people_following_selected);

        //Firebase
        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        //Tabs
        followersButton.setOnClickListener(v -> {
            followingButton.setTypeface(followingButton.getTypeface(), Typeface.NORMAL);
            followingSelected.setVisibility(View.INVISIBLE);
            followingPeopleView.setVisibility(View.INVISIBLE);
            followingButton.setAlpha(.5f);
            followingButton.setTextColor(Color.parseColor("#F2F2F2"));

            followersButton.setTypeface(followingButton.getTypeface(), Typeface.BOLD);
            followersSelected.setVisibility(View.VISIBLE);
            followersPeopleView.setVisibility(View.VISIBLE);
            followersButton.setTextColor(Color.parseColor("#10C380"));
            followersButton.setAlpha(1);
        });

        followingButton.setOnClickListener(v -> {
            followersButton.setTypeface(followingButton.getTypeface(), Typeface.NORMAL);
            followersSelected.setVisibility(View.INVISIBLE);
            followersPeopleView.setVisibility(View.INVISIBLE);
            followersButton.setAlpha(.5f);
            followersButton.setTextColor(Color.parseColor("#F2F2F2"));

            followingButton.setTypeface(followingButton.getTypeface(), Typeface.BOLD);
            followingSelected.setVisibility(View.VISIBLE);
            followingPeopleView.setVisibility(View.VISIBLE);
            followingButton.setTextColor(Color.parseColor("#10C380"));
            followingButton.setAlpha(1);
        });

        // Following People RV
        followingPeopleList = new ArrayList<>();
        followingRVModels = new ArrayList<>();
        followingRVAdapter = new PeopleRVAdapter(followingRVModels);
        followingPeopleView.setAdapter(followingRVAdapter);
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(snapshot -> {
            final User u = snapshot.toObject(User.class);

            for (String id : u.getFollowing())
                if (!id.equals(fuser.getUid()))
                    db.collection("users").document(id).get().addOnSuccessListener(snapshot1 -> {
                        User u1 = snapshot1.toObject(User.class);
                        followingPeopleList.add(u1);
                        followingRVModels.add(new PeopleRVModel(u1, snapshot1.getId()));
                        followingRVAdapter.setSearches(followingRVModels);
                        followingRVAdapter.notifyDataSetChanged();
                    });
        });

        //Follower people RV
        followersRVModels = new ArrayList<>();
        followersRVAdapter = new PeopleRVAdapter(followersRVModels);
        followersPeopleView.setAdapter(followersRVAdapter);
        followersPeopleList = new ArrayList<>();
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(snapshot -> {
            final User u = snapshot.toObject(User.class);

            for (String id : u.getFollowers())
                if (!id.equals(fuser.getUid()))
                    db.collection("users").document(id).get().addOnSuccessListener(snapshot1 -> {
                        User u1 = snapshot1.toObject(User.class);
                        followersPeopleList.add(u1);
                        followersRVModels.add(new PeopleRVModel(u1, snapshot1.getId()));
                        followersRVAdapter.setSearches(followersRVModels);
                        followersRVAdapter.notifyDataSetChanged();
                    });
        });

        followersRVAdapter.setOnItemClickListener(this);
        followingRVAdapter.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * Specifies the action after clicking on the RecyclerView that utilizes this adapter
     *
     * @param snapshot the user or business pulled from Firebase Firestore, formatted as a DocumentSnapshot
     * @param position
     */
    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent intent = new Intent(getContext(), OtherProfileActivity.class);
        intent.putExtra("FROM_ACTIVITY", "HomeActivity");
        intent.putExtra("USER_ID", snapshot.getId());
        startActivity(intent);
    }
}