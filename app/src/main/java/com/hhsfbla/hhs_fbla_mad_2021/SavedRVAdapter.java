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

public class SavedRVAdapter extends RecyclerView.Adapter<SavedRVAdapter.StaticRVViewHolder> {
    private ArrayList<SavedRVModel> savedJobs;
    int row_index = -1;

    public SavedRVAdapter(ArrayList<SavedRVModel> items) {
        this.savedJobs = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_job, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        SavedRVModel currentItem = savedJobs.get(position);
        holder.title.setText(currentItem.getJobTitle());

        //placeholder, need to sort to find name of business given ID of the holder.
        holder.businessName.setText("Google LLC");

        //PLACEHOLDER, need to search to find business and get logo
        holder.businessLogo.setBackgroundResource(R.drawable.ic_followers);

    }


    @Override
    public int getItemCount() {
        return savedJobs.size();
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        Button businessLogo;
        TextView title;

        //have to sort to find
        TextView businessName;
        LinearLayout savedJobLayout;

        public StaticRVViewHolder(@NonNull View savedJobView) {
            super(savedJobView);
            title = savedJobView.findViewById(R.id.saved_title);
            businessLogo = savedJobView.findViewById(R.id.saved_business_logo);
            businessName = savedJobView.findViewById(R.id.saved_business_name);
            savedJobLayout =  savedJobView.findViewById(R.id.saved_job_layout);
        }
    }
}

