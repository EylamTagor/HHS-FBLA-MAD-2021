package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.my_businesses;
import android.net.Uri;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBusinessesRVAdapter extends RecyclerView.Adapter<MyBusinessesRVAdapter.StaticRVViewHolder> {
    private ArrayList<MyBusinessesRVModel> businesses;
    int row_index = -1;
    private FirebaseUser fuser;
    private FirebaseFirestore db;

    public MyBusinessesRVAdapter(ArrayList<MyBusinessesRVModel> items) {
        this.businesses = items;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
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
        holder.name.setText(currentItem.getName());


        //pfp, picasso thingy, placeholder below. Using getlogo() string
        //getting the business from firebase
        if(currentItem.hasLogo()) {

            db.collection("businesses")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.toObject(Business.class).getName().equals(currentItem.getName()) && document.toObject(Business.class).getWebsite().equals(currentItem.getWebsite())) {
                                    holder.name.setText(document.toObject(Business.class).getName());
                                    if (document.toObject(Business.class).getLogo() != null && !document.toObject(Business.class).getLogo().equalsIgnoreCase("")) {
                                        Picasso.get().load(Uri.parse(document.toObject(Business.class).getLogo())).into(holder.logo);
                                        Log.println(Log.DEBUG, "sadsd", "huge dub");
                                    }
                                }
                            }
                        }
                    });
        }
        else{
            holder.logo.setImageResource(R.drawable.ic_no_business_logo);
        }

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
        TextView name;

        public StaticRVViewHolder(@NonNull View myBusinessView) {
            super(myBusinessView);
            logo = myBusinessView.findViewById(R.id.my_business_item_logo);
            myBusinessLayout =  myBusinessView.findViewById(R.id.my_business_item_layout);
            name = myBusinessView.findViewById(R.id.my_business_name);

        }
    }
}

