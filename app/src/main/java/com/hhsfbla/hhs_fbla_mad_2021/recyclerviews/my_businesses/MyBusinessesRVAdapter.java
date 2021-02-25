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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Post;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search.SearchRVAdapter;
import com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.search.SearchRVModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBusinessesRVAdapter extends RecyclerView.Adapter<MyBusinessesRVAdapter.RVViewHolder> {
    private List<MyBusinessesRVModel> businesses;
    private MyBusinessesRVAdapter.OnItemClickListener listener;
    private FirebaseFirestore db;
    private FirebaseUser fuser;

    int row_index = -1;

    public MyBusinessesRVAdapter(ArrayList<MyBusinessesRVModel> items) {
        this.businesses = items;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_business_item, parent, false);
        RVViewHolder rvViewHolder = new RVViewHolder(view);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        MyBusinessesRVModel currentItem = businesses.get(position);
        holder.name.setText(currentItem.getName());

        //pfp, picasso thingy, placeholder below. Using getlogo() string
        //getting the business from firebase
        if (currentItem.hasLogo()) db.collection("businesses")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : task.getResult())
                            if (document.toObject(Business.class).getName().equals(currentItem.getName()) && document.toObject(Business.class).getWebsite().equals(currentItem.getWebsite())) {
                                holder.name.setText(document.toObject(Business.class).getName());
                                if (document.toObject(Business.class).getLogo() != null && !document.toObject(Business.class).getLogo().equalsIgnoreCase(""))
                                    Picasso.get().load(Uri.parse(document.toObject(Business.class).getLogo())).into(holder.logo);
                            }
                });
        else
            holder.logo.setImageResource(R.drawable.ic_no_business_logo);
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    /**
     * Updates the list of businesses using a new list
     *
     * @param businesses new list to replace the old business list
     */
    public void setBusinesses(List<MyBusinessesRVModel> businesses) {
        this.businesses = businesses;
    }

    public class RVViewHolder extends RecyclerView.ViewHolder {
        CircleImageView logo;
        LinearLayout myBusinessLayout;
        TextView name;

        public RVViewHolder(@NonNull View myBusinessView) {
            super(myBusinessView);
            logo = myBusinessView.findViewById(R.id.my_business_item_logo);
            myBusinessLayout = myBusinessView.findViewById(R.id.my_business_item_layout);
            name = myBusinessView.findViewById(R.id.my_business_name);

            myBusinessView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION && listener != null)
                    db.collection("businesses").document(businesses.get(getAdapterPosition()).getId()).get().addOnSuccessListener(documentSnapshot -> listener.onItemClick(documentSnapshot, getAdapterPosition()));
            });
        }
    }

    /**
     * Used to specify action after clicking on the RecyclerView that utilizes this adapter
     */
    public interface OnItemClickListener {
        /**
         * Specifies the action after clicking on the RecyclerView that utilizes this adapter
         *
         * @param snapshot the user or business pulled from Firebase Firestore, formatted as a DocumentSnapshot
         */
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    /**
     * Determines the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     *
     * @param listener the object to listen to and manage clicking actions on the RecyclerView that utilizes this adapter
     */
    public void setOnItemClickListener(MyBusinessesRVAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}