package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search;

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

import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.activities.OtherProfileActivity;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences.ExperiencesRVModel;

import java.util.ArrayList;
import java.util.List;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.StaticRVViewHolder> implements Filterable {
    private List<SearchRVModel> searches;
    private ArrayList<SearchRVModel> searchesFull;

    int row_index = -1;

    public SearchRVAdapter(ArrayList<SearchRVModel> items) {
        this.searches = items;
        searchesFull = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        SearchRVModel currentItem = searches.get(position);
        holder.name.setText(currentItem.getName());
        holder.header.setText(currentItem.getHeader());

        //BACKEND GET PFP THIS IS A PLACEHOLDER
        holder.pfp.setBackgroundResource(R.drawable.apply_button);
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

    private Filter searchesFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<SearchRVModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(searchesFull);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(SearchRVModel item : searchesFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
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
            searches.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView header;
        private Button pfp;

        public StaticRVViewHolder(@NonNull View searchView) {
            super(searchView);
            name = searchView.findViewById(R.id.search_item_name);
            pfp = searchView.findViewById(R.id.search_item_pfp);
            header = searchView.findViewById(R.id.search_item_header);

            searchView.setOnClickListener(view -> {
                view.getContext().startActivity(new Intent(view.getContext(), OtherProfileActivity.class));

                //upload job ID to firebase

                //Delete this after we get firebase working
                // savedJobs.add(new JobOffer(jobs.get(getAdapterPosition()).getbusinessID(),jobs.get(getAdapterPosition()).getJobTitle(),jobs.get(getAdapterPosition()).getLink(), jobs.get(getAdapterPosition()).getJobDescription() ));
            });
        }
    }
}
