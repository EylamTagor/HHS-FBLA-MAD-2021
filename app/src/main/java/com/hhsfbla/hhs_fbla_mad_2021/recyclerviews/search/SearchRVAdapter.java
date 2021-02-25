package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search;

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
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.RVViewHolder> implements Filterable {
    private List<SearchRVModel> searches;
    private ArrayList<SearchRVModel> searchesFull;
    private SearchRVAdapter.OnItemClickListener listener;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    int row_index = -1;

    public SearchRVAdapter(ArrayList<SearchRVModel> items) {
        this.searches = items;
        searchesFull = new ArrayList<>(items);
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
        SearchRVModel currentItem = searches.get(position);
        holder.name.setText(currentItem.getName());
        holder.header.setText(currentItem.getHeader());

        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();

        //BACKEND GET PFP THIS IS A PLACEHOLDER
        db.collection("users").document(currentItem.getId()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);

            if(u != null){
                if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase("")) {
                    Picasso.get().load(Uri.parse(u.getPfp())).into(holder.pfp);
                } else {
                    Picasso.get().load(fbuser.getPhotoUrl()).into(holder.pfp);
                }
            }


        });

        db.collection("businesses").document(currentItem.getId()).get().addOnSuccessListener(documentSnapshot -> {
            Business u = documentSnapshot.toObject(Business.class);

            if(u != null){
                if (u.getLogo() != null && !u.getLogo().equalsIgnoreCase("")) {
                    Picasso.get().load(Uri.parse(u.getLogo())).into(holder.pfp);
                }
            }


        });

    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    /**
     * Updates the list of users and businesses using a new list
     *
     * @param searches new list to replace the old search list
     */
    public void setSearches(List<SearchRVModel> searches) {
        this.searches = searches;
        this.searchesFull.clear();
        searchesFull = new ArrayList<>(searches);
    }

    @Override
    public Filter getFilter() {
        return searchesFilter;
    }

    private Filter searchesFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<SearchRVModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(searchesFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (SearchRVModel item : searchesFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            searches.clear();
            searches.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class RVViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView header;
        private CircleImageView pfp;

        public RVViewHolder(@NonNull View searchView) {
            super(searchView);
            name = searchView.findViewById(R.id.search_item_name);
            pfp = searchView.findViewById(R.id.search_item_pfp);
            header = searchView.findViewById(R.id.search_item_header);

            searchView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION && listener != null) {
                    if (searches.get(getAdapterPosition()).isUser())
                        db.collection("users").document(searches.get(getAdapterPosition()).getId()).get().addOnSuccessListener(documentSnapshot -> listener.onItemClick(documentSnapshot, getAdapterPosition(), true));
                    else
                        db.collection("businesses").document(searches.get(getAdapterPosition()).getId()).get().addOnSuccessListener(documentSnapshot -> listener.onItemClick(documentSnapshot, getAdapterPosition(), false));
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
        void onItemClick(DocumentSnapshot snapshot, int position, boolean isUser);
    }

    /**
     * Determines the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     *
     * @param listener the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     */
    public void setOnItemClickListener(SearchRVAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
