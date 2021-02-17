package com.hhsfbla.hhs_fbla_mad_2021;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExperiencesRVAdapter extends RecyclerView.Adapter<ExperiencesRVAdapter.StaticRVViewHolder> {
    private ArrayList<ExperiencesRVModel> experiences;
    int row_index = -1;

    public ExperiencesRVAdapter(ArrayList<ExperiencesRVModel> items) {
        this.experiences = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experience, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        ExperiencesRVModel currentItem = experiences.get(position);
        holder.description.setText(currentItem.getDescription());
        holder.header.setText(currentItem.getHeader());
        holder.description.setText(currentItem.getDescription());

    }


    @Override
    public int getItemCount() {
        return experiences.size();
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView header;
        TextView description;
        LinearLayout experienceLayout;

        public StaticRVViewHolder(@NonNull View experienceView) {
            super(experienceView);
            name = experienceView.findViewById(R.id.experience_name);
            header = experienceView.findViewById(R.id.experience_header);
            description = experienceView.findViewById(R.id.experience_description);
            experienceLayout =  experienceView.findViewById(R.id.ExperienceLayout);
        }
    }
}

