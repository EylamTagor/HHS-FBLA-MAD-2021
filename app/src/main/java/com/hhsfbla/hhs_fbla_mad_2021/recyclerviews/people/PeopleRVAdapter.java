package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search.SearchRVModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleRVAdapter extends RecyclerView.Adapter<PeopleRVAdapter.RVViewHolder> {
    //List of people models
    private List<PeopleRVModel> people;
    private PeopleRVAdapter.OnItemClickListener listener;
    private FirebaseFirestore db;
    int row_index = -1;

    /**
     * Constructor: takes in the people models to be displayed and initializes field
     * @param items people items to be displayed
     */
    public PeopleRVAdapter(ArrayList<PeopleRVModel> items) {
        this.people = items;
        db = FirebaseFirestore.getInstance();
    }

    /**
     *
     * Creates the  View holder to be used. The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType  The view type of the new View.
     * @return the view holder to be used
     */
    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        RVViewHolder rvViewHolder = new RVViewHolder(view);
        return rvViewHolder;
    }
    /**
     *Called by RecyclerView to display the data at the specified position.
     *This method updates the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        PeopleRVModel currentItem = people.get(position);
        holder.name.setText(currentItem.getName());
        holder.header.setText(currentItem.getHeader());

        //getting the user from firebase.
        db.collection("users").document(currentItem.getId()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);
            //Getting the profile picture from firebase
            if (u != null)
                if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase(""))
                    Picasso.get().load(Uri.parse(u.getPfp())).into(holder.pfp);
        });
    }

    /**
     *
     * @return the size of the people in the list to be displayed.
     */
    @Override
    public int getItemCount() {
        return people.size();
    }

    /**
     * Updates the list of users using a new list
     *
     * @param people new list to replace the old search list
     */
    public void setSearches(List<PeopleRVModel> people) {
        this.people = people;
    }

    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     */
    public class RVViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView header;
        private CircleImageView pfp;

        /**
         * Connects the fields to the XML of the people item. Initializes variables for display.
         *
         * @param peopleView the people xml layout reference
         */
        public RVViewHolder(@NonNull View peopleView) {
            super(peopleView);
            name = peopleView.findViewById(R.id.search_item_name);
            pfp = peopleView.findViewById(R.id.search_item_pfp);
            header = peopleView.findViewById(R.id.search_item_header);

            peopleView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION && listener != null) {
                    db.collection("users").document(people.get(getAdapterPosition()).getId()).get().addOnSuccessListener(documentSnapshot -> listener.onItemClick(documentSnapshot, getAdapterPosition()));
                }
            });
        }
    }

    /**
     * Used to specify action after clicking on the RecyclerView that utilizes this adapter
     */
    public interface OnItemClickListener {
        /**
         * Specifies the action after clicking on the RecyclerView that utilizes this adapter
         *
         * @param snapshot the user or business pulled from Firebase Firestore, formatted as a DocumentSnapshot
         */
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    /**
     * Determines the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     *
     * @param listener the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     */
    public void setOnItemClickListener(PeopleRVAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
