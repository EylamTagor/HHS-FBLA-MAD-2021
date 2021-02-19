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

public class NotificationsRVAdapter extends RecyclerView.Adapter<NotificationsRVAdapter.StaticRVViewHolder> {
    private ArrayList<NotificationsRVModel> notifications;
    int row_index = -1;

    public NotificationsRVAdapter(ArrayList<NotificationsRVModel> items) {
        this.notifications = items;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        NotificationsRVModel currentItem = notifications.get(position);

        //If someone liked
        if(currentItem.getType() == 1) {
            holder.view.setText("View Profile");

            //USE ID TO FIND User to find NAME
            holder.message.setText("Full Name" + " Just followed you");
        }

        //If someone followed
        else {
            holder.view.setText("View Post");
            //USE ID TO FIND User to find NAME
            holder.message.setText("Full Name" + " Just liked your post");
        }




    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        Button view;


        public StaticRVViewHolder(@NonNull View notificationView) {
            super(notificationView);
            message = notificationView.findViewById(R.id.notification_message);
            view = notificationView.findViewById(R.id.notification_view);
        }
    }
}

