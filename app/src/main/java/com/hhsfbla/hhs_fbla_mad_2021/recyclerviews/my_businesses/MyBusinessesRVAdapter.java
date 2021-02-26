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

    //List of business models to be displayed in the recyclerview.
    private List<MyBusinessesRVModel> businesses;
    private MyBusinessesRVAdapter.OnItemClickListener listener;
    private FirebaseFirestore db;
    private FirebaseUser fuser;
    int row_index = -1;

    /**
     * Constructor: takes in the skills models to be displayed and initializes field
     * @param items business items to be displayed
     */
    public MyBusinessesRVAdapter(ArrayList<MyBusinessesRVModel> items) {
        this.businesses = items;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
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
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_business_item, parent, false);
        RVViewHolder rvViewHolder = new RVViewHolder(view);
        return rvViewHolder;
    }

    /**
     *Called by RecyclerView to display the data at the specified position.
     *This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        MyBusinessesRVModel currentItem = businesses.get(position);
        holder.name.setText(currentItem.getName());

        //getting the business from firebase. Displaying logo.
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

    /**
     *
     * @return the size of the list of businesses to be displayed
     */
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

    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     */
    public class RVViewHolder extends RecyclerView.ViewHolder {
        CircleImageView logo;
        LinearLayout myBusinessLayout;
        TextView name;
        /**
         * Connects the fields to the XML of the people item. Initializes variables for display.
         *
         * @param myBusinessView the Business xml layout reference
         */
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