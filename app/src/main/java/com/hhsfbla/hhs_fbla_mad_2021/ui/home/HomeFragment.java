package com.hhsfbla.hhs_fbla_mad_2021.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView followingPostsView;
    private RecyclerView trendingPostsView;

    private PostsRVAdapter followingPostsRVAdapter;
    private PostsRVAdapter trendingPostsRVAdapter;

    private ArrayList<PostsRVModel> followingPosts = new ArrayList<>();
    private ArrayList<PostsRVModel> trendingPosts = new ArrayList<>();

    private Button followingButton;
    private Button trendingButton;
    private View trendingSelected;
    private View followingSelected;
    private SearchView searchView;





    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        followingPostsView = (RecyclerView)rootView.findViewById(R.id.home_following_posts);
        followingPostsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        trendingPostsView = (RecyclerView)rootView.findViewById(R.id.home_trending_posts);
        trendingPostsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        trendingButton = rootView.findViewById(R.id.home_trending);
        followingButton = rootView.findViewById(R.id.home_following);
        trendingSelected = rootView.findViewById(R.id.home_trending_selected);
        followingSelected = rootView.findViewById(R.id.home_following_selected);


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
        searchView = rootView.findViewById(R.id.home_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                trendingPostsRVAdapter.getFilter().filter(s);

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
                return false;
            }
        });

        ArrayList<PostsRVModel> followingPosts = new ArrayList<>();
        ArrayList<PostsRVModel> trendingPosts = new ArrayList<>();

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
        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}