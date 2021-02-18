package com.hhsfbla.hhs_fbla_mad_2021.ui.home;

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


import com.hhsfbla.hhs_fbla_mad_2021.PostsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.PostsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView postsView;
    private PostsRVAdapter postsRVAdapter;
    ArrayList<PostsRVModel> followingPosts = new ArrayList<>();
    ArrayList<PostsRVModel> trendingPosts = new ArrayList<>();




    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        postsView = (RecyclerView)rootView.findViewById(R.id.home_posts);
        postsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ArrayList<PostsRVModel> posts = new ArrayList<>();
        ArrayList<String> dummyHashtags = new ArrayList<>();
        dummyHashtags.add("Sustainability");


        ArrayList<String> dummyComments = new ArrayList<>();
        dummyHashtags.add("you are a genius");
        dummyHashtags.add("I agree");
        dummyHashtags.add("WOW so cool man!");


        Post dummyPost = new Post("I planted 1,000 trees", "Yesterday I planted 1000 trees and today I planted 1000 more. It was a great experience and a privledge to be able to give back to my community in such a great way. I hope to keep up these altruistic efforts and I truly hope that you all can join me in these valiant efforts of mine. If you are interested, comment below or email me. I check my email very often and would love to get in touch to discuss logistics. Look forward to meeting with you!",
                null, "#sustainability");

        User dummyUser = new User("Sky Johnson", "skyngthowhing@gmail.com");
        dummyUser.setJobTitle("UI/UX designer");

        posts.add(new PostsRVModel(dummyPost, dummyUser));
        posts.add(new PostsRVModel(dummyPost, dummyUser));
        posts.add(new PostsRVModel(dummyPost, dummyUser));
        posts.add(new PostsRVModel(dummyPost, dummyUser));
        posts.add(new PostsRVModel(dummyPost, dummyUser));
        posts.add(new PostsRVModel(dummyPost, dummyUser));
        posts.add(new PostsRVModel(dummyPost, dummyUser));




        postsRVAdapter = new PostsRVAdapter(posts);
        postsView.setAdapter(postsRVAdapter);
        return rootView;




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}