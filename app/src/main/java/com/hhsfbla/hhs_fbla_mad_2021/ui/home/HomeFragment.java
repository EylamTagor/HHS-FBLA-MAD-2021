package com.hhsfbla.hhs_fbla_mad_2021.ui.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;
import com.google.firestore.v1.StructuredQuery.Order;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.activities.HomeActivity;
import com.hhsfbla.hhs_fbla_mad_2021.activities.OtherProfileActivity;
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
    private TextView notFollowingMessage;
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
    private ArrayList<String> usersFollowingID;
    private ArrayList<String> usersFollowingPostsID;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //Linking UI XML to fields
        notFollowingMessage = rootView.findViewById(R.id.home_not_following_message);
        followingPostsView = rootView.findViewById(R.id.home_following_posts);
        followingPostsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        followingButton = rootView.findViewById(R.id.home_following);
        followingSelected = rootView.findViewById(R.id.home_following_selected);

        trendingPostsView = rootView.findViewById(R.id.home_trending_posts);
        trendingPostsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        trendingButton = rootView.findViewById(R.id.home_trending);
        trendingSelected = rootView.findViewById(R.id.home_trending_selected);

        searchButton = rootView.findViewById(R.id.home_search);
        postButton = rootView.findViewById(R.id.home_post_button);

        notFollowingMessage.setVisibility(View.GONE);

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


        //Tabs

        followingButton.setOnClickListener(v -> {
            initFollowing();

            trendingButton.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            trendingSelected.setVisibility(View.INVISIBLE);
            trendingPostsView.setVisibility(View.INVISIBLE);
            trendingButton.setAlpha(.5f);
            trendingButton.setTextColor(Color.parseColor("#F2F2F2"));

            followingButton.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            followingSelected.setVisibility(View.VISIBLE);
            followingPostsView.setVisibility(View.VISIBLE);
            followingButton.setTextColor(Color.parseColor("#10C380"));
            followingButton.setAlpha(1);
            if (usersFollowingPostsID.size() == 0) {
                notFollowingMessage.setVisibility(View.VISIBLE);
            } else {
                notFollowingMessage.setVisibility(View.GONE);
                followingPostsRVModels = new ArrayList<>();
            }
        });
        trendingButton.setOnClickListener(v -> {
            initTrending();

            followingButton.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            followingSelected.setVisibility(View.INVISIBLE);
            followingPostsView.setVisibility(View.INVISIBLE);
            followingButton.setAlpha(.5f);
            followingButton.setTextColor(Color.parseColor("#F2F2F2"));
            notFollowingMessage.setVisibility(View.GONE);


            trendingButton.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            trendingSelected.setVisibility(View.VISIBLE);
            trendingPostsView.setVisibility(View.VISIBLE);
            trendingButton.setTextColor(Color.parseColor("#10C380"));
            trendingButton.setAlpha(1);
        });

        searchButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));

        initTrending();
        initFollowing();

        followingPostsRVAdapter.setOnItemClickListener(this);
        trendingPostsRVAdapter.setOnItemClickListener(this);

        //Posting functionality
        postButton.setOnClickListener(v -> {
            postingDialog.setContentView(R.layout.posting_dialog);

            TextInputEditText title = postingDialog.findViewById(R.id.new_post_title);
            TextInputEditText hashtag = postingDialog.findViewById(R.id.new_post_hashtag);
            TextInputEditText content = postingDialog.findViewById(R.id.new_post_content);
            Button post = postingDialog.findViewById(R.id.new_post_post);

            Long tsLong = System.currentTimeMillis() / 1000;
            post.setOnClickListener(view -> {
                Post p = new Post(
                        title.getText().toString(),

                        content.getText().toString(), hashtag.getText().toString(), fuser.getUid(),
                        tsLong
                );

                db.collection("posts").add(p)
                        .addOnSuccessListener(documentReference -> {
                            db.collection("users").document(fuser.getUid()).update("myPosts", FieldValue.arrayUnion(documentReference.getId()));
                            Toast.makeText(getActivity(), "Post added.", Toast.LENGTH_SHORT).show();
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
                db.collection("posts").document(fuser.getUid()).update("timePosted", tsLong);
                postingDialog.dismiss();

                //Resets Fragment
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();

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
    public void onItemClick(View v, String userID, int position) {
        if (userID == null || userID.equals("")) {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder().build();
                shareDialog.show(linkContent);
            }
        } else {
            if (userID.equals(fuser.getUid())) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.putExtra("fragmentToLoad", "MyProfileFragment");
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), OtherProfileActivity.class);
                intent.putExtra("FROM_ACTIVITY", "HomeActivity");
                intent.putExtra("USER_ID", userID);
                startActivity(intent);
            }
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

    private void initFollowing() {
        //FollowingPostsRV
        followingPostsRVModels = new ArrayList<>();
        followingPostsRVAdapter = new PostsRVAdapter(followingPostsRVModels);
        followingPostsView.setAdapter(followingPostsRVAdapter);
        followingPostsList = new ArrayList<>();

        //Get the list of people that the user is following
        db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            usersFollowingID = u.getFollowing();

            ArrayList<User> usersFollowing = new ArrayList<>();
            usersFollowingPostsID = new ArrayList<>();

            //Get users that user follow
            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for (String ID : usersFollowingID) {
                                    if (document.getId().equals(ID)) {
                                        usersFollowing.add(document.toObject(User.class));
                                        break;
                                    }

                                }


                            }

                            //Add all the IDs of the posts of the users that user follows
                            for (User user : usersFollowing) {
                                for (String post : user.getMyPosts()) {
                                    {
                                        usersFollowingPostsID.add(post);
                                    }
                                }
                            }
                            if (usersFollowingPostsID.size() == 0) {
                                notFollowingMessage.setVisibility(View.VISIBLE);
                            } else {
                                notFollowingMessage.setVisibility(View.GONE);

                            }
                            //match post ids of the posts of the users that the user follows to the posts
                            db.collection("posts")
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task1.getResult()) {

                                                for (String ID : usersFollowingPostsID) {
                                                    if (document.getId().equals(ID)) {
                                                        followingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                                        followingPostsList.add(document.toObject(Post.class));
                                                        followingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                                                        Log.println(Log.DEBUG, "adsd", "Loading following posts");
                                                        followingPostsRVAdapter.setPosts(followingPostsList);
                                                        followingPostsRVAdapter.sortFollowingPosts();
                                                        followingPostsRVAdapter.notifyDataSetChanged();
                                                    }
                                                }

                                            }
                                        }
                                    });
                        }
                    });

            Log.println(Log.DEBUG, "adsd", "Trump " + usersFollowingPostsID.size());


        });
    }

    private void initTrending() {
        // trending Posts RV

        trendingPostsRVModels = new ArrayList<>();
        trendingPostsRVAdapter = new PostsRVAdapter(trendingPostsRVModels);
        trendingPostsView.setAdapter(trendingPostsRVAdapter);

        trendingPostsList = new ArrayList<>();

        db.collection("posts").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            trendingPostsList.add(document.toObject(Post.class));
                            trendingPostsRVModels.add(new PostsRVModel(document.toObject(Post.class)));
                            trendingPostsRVAdapter.setPosts(trendingPostsList);
                            trendingPostsRVAdapter.sortTrendingPosts();
                            trendingPostsRVAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}