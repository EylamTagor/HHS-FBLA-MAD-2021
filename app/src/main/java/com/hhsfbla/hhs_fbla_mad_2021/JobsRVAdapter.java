package com.hhsfbla.hhs_fbla_mad_2021;
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
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class JobsRVAdapter extends RecyclerView.Adapter<JobsRVAdapter.StaticRVViewHolder> implements Filterable {
    private ArrayList<JobsRVModel> jobs;
    private ArrayList<JobsRVModel> jobsFull;

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

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        Button businessLogo;
        TextView title;

        //have to sort to find
        TextView businessName;
        LinearLayout job_layout;
        TextView description;

        public StaticRVViewHolder(@NonNull View jobView) {
            super(jobView);
            title = jobView.findViewById(R.id.job_title);
            businessLogo = jobView.findViewById(R.id.job_business_logo);
            businessName = jobView.findViewById(R.id.job_business_name);
            job_layout =  jobView.findViewById(R.id.job_layout);
            description = jobView.findViewById(R.id.job_description);
        }
    }
}

