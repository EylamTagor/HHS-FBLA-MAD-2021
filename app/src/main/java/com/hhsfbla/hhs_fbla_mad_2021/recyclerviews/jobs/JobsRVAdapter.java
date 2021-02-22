package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;

import java.util.ArrayList;
import java.util.List;

public class JobsRVAdapter extends RecyclerView.Adapter<JobsRVAdapter.StaticRVViewHolder> implements Filterable {
    private ArrayList<JobsRVModel> jobs;
    private ArrayList<JobsRVModel> jobsFull;

    //Can probably delete after we get firebase upload
    private ArrayList<JobOffer> savedJobs;

    int row_index = -1;

    public JobsRVAdapter(ArrayList<JobsRVModel> items) {
        this.jobs = items;
        jobsFull = new ArrayList<>(items);

    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        JobsRVModel currentItem = jobs.get(position);
        holder.description.setText(currentItem.getJobDescription());
        holder.title.setText(currentItem.getJobTitle());
        //PLACEHOLDER, need to search to find business and get logo
        holder.businessLogo.setBackgroundResource(R.drawable.ic_followers);

    }


    @Override
    public int getItemCount() {
        return jobs.size();
    }

    @Override
    public Filter getFilter() {
        return jobsFilter;
    }

    private Filter jobsFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<JobsRVModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(jobsFull);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(JobsRVModel item : jobsFull){
                    if(item.getJobTitle().toLowerCase().contains(filterPattern)){
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
            jobs.clear();
            jobs.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void removeAt(int position) {
        jobs.remove(position);
        jobsFull.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, jobs.size());
    }

    public ArrayList<JobOffer> getSavedJobs() {
        return savedJobs;
    }

    public class StaticRVViewHolder extends RecyclerView.ViewHolder{
        Button businessLogo;
        TextView title;

        //have to sort to find
        TextView businessName;
        LinearLayout job_layout;
        TextView description;
        Button saveButton;
        private boolean isSaved;


        public StaticRVViewHolder(@NonNull View jobView) {
            super(jobView);
            isSaved = false;
            title = jobView.findViewById(R.id.job_title);
            businessLogo = jobView.findViewById(R.id.job_business_logo);
            businessName = jobView.findViewById(R.id.job_business_name);
            job_layout =  jobView.findViewById(R.id.job_layout);
            description = jobView.findViewById(R.id.job_description);
            saveButton = jobView.findViewById(R.id.job_save_button);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSaved = true;
                    removeAt(getAdapterPosition());
                    saveButton.setBackgroundResource(R.drawable.ic_saved);

                    //upload job ID to firebase


                    //Delete this after we get firebase working
                    // savedJobs.add(new JobOffer(jobs.get(getAdapterPosition()).getbusinessID(),jobs.get(getAdapterPosition()).getJobTitle(),jobs.get(getAdapterPosition()).getLink(), jobs.get(getAdapterPosition()).getJobDescription() ));
                }
            });

            //Button for when user profile is clicked.open new profile based on ID.
        }
    }
}

