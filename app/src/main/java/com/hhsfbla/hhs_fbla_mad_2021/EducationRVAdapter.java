package com.hhsfbla.hhs_fbla_mad_2021;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Education;

import java.util.ArrayList;
import java.util.List;

public class EducationRVAdapter extends RecyclerView.Adapter<EducationRVAdapter.StaticRVViewHolder> {
    private ArrayList<EducationRVModel> education;
    int row_index = -1;

    public EducationRVAdapter(ArrayList<EducationRVModel> items) {
        this.education = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.education, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        EducationRVModel currentItem = education.get(position);
        holder.schoolName.setText(currentItem.getSchool());
        holder.period.setText(currentItem.getPeriod());
        holder.degree.setText(currentItem.getDegree());

    }


    @Override
    public int getItemCount() {
        return education.size();
    }

    /**
     * Updates the list of educations using a new list
     *
     * @param edus new list to replace the old list
     */
    public void setEducations(List<Education> edus) {
        education.clear();

        for (Education e : edus)
            education.add(new EducationRVModel(e));
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        TextView schoolName;
        TextView period;
        TextView degree;
        LinearLayout educationLayout;

        public StaticRVViewHolder(@NonNull View educationView) {
            super(educationView);
            schoolName = educationView.findViewById(R.id.education_school_name);
            period = educationView.findViewById(R.id.education_period);
            degree = educationView.findViewById(R.id.education_degree);
            educationLayout =  educationView.findViewById(R.id.education_layout);
        }
    }
}

