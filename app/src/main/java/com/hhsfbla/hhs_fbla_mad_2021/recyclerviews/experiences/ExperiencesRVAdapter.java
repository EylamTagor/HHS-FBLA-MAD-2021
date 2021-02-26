package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.experiences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Experience;

import java.util.ArrayList;
import java.util.List;

public class ExperiencesRVAdapter extends RecyclerView.Adapter<ExperiencesRVAdapter.StaticRVViewHolder> {
    private ArrayList<ExperiencesRVModel> experiences;
    int row_index = -1;

    /**
     * Constructor: takes in the experience models to be displayed and initializes field
     * @param items experiences items to be displayed
     */
    public ExperiencesRVAdapter(ArrayList<ExperiencesRVModel> items) {
        this.experiences = items;
    }

    /**
     *
     * Creates the  View holder to be used. The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType  The view type of the new View.
     * @return the view holder to be used
     */
    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experience, parent,false);
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
        ExperiencesRVModel currentItem = experiences.get(position);
        holder.name.setText(currentItem.getWorkplace());
        holder.header.setText(currentItem.getHeader());
        holder.description.setText(currentItem.getDescription());

    }
    /**
     *
     * @return the size of the experiences in the list to be displayed.
     */
    @Override
    public int getItemCount() {
        return experiences.size();
    }

    /**
     * Updates the list of experiences using a new list
     *
     * @param exps new list to replace the old list
     */
    public void setExperiences(List<Experience> exps) {
        experiences.clear();

        for (Experience e : exps)
            experiences.add(new ExperiencesRVModel(e));
    }

    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     */
    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView header;
        TextView description;
        /**
         * Connects the fields to the XML of the experience item. Initializes variables for display.
         *
         * @param experienceView the experience xml layout reference
         */
        public StaticRVViewHolder(@NonNull View experienceView) {
            super(experienceView);
            name = experienceView.findViewById(R.id.experience_name);
            header = experienceView.findViewById(R.id.experience_header);
            description = experienceView.findViewById(R.id.experience_description);
        }
    }
}

