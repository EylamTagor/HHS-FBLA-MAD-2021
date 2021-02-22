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

public class SkillsRVAdapter extends RecyclerView.Adapter<SkillsRVAdapter.StaticRVViewHolder> {
    private ArrayList<SkillsRVModel> skills;
    int row_index = -1;

    public SkillsRVAdapter(ArrayList<SkillsRVModel> items) {
        this.skills = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        SkillsRVModel currentItem = skills.get(position);
        holder.skillName.setText(currentItem.getSkill());

    }


    @Override
    public int getItemCount() {
        return skills.size();
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        TextView skillName;
        LinearLayout skillLayout;

        public StaticRVViewHolder(@NonNull View skillView) {
            super(skillView);
            skillName = skillView.findViewById(R.id.skill_skill_name);
            skillLayout =  skillView.findViewById(R.id.skill_layout);

        }
    }
}

