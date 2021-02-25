package com.hhsfbla.hhs_fbla_mad_2021.ui.people;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.activities.SearchActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.People;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people.PeopleRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people.PeopleRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVModel;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
    private CallbackManager callbackManager;
    private ArrayList<PeopleRVModel> followingRVModel;
    private ArrayList<User> followingPeopleList;
    private ArrayList<PeopleRVModel> followersRVModel;
    private ArrayList<User> followersPeopleList;

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
        followingRVModel = new ArrayList<>();
        followingRVAdapter = new PeopleRVAdapter(followingRVModel);
        followingPeopleView.setAdapter(followingRVAdapter);
        followingPeopleList = new ArrayList<>();
        db.collection("users").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            followingPeopleList.add(document.toObject(User.class));
                            followingRVModel.add(new PeopleRVModel(document.toObject(User.class), document.getId()));
                            followingRVAdapter.notifyDataSetChanged();
                        }
                    }
                });



        //Follower people RV
        followersRVModel = new ArrayList<>();
        followersRVAdapter = new PeopleRVAdapter(followersRVModel);
        followersPeopleView.setAdapter(followersRVAdapter);
        followersPeopleList = new ArrayList<>();


            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                        followersPeopleList.add(document.toObject(User.class));
                                        followersRVModel.add(new PeopleRVModel(document.toObject(User.class), document.getId()));
                                        followersRVAdapter.notifyDataSetChanged();



                            }
                        }
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
     * This method gets called after an action to get data from the user
     * handles callback for facebook sharing
     *
     * @param requestCode the request code of the request
     * @param resultCode  a code representing the state of the result of the action
     * @param data        the data gained from the activity
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Specifies the action after clicking on the RecyclerView that utilizes this adapter
     *
     * @param snapshot the user or business pulled from Firebase Firestore, formatted as a DocumentSnapshot
     * @param position
     */
    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {

    }
}