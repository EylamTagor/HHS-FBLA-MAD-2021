package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts;

import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs.JobsRVModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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


        db.collection("users").document(currentItem.getUserID()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            holder.name.setText(u.getName());
            holder.jobTitle.setText(u.getJobTitle());

            if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase("")) {
                Picasso.get().load(Uri.parse(u.getPfp())).into(holder.pfp);
            } else {
                Picasso.get().load(fuser.getPhotoUrl()).into(holder.pfp);
            }
        });
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for(String uid: document.toObject(Post.class).getUsersLiked()) {
                                    if (fuser.getUid().equals(uid)) {
                                        holder.likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filled_heart, 0, 0, 0);
                                        posts.get(position).setIsLiked(true);
                                    }
                                    else{
                                        holder.likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_heart, 0, 0, 0);
                                        posts.get(position).setIsLiked(false);
                                    }
                                }
                                if (document.toObject(Post.class).getTimePosted() == (posts.get(position).getTime())){
                                    holder.likes.setText(""+document.toObject(Post.class).getLikes());
                                }
                            }
                        }
                    }
                });


        holder.description.setText(currentItem.getDescription());

        holder.tag1.setText(currentItem.getHashtag());
        holder.title.setText(currentItem.getTitle());
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
        CircleImageView pfp;
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

            //Handling liking functionality
            likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = getAdapterPosition();
                    if(!posts.get(num).isLiked()) {
                        Log.println(Log.DEBUG, "asdasd", "bruh moment");
                        likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filled_heart, 0, 0, 0);
                        posts.get(num).setIsLiked(true);

                        db.collection("posts")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.println(Log.DEBUG, "sad", "JOe weller");

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.println(Log.DEBUG, "sad", "Document Time: " + document.toObject(Post.class).getTimePosted() + " post Time " + (posts.get(num).getTime()));

                                                if (document.toObject(Post.class).getTimePosted() == (posts.get(num).getTime())) {

                                                    db.collection("posts").document(document.getId()).get().addOnSuccessListener(documentSnapshot -> {
                                                        Post p = documentSnapshot.toObject(Post.class);
                                                        p.like(fuser.getUid());
                                                        likes.setText(""+p.getLikes());
                                                        db.collection("posts").document(document.getId()).set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("SUCCESS", "DocumentSnapshot successfully written!");
                                                            }
                                                        });

                                                    });
                                                    db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                                                        User u = documentSnapshot.toObject(User.class);
                                                        u.likePost(document.getId());
                                                        db.collection("users").document(fuser.getUid()).set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("SUCCESS", "DocumentSnapshot successfully written!");
                                                            }
                                                        });

                                                    });
                                                }
                                            }
                                        }
                                    }
                                });


                    }
                    else{
                        Log.println(Log.DEBUG, "asdasd", "chicken moment");
                        likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_heart, 0, 0, 0);
                        posts.get(num).setIsLiked(false);

                        db.collection("posts")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.println(Log.DEBUG, "sad", "JOe weller");

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.println(Log.DEBUG, "sad", "Document Time: " + document.toObject(Post.class).getTimePosted() + " post Time " + (posts.get(num).getTime()));

                                                if (document.toObject(Post.class).getTimePosted() == (posts.get(num).getTime())) {

                                                    db.collection("posts").document(document.getId()).get().addOnSuccessListener(documentSnapshot -> {
                                                        Post p = documentSnapshot.toObject(Post.class);
                                                        p.unlike(fuser.getUid());
                                                        likes.setText(""+p.getLikes());
                                                        db.collection("posts").document(document.getId()).set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("SUCCESS", "DocumentSnapshot successfully written!");
                                                            }
                                                        });

                                                    });
                                                    db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                                                        User u = documentSnapshot.toObject(User.class);
                                                        u.removeLikedPost(document.getId());
                                                        db.collection("users").document(fuser.getUid()).set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("SUCCESS", "DocumentSnapshot successfully written!");
                                                            }
                                                        });

                                                    });
                                                }
                                            }
                                        }
                                    }
                                });
                    }

                }
            });
        }

    }

    //Bubble sort to sort the following posts by most recent
    public void sortFollowingPosts(){

        for(int i = 0; i <posts.size() - 1; i++){
            for(int j = 0; j < posts.size() - 1 - i; j++) {
                PostsRVModel temp;
                if (posts.get(j).getTime() < posts.get(j + 1).getTime()) {
                    temp = posts.get(j);
                    posts.set(j, posts.get(j + 1));
                    posts.set(j + 1, temp);
                }
            }
        }
    }

    //Bubble sort to sort the trending posts by most likes
    public void sortTrendingPosts(){

        for(int i = 0; i <posts.size() - 1; i++){
            for(int j = 0; j < posts.size() - 1 - i; j++) {
                PostsRVModel temp;
                if (posts.get(j).getLikes() < posts.get(j + 1).getLikes()) {
                    temp = posts.get(j);
                    posts.set(j, posts.get(j + 1));
                    posts.set(j + 1, temp);
                }
            }
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

