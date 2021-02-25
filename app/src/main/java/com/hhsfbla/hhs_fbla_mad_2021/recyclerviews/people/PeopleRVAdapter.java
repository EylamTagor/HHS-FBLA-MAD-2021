package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.people;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.posts.PostsRVModel;

import java.util.ArrayList;
import java.util.List;

public class PeopleRVAdapter extends RecyclerView.Adapter<PeopleRVAdapter.RVViewHolder>{
    private List<PeopleRVModel> people;
    private PeopleRVAdapter.OnItemClickListener listener;
    private FirebaseFirestore db;

    int row_index = -1;

    public PeopleRVAdapter(ArrayList<PeopleRVModel> items) {
        this.people = items;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        RVViewHolder rvViewHolder = new RVViewHolder(view);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        PeopleRVModel currentItem = people.get(position);
        holder.name.setText(currentItem.getName());
        holder.header.setText(currentItem.getHeader());

        //BACKEND GET PFP THIS IS A PLACEHOLDER
        holder.pfp.setBackgroundResource(R.drawable.apply_button);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }


    public class RVViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView header;
        private Button pfp;

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
