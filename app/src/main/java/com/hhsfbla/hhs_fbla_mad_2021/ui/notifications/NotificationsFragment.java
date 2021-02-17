package com.hhsfbla.hhs_fbla_mad_2021.ui.notifications;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.hhsfbla.hhs_fbla_mad_2021.NotificationsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.NotificationsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.PostsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.PostsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Notification;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;


import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;
    private RecyclerView notificationsView;
    private NotificationsRVAdapter notificationsRVAdapter;




    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsView = (RecyclerView)rootView.findViewById(R.id.notification_posts);
        notificationsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

      ArrayList<NotificationsRVModel> notifications = new ArrayList<>();

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));

        notifications.add(new NotificationsRVModel(new Notification("host", "user")));
        notifications.add(new NotificationsRVModel(new Notification("host", "user", "123456")));



        notificationsRVAdapter = new NotificationsRVAdapter(notifications);
        notificationsView.setAdapter(notificationsRVAdapter);
        return rootView;




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        // TODO: Use the ViewModel
    }

}