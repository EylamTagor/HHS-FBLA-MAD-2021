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

/**
 * Responsible for displaying the search items in the search view. It also handles the filtering based on user inputted text.
 */
public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.RVViewHolder> implements Filterable {
    private List<SearchRVModel> searches;
    private ArrayList<SearchRVModel> searchesFull;
    private SearchRVAdapter.OnItemClickListener listener;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    int row_index = -1;

    /**
     * Initializes the fields in the adapter. Constructor
     * @param items the search items passed in that need to be displayed.
     */
    public SearchRVAdapter(ArrayList<SearchRVModel> items) {
        this.searches = items;
        searchesFull = new ArrayList<>(items);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        RVViewHolder rvViewHolder = new RVViewHolder(view);
        return rvViewHolder;
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
        SearchRVModel currentItem = searches.get(position);
        holder.name.setText(currentItem.getName());
        holder.header.setText(currentItem.getHeader());
        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();

        //Get the profile pic of the users in the search
        db.collection("users").document(currentItem.getId()).get().addOnSuccessListener(documentSnapshot -> {
            User u = documentSnapshot.toObject(User.class);

            if(u != null){
                if (u.getPfp() != null && !u.getPfp().equalsIgnoreCase(""))
                    Picasso.get().load(Uri.parse(u.getPfp())).into(holder.pfp);
            }

        });
        //Get the profile pic of the businesses in the search
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

    /**
     *
     * @return returns the filter
     */
    @Override
    public Filter getFilter() {
        return searchesFilter;
    }

    //Creates the search filter
    private Filter searchesFilter = new Filter() {

        /**
         * Filters the search results based on the inputted text
         * @param charSequence the text that the user inputs, the constraint used to filter the data
         * @return the filtered results from the operation
         */
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

        /**
         * publishes the filter results, updates the list of the search results.
         * @param charSequence the constraint used to filter the data
         * @param filterResults the results of the filter.
         */
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            searches.clear();
            searches.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    /**
     * The ViewHolder will be used to display items.
     */
    public class RVViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView header;
        private CircleImageView pfp;

        /**
         *
         * Initalizes the recyclerview view holder. Links XML.
         *
         * @param searchView The view type to be used. View type references search item XML.
         * @return the view holder to be used
         */
        public RVViewHolder(@NonNull View searchView) {
            super(searchView);
            name = searchView.findViewById(R.id.search_item_name);
            pfp = searchView.findViewById(R.id.search_item_pfp);
            header = searchView.findViewById(R.id.search_item_header);

            //Handling transition to respective user/biz page when clicked
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
