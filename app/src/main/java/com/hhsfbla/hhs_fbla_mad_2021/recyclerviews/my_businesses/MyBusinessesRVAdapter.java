package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.my_businesses;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBusinessesRVAdapter extends RecyclerView.Adapter<MyBusinessesRVAdapter.StaticRVViewHolder> {
    private ArrayList<MyBusinessesRVModel> businesses;
    int row_index = -1;

    public MyBusinessesRVAdapter(ArrayList<MyBusinessesRVModel> items) {
        this.businesses = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_business_item, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        MyBusinessesRVModel currentItem = businesses.get(position);

        //pfp, picasso thingy, placeholder below. Using getlogo() string
        holder.logo.setImageResource(R.drawable.apply_button);

    }


    @Override
    public int getItemCount() {
        return businesses.size();
    }

    /**
     * Updates the list of businesses using a new list
     *
     * @param newBusinesses new list to replace the old list
     */
    public void setbusinesses(List<Business> newBusinesses) {
        businesses.clear();

        for (Business b : newBusinesses)
            businesses.add(new MyBusinessesRVModel(b));
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        CircleImageView logo;
        LinearLayout myBusinessLayout;

        public StaticRVViewHolder(@NonNull View myBusinessView) {
            super(myBusinessView);
            logo = myBusinessView.findViewById(R.id.my_business_item_logo);
            myBusinessLayout =  myBusinessView.findViewById(R.id.skill_layout);

        }
    }
}

