package com.hhsfbla.hhs_fbla_mad_2021;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class JobsRVAdapter extends RecyclerView.Adapter<JobsRVAdapter.StaticRVViewHolder> {
    private ArrayList<JobsRVModel> jobs;
    int row_index = -1;

    public JobsRVAdapter(ArrayList<JobsRVModel> items) {
        this.jobs = items;
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

