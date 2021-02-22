package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhsfbla.hhs_fbla_mad_2021.R;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class SavedRVAdapter extends RecyclerView.Adapter<SavedRVAdapter.StaticRVViewHolder> {
    private ArrayList<SavedRVModel> savedJobs;
    int row_index = -1;

    public SavedRVAdapter(ArrayList<SavedRVModel> items) {
        this.savedJobs = items;
    }

    public interface ClickListener {

        void onPositionClicked(int position);

        void onLongClicked(int position);
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
        holder.link = currentItem.getLink();
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

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder {
        Button businessLogo;
        TextView title;
        Button apply;
        //have to sort to find
        TextView businessName;
        LinearLayout savedJobLayout;
        String link;

        public StaticRVViewHolder(@NonNull View savedJobView) {
            super(savedJobView);
            apply = savedJobView.findViewById(R.id.saved_apply_button);
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    view.getContext().startActivity(intent);
                    startActivity(view.getContext(), new Intent(Intent.ACTION_VIEW, Uri.parse(link)), null);
                }
            });

            title = savedJobView.findViewById(R.id.saved_title);
            businessLogo = savedJobView.findViewById(R.id.saved_business_logo);
            businessName = savedJobView.findViewById(R.id.saved_business_name);
            savedJobLayout =  savedJobView.findViewById(R.id.saved_job_layout);

        }
    }
}

