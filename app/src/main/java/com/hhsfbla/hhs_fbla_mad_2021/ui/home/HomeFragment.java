package com.hhsfbla.hhs_fbla_mad_2021.ui.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.activities.SearchActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education.EducationRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.education.EducationRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved.SavedRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved.SavedRVModel;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment implements PostsRVAdapter.OnItemClickListener {

    private HomeViewModel mViewModel;
    private RecyclerView followingPostsView;
    private RecyclerView trendingPostsView;
    private PostsRVAdapter followingPostsRVAdapter;
    private PostsRVAdapter trendingPostsRVAdapter;
    private Button followingButton;
    private Button trendingButton;
    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Dialog postingDialog;
    private FloatingActionButton postButton;
    private View trendingSelected;
    private Button searchButton;
    private View followingSelected;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private ArrayList<PostsRVModel> followingPostsRVModels;
    private ArrayList<Post> followingPostsList;
    private ArrayList<PostsRVModel> trendingPostsRVModels;
    private ArrayList<Post> trendingPostsList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //Linking UI XML to fields
        followingPostsView = (RecyclerView) rootView.findViewById(R.id.home_following_posts);
        followingPostsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        trendingPostsView = (RecyclerView) rootView.findViewById(R.id.home_trending_posts);
        trendingPostsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        trendingButton = rootView.findViewById(R.id.home_trending);
        followingButton = rootView.findViewById(R.id.home_following);
        searchButton = rootView.findViewById(R.id.home_search);
        trendingSelected = rootView.findViewById(R.id.home_trending_selected);
        followingSelected = rootView.findViewById(R.id.home_following_selected);
        postButton = rootView.findViewById(R.id.home_post_button);

        // FB sharing inits
        shareDialog = new ShareDialog(getActivity());
        callbackManager = CallbackManager.Factory.create();

        //Firebase
        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        //Posting button
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        postingDialog = new Dialog(getActivity());

        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trendingButton.setTypeface(trendingButton.getTypeface(), Typeface.NORMAL);
                followingButton.setTypeface(followingButton.getTypeface(), Typeface.BOLD);
                followingSelected.setVisibility(View.VISIBLE);
                trendingSelected.setVisibility(View.INVISIBLE);
                trendingPostsView.setVisibility(View.INVISIBLE);
                followingPostsView.setVisibility(View.VISIBLE);
                followingButton.setTextColor(Color.parseColor("#10C380"));
                //followingButton.setTextColor(Color.parseColor("@color/Green"));
                trendingButton.setTextColor(Color.parseColor("#F2F2F2"));
                followingButton.setAlpha(1);
                trendingButton.setAlpha(.5f);

            }
        });
        trendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followingButton.setTypeface(followingButton.getTypeface(), Typeface.NORMAL);
                trendingButton.setTypeface(trendingButton.getTypeface(), Typeface.BOLD);
                followingSelected.setVisibility(View.INVISIBLE);
                trendingSelected.setVisibility(View.VISIBLE);
                trendingPostsView.setVisibility(View.VISIBLE);
                followingPostsView.setVisibility(View.INVISIBLE);
                trendingButton.setTextColor(Color.parseColor("#10C380"));
                followingButton.setTextColor(Color.parseColor("#F2F2F2"));
                trendingButton.setAlpha(1);
                followingButton.setAlpha(.5f);

            }
        });

        searchButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        });

        followingPostsRVModels = new ArrayList<>();


        // trending Posts RV
        trendingPostsRVModels = new ArrayList<>();
        trendingPostsRVAdapter = new PostsRVAdapter(trendingPostsRVModels);
        trendingPostsView.setAdapter(trendingPostsRVAdapter);
        trendingPostsList = new ArrayList<>();

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                trendingPostsList.add(document.toObject(Post.class));
                                trendingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                trendingPostsRVAdapter.setPosts(trendingPostsList);
                                trendingPostsRVAdapter.notifyDataSetChanged();
                            }
                            }
                        }
                        });



        //FollowingPostsRV
        followingPostsRVModels = new ArrayList<>();
        followingPostsRVAdapter = new PostsRVAdapter(followingPostsRVModels);
        followingPostsView.setAdapter(followingPostsRVAdapter);
        followingPostsList = new ArrayList<>();

        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            ArrayList<String> usersFollowingID = u.getFollowing();
            ArrayList<User> usersFollowing = new ArrayList<>();

            ArrayList<String> usersFollowingPostsID = new ArrayList<>();
            db.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    for(String ID: usersFollowingID){
                                        if(document.getId().equals(ID)){
                                            usersFollowing.add(document.toObject(User.class));
                                        }
                                    }


                                }
                            }
                        }
                    });
            for(User user: usersFollowing){
                for(String post: user.getMyPosts()){{
                    usersFollowingPostsID.add(post);
                }}
            }

            db.collection("posts")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    for(String ID: usersFollowingPostsID){
                                        if(document.getId().equals(ID)){
                                            followingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                            followingPostsList.add(document.toObject(Post.class));
                                            followingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                            followingPostsRVAdapter.setPosts(followingPostsList);
                                            followingPostsRVAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    for(String ID2: u.getMyPosts()){
                                        if(document.getId().equals(ID2)){
                                            followingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                            followingPostsList.add(document.toObject(Post.class));
                                            followingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                            followingPostsRVAdapter.setPosts(followingPostsList);
                                            followingPostsRVAdapter.notifyDataSetChanged();
                                        }
                                    }

                                }
                            }
                        }
                    });

            /*db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
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

            });*/
        });
        /*
        ArrayList<String> dummyHashtags = new ArrayList<>();
        dummyHashtags.add("Sustainability");


        ArrayList<String> dummyComments = new ArrayList<>();
        dummyHashtags.add("you are a genius");
        dummyHashtags.add("I agree");
        dummyHashtags.add("WOW so cool man!");


        Post dummyPost = new Post("I planted 1,000 trees", "Yesterday I planted 1000 trees and today I planted 1000 more. It was a great experience and a privledge to be able to give back to my community in such a great way. I hope to keep up these altruistic efforts and I truly hope that you all can join me in these valiant efforts of mine. If you are interested, comment below or email me. I check my email very often and would love to get in touch to discuss logistics. Look forward to meeting with you!", "#sustainability");

        User dummyUser = new User("Sky Johnson", "skyngthowhing@gmail.com");
        dummyUser.setJobTitle("UI/UX designer");

        Post dummyPost2 = new Post("I ate 2,000 plants", "Yesterday I ate 2000 trees and today I ate 1000 more. It was a great experience and a privledge to be able to give back to my community in such a great way. I hope to keep up these altruistic efforts and I truly hope that you all can join me in these valiant efforts of mine. If you are interested, comment below or email me. I check my email very often and would love to get in touch to discuss logistics. Look forward to meeting with you!", "#environmentalism");

        User dummyUser2 = new User("Suarez Wanderlax", "skyngthowhing@gmail.com");
        dummyUser2.setJobTitle("CEO of Yummy");

        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));
        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));
        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));
        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));
        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));
        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));
        followingPosts.add(new PostsRVModel(dummyPost, dummyUser));

        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));
        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));
        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));
        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));
        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));
        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));
        trendingPosts.add(new PostsRVModel(dummyPost2, dummyUser2));

        followingPostsRVAdapter = new PostsRVAdapter(followingPosts);
        trendingPostsRVAdapter = new PostsRVAdapter(trendingPosts);
        followingPostsView.setAdapter(followingPostsRVAdapter);
        trendingPostsView.setAdapter(trendingPostsRVAdapter);
        */

        followingPostsRVAdapter.setOnItemClickListener(this);
        trendingPostsRVAdapter.setOnItemClickListener(this);

        //Posting functionality
        postButton.setOnClickListener(v -> {
            postingDialog.setContentView(R.layout.posting_dialog);

            TextInputEditText title = postingDialog.findViewById(R.id.new_post_title);
            TextInputEditText hashtag = postingDialog.findViewById(R.id.new_post_hashtag);
            TextInputEditText content = postingDialog.findViewById(R.id.new_post_content);
            Button post = postingDialog.findViewById(R.id.new_post_post);


            Time time = new Time();
            post.setOnClickListener(view -> {
                Post p = new Post(
                        title.getText().toString(),
                        hashtag.getText().toString(),
                        content.getText().toString(), fuser.getUid(),
                        time.toMillis(true)
                        );

                db.collection("posts").add(p)
                        .addOnSuccessListener(documentReference -> {
                            db.collection("users").document(fuser.getUid()).update("myPosts", FieldValue.arrayUnion(documentReference.getId()));
                            Toast.makeText(getActivity(), "Post added.", Toast.LENGTH_SHORT).show();
                            followingPostsList.add(p);
                            followingPostsRVModels.add(new PostsRVModel(p));
                            followingPostsRVAdapter.setPosts(followingPostsList);
                            followingPostsRVAdapter.notifyDataSetChanged();
                            trendingPostsList.add(p);
                            trendingPostsRVModels.add(new PostsRVModel(p));
                            trendingPostsRVAdapter.setPosts(followingPostsList);
                            trendingPostsRVAdapter.notifyDataSetChanged();
                        }).addOnFailureListener(documentReference -> Toast.makeText(getActivity(), "Invalid education. If this is a mistake, report this as a bug.", Toast.LENGTH_SHORT).show());

                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                db.collection("posts").document(fuser.getUid()).update("title", title.getText().toString());
                db.collection("posts").document(fuser.getUid()).update("hashtag", hashtag.getText().toString());
                db.collection("posts").document(fuser.getUid()).update("content", content.getText().toString());
                db.collection("posts").document(fuser.getUid()).update("id", fuser.getUid());
                postingDialog.dismiss();
            });
            postingDialog.show();
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * Describes what to do when a user from the attendees recycler view is clicked, in this case share the right Facebook post
     *
     * @param v        the View that will contain the click action
     * @param position the numbered position of snapshot in the full item list
     */
    @Override
    public void onItemClick(View v, int position) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder().build();
            shareDialog.show(linkContent);
        }
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
}