package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.skills;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhsfbla.hhs_fbla_mad_2021.R;

import java.util.ArrayList;
import java.util.List;

public class SkillsRVAdapter extends RecyclerView.Adapter<SkillsRVAdapter.StaticRVViewHolder> {

    //List of skill models
    private ArrayList<SkillsRVModel> skills;
    int row_index = -1;

    public SkillsRVAdapter(ArrayList<SkillsRVModel> items) {
        this.skills = items;
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
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }

    /**
     *Called by RecyclerView to display the data at the specified position.
     *This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        SkillsRVModel currentItem = skills.get(position);
        holder.skillName.setText(currentItem.getSkill());

    }

    /**
     *
     *
     * @return The size of the list of skill models
     */
    @Override
    public int getItemCount() {
        return skills.size();
    }

    /**
     * Updates the list of skills using a new list
     *
     * @param skis new list to replace the old list
     */
    public void setSkills(List<String> skis) {
        skills.clear();

        for (String s : skis)
            skills.add(new SkillsRVModel(s));
    }

    /**
     * The ViewHolder will be used to display items. Links the holder to the XML file
     */
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

