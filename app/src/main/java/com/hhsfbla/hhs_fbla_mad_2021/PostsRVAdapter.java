package com.hhsfbla.hhs_fbla_mad_2021;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PostsRVAdapter extends RecyclerView.Adapter<PostsRVAdapter.StaticRVViewHolder> {
    private ArrayList<PostsRVModel> posts;
    int row_index = -1;

    public PostsRVAdapter(ArrayList<PostsRVModel> items) {
        this.posts = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        PostsRVModel currentItem = posts.get(position);
        //NEED TO FIX, FIGURE OUT HOW TO SET IMAGE RESOURCE
        holder.pfp.setImageResource(R.drawable.ic_followers);
        holder.description.setText(currentItem.getDescription());
        holder.jobTitle.setText(currentItem.getJobTitle());
        holder.tag1.setText(currentItem.getHashtags().get(0));
        holder.tag2.setText(currentItem.getHashtags().get(1));
        holder.tag3.setText(currentItem.getHashtags().get(2));
        holder.title.setText(currentItem.getTitle());
        holder.name.setText(currentItem.getName());
        holder.likes.setText("540");
        holder.comments.setText("100");
        //holder.likes.setText(currentItem.getLikes());
        //holder.comments.setText(currentItem.getNumComments());



    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView jobTitle;
        TextView description;
        TextView title;
        TextView tag1;
        TextView tag2;
        TextView tag3;
        Button likes;
        Button comments;
        ImageView pfp;
        LinearLayout postLayout;

        public StaticRVViewHolder(@NonNull View postView) {
            super(postView);
            name = postView.findViewById(R.id.name);
            jobTitle = postView.findViewById(R.id.job_title);
            description = postView.findViewById(R.id.description);
            pfp = postView.findViewById(R.id.profile_picture);
            postLayout =  postView.findViewById(R.id.post_layout);
            tag1 = postView.findViewById(R.id.tag_one);
            tag2 = postView.findViewById(R.id.tag_two);
            tag3 = postView.findViewById(R.id.tag_three);
            likes = postView.findViewById(R.id.likes);
            comments = postView.findViewById(R.id.comments);
            title = postView.findViewById(R.id.title);


        }
    }
}

