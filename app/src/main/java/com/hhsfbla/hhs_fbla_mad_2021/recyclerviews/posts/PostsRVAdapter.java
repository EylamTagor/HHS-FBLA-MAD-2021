package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsRVAdapter extends RecyclerView.Adapter<PostsRVAdapter.RVViewHolder> {
    private ArrayList<PostsRVModel> posts;
    private PostsRVAdapter.OnItemClickListener listener;
    private FirebaseUser fuser;
    private FirebaseFirestore db;

    /**
     * Constructor: initializes fields needed to display the models.
     * @param items post models to be displayed
     */
    public PostsRVAdapter(ArrayList<PostsRVModel> items) {
        posts = items;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }


    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType  The view type of the new View.
     * @return the view holder to be used
     */
    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        RVViewHolder RVViewHolder = new RVViewHolder(view);
        return RVViewHolder;
    }

    /**
     *Called by RecyclerView to display the data at the specified position.
     *This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        PostsRVModel currentItem = posts.get(position);

        //retrieving the data of the post from firebase
        db.collection("users").document(currentItem.getUserID()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            holder.name.setText(u.getName());
            holder.jobTitle.setText(u.getJobTitle());

            //getting the profile page
            if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase("")) {
                Picasso.get().load(Uri.parse(u.getPfp())).into(holder.pfp);
            } else {
                Picasso.get().load(fuser.getPhotoUrl()).into(holder.pfp);
            }

            // setting initial state of like button (liked vs not liked)
            posts.get(position).setIsLiked(false);
            db.collection("posts")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            holder.likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_heart, 0, 0, 0);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for (String uid : document.toObject(Post.class).getUsersLiked()) {
                                    if (fuser.getUid().equals(uid) && posts.get(position).getTime() == document.toObject(Post.class).getTimePosted()) {
                                        holder.likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filled_heart, 0, 0, 0);
                                        posts.get(position).setIsLiked(true);
                                        break;
                                    }
                                    Log.println(Log.DEBUG, "sad", "This is the post2: " + document.getId());

                                }
                                if (document.toObject(Post.class).getTimePosted() == (posts.get(position).getTime())) {
                                    holder.likes.setText("" + document.toObject(Post.class).getLikes());
                                }
                            }
                        }
                    });

        });


        holder.description.setText(currentItem.getDescription());
        holder.tag1.setText(currentItem.getHashtag());
        holder.title.setText(currentItem.getTitle());
    }

    /**
     * gets the size of the list of post models
     * @return the size of the list of post models
     */
    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     */
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

        /**
         *
         * Creates the viewholder and sets the layout to the XML post layout
         *
         *
         * @param postView The post xml layout to be used.
         */
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
            share.setOnClickListener(v -> listener.onItemClick(share, "", getAdapterPosition()));

            pfp.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION && listener != null)
                    listener.onItemClick(pfp, posts.get(getAdapterPosition()).getUserID(), getAdapterPosition());
            });

            //Handling liking functionality
            likes.setOnClickListener(view -> {
                int num = getAdapterPosition();

                //If the post is not currently liked.
                if (!posts.get(num).isLiked()) {
                    likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filled_heart, 0, 0, 0);
                    posts.get(num).setIsLiked(true);

                    db.collection("posts")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //verify that we are referencing the right post
                                        if (document.toObject(Post.class).getTimePosted() == (posts.get(num).getTime())) {
                                            //Updating the post in the firebase database
                                            db.collection("posts").document(document.getId()).get().addOnSuccessListener(documentSnapshot -> {
                                                Post p = documentSnapshot.toObject(Post.class);
                                                p.like(fuser.getUid());
                                                likes.setText("" + p.getLikes());
                                                db.collection("posts").document(document.getId()).set(p).addOnSuccessListener(aVoid -> Log.d("SUCCESS", "DocumentSnapshot successfully written!"));
                                            });
                                            //Updating the user in the firebase database
                                            db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                                                User u = documentSnapshot.toObject(User.class);
                                                u.likePost(document.getId());
                                                db.collection("users").document(fuser.getUid()).set(u).addOnSuccessListener(aVoid -> Log.d("SUCCESS", "DocumentSnapshot successfully written!"));

                                            });
                                        }
                                    }
                                }
                            });


                    //If the post is currently liked, we need to unlike
                } else {
                    likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_heart, 0, 0, 0);
                    posts.get(num).setIsLiked(false);
                    db.collection("posts")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //verify that we are referencing the right post
                                        if (document.toObject(Post.class).getTimePosted() == (posts.get(num).getTime())) {
                                            //Updating the post in the firebase database
                                            db.collection("posts").document(document.getId()).get().addOnSuccessListener(documentSnapshot -> {
                                                Post p = documentSnapshot.toObject(Post.class);
                                                p.unlike(fuser.getUid());
                                                likes.setText("" + p.getLikes());
                                                db.collection("posts").document(document.getId()).set(p).addOnSuccessListener(aVoid -> Log.d("SUCCESS", "DocumentSnapshot successfully written!"));

                                            });
                                            //Updating the user in the firebase database
                                            db.collection("users").document(fuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                                                User u = documentSnapshot.toObject(User.class);
                                                u.removeLikedPost(document.getId());
                                                db.collection("users").document(fuser.getUid()).set(u).addOnSuccessListener(aVoid -> Log.d("SUCCESS", "DocumentSnapshot successfully written!"));

                                            });
                                        }
                                    }
                                }
                            });
                }

            });
        }

    }

    //Bubble sort algorithm to sort the following posts by most recent
    public void sortFollowingPosts() {

        for (int i = 0; i < posts.size() - 1; i++) {
            for (int j = 0; j < posts.size() - 1 - i; j++) {
                PostsRVModel temp;
                if (posts.get(j).getTime() < posts.get(j + 1).getTime()) {
                    temp = posts.get(j);
                    posts.set(j, posts.get(j + 1));
                    posts.set(j + 1, temp);
                }
            }
        }
    }

    //Bubble sort algorithm to sort the trending posts by most likes
    public void sortTrendingPosts() {

        for (int i = 0; i < posts.size() - 1; i++) {
            for (int j = 0; j < posts.size() - 1 - i; j++) {
                PostsRVModel temp;
                if (posts.get(j).getLikes() < posts.get(j + 1).getLikes()) {
                    temp = posts.get(j);
                    posts.set(j, posts.get(j + 1));
                    posts.set(j + 1, temp);
                }
            }
        }

    }

    /**
     * Sets the list of posts to what is pulled from firebase
     * @param posts the posts that are in the list to be displayed in the home fragment
     */
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
         * @param v        the view that was clicked on
         * @param userID   the id of the user who authored this post
         */
        void onItemClick(View v, String userID, int position);
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

