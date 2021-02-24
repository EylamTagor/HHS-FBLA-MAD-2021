package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.share.widget.ShareButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVModel;

import java.util.ArrayList;
import java.util.List;

public class PostsRVAdapter extends RecyclerView.Adapter<PostsRVAdapter.RVViewHolder> {
    private ArrayList<PostsRVModel> posts;
    private PostsRVAdapter.OnItemClickListener listener;

    private FirebaseUser fuser;
    private FirebaseFirestore db;

    int row_index = -1;

    public PostsRVAdapter(ArrayList<PostsRVModel> items) {
        posts = items;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        RVViewHolder RVViewHolder = new RVViewHolder(view);
        return RVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        PostsRVModel currentItem = posts.get(position);
        //NEED TO FIX, FIGURE OUT HOW TO SET IMAGE RESOURCE

        //Get User

        //holder.name.setText(user.getName());
        //holder.jobTitle.setText(user.getJobTitle());

        holder.pfp.setImageResource(R.drawable.ic_followers);
        holder.description.setText(currentItem.getDescription());

        holder.tag1.setText(currentItem.getHashtag());
        holder.title.setText(currentItem.getTitle());
        Log.println(Log.DEBUG,"nada","" + currentItem.getLikes());
//        holder.likes.setText(currentItem.getLikes());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    };

    public class RVViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView jobTitle;
        TextView description;
        TextView title;
        TextView tag1;
        Button likes;
        AppCompatButton share;
        ImageView pfp;
        LinearLayout postLayout;

        public RVViewHolder(@NonNull View postView) {
            super(postView);
            name = postView.findViewById(R.id.post_name);
            jobTitle = postView.findViewById(R.id.post_job_title);
            description = postView.findViewById(R.id.post_description);
            pfp = postView.findViewById(R.id.post_profile_picture);
            postLayout = postView.findViewById(R.id.post_layout);
            tag1 = postView.findViewById(R.id.post_tag_one);
            likes = postView.findViewById(R.id.post_likes);
            title = postView.findViewById(R.id.post_header);
            share = postView.findViewById(R.id.post_share);
            share.setOnClickListener(v -> listener.onItemClick(share, getAdapterPosition()));
        }
    }
    public void setPosts(List<Post> posts) {
        this.posts.clear();

        for (Post p : posts)
            this.posts.add(new PostsRVModel(p));
    }
    /**
     * Used to specify action after clicking on the RecyclerView that utilizes this adapter
     */
    public interface OnItemClickListener {
        /**
         * Specifies the action after clicking on the RecyclerView that utilizes this adapter
         *
         * @param position the numbered position of snapshot in the full item list
         * @param v        the View that will contain the click action
         */
        void onItemClick(View v, int position);
    }

    /**
     * Determines the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     *
     * @param listener the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     */
    public void setOnItemClickListener(PostsRVAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}