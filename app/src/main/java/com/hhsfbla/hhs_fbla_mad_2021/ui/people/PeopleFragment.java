package com.hhsfbla.hhs_fbla_mad_2021.ui.people;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.activities.OtherProfileActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people.PeopleRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people.PeopleRVModel;

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
    private TextView noFollowersMessage;
    private TextView notFollowingMessage;
    private int numFollowing;
    private int numFollowers;
    private boolean isFollowingTab;


    /**
     * Returns a new Instance of the PeopleFragment
     *
     * @return a new Instance of the PeopleFragment
     */
    public static PeopleFragment newInstance() {
        return new PeopleFragment();
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
        notFollowingMessage = rootView.findViewById(R.id.people_not_following_message);
        noFollowersMessage = rootView.findViewById(R.id.people_no_followers_message);
        notFollowingMessage.setVisibility(View.GONE);
        noFollowersMessage.setVisibility(View.GONE);

        isFollowingTab = false;

        //Firebase
        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        //transition to followers tab
        followersButton.setOnClickListener(v -> {
            isFollowingTab = false;
            followingButton.setTypeface(followingButton.getTypeface(), Typeface.NORMAL);
            followingSelected.setVisibility(View.INVISIBLE);
            followingPeopleView.setVisibility(View.INVISIBLE);
            followingButton.setAlpha(.5f);
            followingButton.setTextColor(Color.parseColor("#F2F2F2"));

            //no follower/following message handle
            notFollowingMessage.setVisibility(View.GONE);
            if (numFollowers < 2) {
                noFollowersMessage.setVisibility(View.VISIBLE);
            } else {
                noFollowersMessage.setVisibility(View.GONE);
            }

            followersButton.setTypeface(followingButton.getTypeface(), Typeface.BOLD);
            followersSelected.setVisibility(View.VISIBLE);
            followersPeopleView.setVisibility(View.VISIBLE);
            followersButton.setTextColor(Color.parseColor("#10C380"));
            followersButton.setAlpha(1);


        });
        //transition to following tab
        followingButton.setOnClickListener(v -> {
            isFollowingTab = true;
            followersButton.setTypeface(followingButton.getTypeface(), Typeface.NORMAL);
            followersSelected.setVisibility(View.INVISIBLE);
            followersPeopleView.setVisibility(View.INVISIBLE);
            followersButton.setAlpha(.5f);
            followersButton.setTextColor(Color.parseColor("#F2F2F2"));

            //no follower/following message handle
            noFollowersMessage.setVisibility(View.GONE);
            if (numFollowing < 2) {
                notFollowingMessage.setVisibility(View.VISIBLE);
            } else {
                notFollowingMessage.setVisibility(View.GONE);
            }


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

        //getting users who are following
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(snapshot -> {
            final User u = snapshot.toObject(User.class);
            numFollowing = u.getFollowing().size();
            if(numFollowing < 2 && isFollowingTab){
                    notFollowingMessage.setVisibility(View.VISIBLE);
            }
            else{
                notFollowingMessage.setVisibility(View.GONE);
            }

            for (String id : u.getFollowing())
                if (!id.equals(fuser.getUid())) {
                    db.collection("users").document(id).get().addOnSuccessListener(snapshot1 -> {
                        User u1 = snapshot1.toObject(User.class);
                        followingPeopleList.add(u1);
                        followingRVModels.add(new PeopleRVModel(u1, snapshot1.getId()));
                        followingRVAdapter.setSearches(followingRVModels);
                        followingRVAdapter.notifyDataSetChanged();
                    });
                }
        });

        //Follower people RV
        followersRVModels = new ArrayList<>();
        followersRVAdapter = new PeopleRVAdapter(followersRVModels);
        followersPeopleView.setAdapter(followersRVAdapter);
        followersPeopleList = new ArrayList<>();
        //getting users who are followers
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(snapshot -> {
            final User u = snapshot.toObject(User.class);
            numFollowers = u.getFollowers().size();
            if(numFollowers < 2 && !isFollowingTab){
                noFollowersMessage.setVisibility(View.VISIBLE);
            }
            else{
                noFollowersMessage.setVisibility(View.GONE);
            }
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

    /**
     * Runs when the activity is created
     *
     * @param savedInstanceState The Bundle passed into this fragment
     */
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
     * @param position the position of the clicked item
     */
    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent intent = new Intent(getContext(), OtherProfileActivity.class);
        intent.putExtra("FROM_ACTIVITY", "HomeActivity");
        intent.putExtra("USER_ID", snapshot.getId());
        startActivity(intent);
    }
}