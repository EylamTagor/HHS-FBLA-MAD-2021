package com.hhsfbla.hhs_fbla_mad_2021.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.notifs.NotificationsRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.notifs.NotificationsRVModel;
import com.hhsfbla.hhs_fbla_mad_2021.R;

import com.hhsfbla.hhs_fbla_mad_2021.classes.Notification;


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